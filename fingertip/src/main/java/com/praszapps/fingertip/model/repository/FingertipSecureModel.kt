package com.praszapps.fingertip.model.repository

import android.annotation.TargetApi
import android.app.KeyguardManager
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v4.os.CancellationSignal
import com.praszapps.fingertip.contract.FingertipMVPContract
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

@TargetApi(23)
internal class FingertipSecureModel : FingerprintManagerCompat.AuthenticationCallback(), FingertipMVPContract.IModel {

    private val DEFAULT_KEY_NAME = "FingertipKeyName"
    private val mSignal = CancellationSignal()

    private lateinit var fManager: FingerprintManagerCompat
    private lateinit var kManager: KeyguardManager
    private lateinit var keyStore: KeyStore
    private lateinit var keyGenerator: KeyGenerator
    private lateinit var mCryptoObj: FingerprintManagerCompat.CryptoObject

    override suspend fun initialize(mFingerprintManager: FingerprintManagerCompat, mKeyguardManager: KeyguardManager): FingertipInitResponseModel {

        fManager = mFingerprintManager
        kManager = mKeyguardManager
        val defaultCipher: Cipher

        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyGenerator = KeyGenerator
                    .getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            keyStore.load(null)
            // Set the alias of the entry in Android KeyStore where the key will appear
            // and the constrains (purposes) in the constructor of the Builder

            val builder = KeyGenParameterSpec.Builder(DEFAULT_KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
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
            val key = keyStore.getKey(DEFAULT_KEY_NAME, null) as SecretKey
            defaultCipher.init(Cipher.ENCRYPT_MODE, key)
            mCryptoObj = FingerprintManagerCompat.CryptoObject(defaultCipher)

            return FingertipInitResponseModel(true)

        } catch (e: Exception) {
            return FingertipInitResponseModel(message = e.localizedMessage)
        }
    }

    override fun startFingerprintTracking(listener: FingerprintManagerCompat.AuthenticationCallback) {
        fManager.authenticate(mCryptoObj, 0, mSignal, listener, null)
    }

    override fun stopFingerprintTracking() {
        mSignal.cancel()
    }
}