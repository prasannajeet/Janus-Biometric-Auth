/*
 *    Copyright 2018 Prasannajeet Pani
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.praszapps.janus.model.repository

import android.annotation.TargetApi
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.core.os.CancellationSignal
import com.praszapps.janus.model.FingerprintInitializationKeyInvalidation
import com.praszapps.janus.model.JanusResult
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

@TargetApi(23)
internal class JanusSecureProvider : FingerprintManagerCompat.AuthenticationCallback() {

    private val mSignal = CancellationSignal()

    private val _keyName = "JanusKeyName"
    private lateinit var keyStore: KeyStore
    private lateinit var keyGenerator: KeyGenerator
    private lateinit var mCryptoObj: FingerprintManagerCompat.CryptoObject

    @RequiresApi(Build.VERSION_CODES.N)
    internal fun initialize(): JanusResult<FingerprintInitializationKeyInvalidation> {

        val defaultCipher: Cipher

        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyGenerator = KeyGenerator
                    .getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            keyStore.load(null)
            // Set the alias of the entry in Android KeyStore where the key will appear
            // and the constrains (purposes) in the constructor of the Builder

            val builder = KeyGenParameterSpec.Builder(_keyName,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setInvalidatedByBiometricEnrollment(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)

            // This is a workaround to avoid crashes on devices whose API level is < 24
            // because KeyGenParameterSpec.Builder#setInvalidatedByBiometricEnrollment is only
            // visible on API level +24.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setInvalidatedByBiometricEnrollment(false)
            }
            keyGenerator.init(builder.build())
            keyGenerator.generateKey()
            defaultCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7)
            keyStore.load(null)
            val key = keyStore.getKey(_keyName, null) as SecretKey
            defaultCipher.init(Cipher.ENCRYPT_MODE, key)
            mCryptoObj = FingerprintManagerCompat.CryptoObject(defaultCipher)
            return JanusResult.Success(FingerprintInitializationKeyInvalidation())
        } catch (e1: KeyPermanentlyInvalidatedException) {
            keyStore.deleteEntry(_keyName)
            return JanusResult.Success(FingerprintInitializationKeyInvalidation(true))
        } catch (e: Exception) {
            return JanusResult.Error(e)
        }
    }

    internal fun startFingerprintTracking(fManager: FingerprintManagerCompat, listener: FingerprintManagerCompat.AuthenticationCallback) {
        fManager.authenticate(mCryptoObj, 0, mSignal, listener, null)
    }

    internal fun stopFingerprintTracking() {
        mSignal.cancel()
    }
}