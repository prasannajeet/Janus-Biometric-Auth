package com.praszapps.fingertip.model.repository

import android.app.KeyguardManager
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v4.os.CancellationSignal
import android.util.Log
import com.praszapps.fingertip.MVP.FingertipMVPContract
import com.praszapps.fingertip.presenter.FingertipDialogFragmentPresenter
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

internal class FingertipSecureProvider : FingerprintManagerCompat.AuthenticationCallback(), FingertipMVPContract.IProvider {

    private val DEFAULT_KEY_NAME = "FingertipKeyName"
    private val mSignal = CancellationSignal()

    private lateinit var fManager: FingerprintManagerCompat
    private lateinit var kManager: KeyguardManager
    private lateinit var keyStore: KeyStore
    private lateinit var keyGenerator: KeyGenerator
    private lateinit var mCryptoObj: FingerprintManagerCompat.CryptoObject
    private lateinit var mCallback: FingertipDialogFragmentPresenter.FingerprintResultCallback

    override fun initialize(mFingerprintManager: FingerprintManagerCompat, mKeyguardManager: KeyguardManager) {

        fManager = mFingerprintManager
        kManager = mKeyguardManager

        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
        } catch (e: KeyStoreException) {
            throw RuntimeException("Failed to get an instance of KeyStore", e)
        }

        try {
            keyGenerator = KeyGenerator
                    .getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get an instance of KeyGenerator", e)
        } catch (e: NoSuchProviderException) {
            throw RuntimeException("Failed to get an instance of KeyGenerator", e)
        }

        createKey(DEFAULT_KEY_NAME, false)

        val defaultCipher: Cipher
        try {
            defaultCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get an instance of Cipher", e)
        } catch (e: NoSuchPaddingException) {
            throw RuntimeException("Failed to get an instance of Cipher", e)
        }

        if (initCipher(defaultCipher, DEFAULT_KEY_NAME)) {
            mCryptoObj = FingerprintManagerCompat.CryptoObject(defaultCipher)
        }

    }


    private fun createKey(keyName: String, invalidatedByBiometricEnrollment: Boolean) {
        // The enrolling flow for fingerprint. This is where you ask the user to set up fingerprint
        // for your flow. Use of keys is necessary if you need to know if the set of
        // enrolled fingerprints has changed.
        try {
            keyStore.load(null)
            // Set the alias of the entry in Android KeyStore where the key will appear
            // and the constrains (purposes) in the constructor of the Builder

            val builder = KeyGenParameterSpec.Builder(keyName,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    // Require the user to authenticate with a fingerprint to authorize every use
                    // of the key
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)

            // This is a workaround to avoid crashes on devices whose API level is < 24
            // because KeyGenParameterSpec.Builder#setInvalidatedByBiometricEnrollment is only
            // visible on API level +24.
            // Ideally there should be a compat library for KeyGenParameterSpec.Builder but
            // which isn't available yet.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setInvalidatedByBiometricEnrollment(invalidatedByBiometricEnrollment)
            }
            keyGenerator.init(builder.build())
            keyGenerator.generateKey()
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: InvalidAlgorithmParameterException) {
            throw RuntimeException(e)
        } catch (e: CertificateException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    private fun initCipher(cipher: Cipher, keyName: String): Boolean {
        try {
            keyStore.load(null)
            val key = keyStore.getKey(keyName, null) as SecretKey
            cipher.init(Cipher.ENCRYPT_MODE, key)
            return true
        } catch (e: KeyPermanentlyInvalidatedException) {
            return false
        } catch (e: KeyStoreException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: CertificateException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: UnrecoverableKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: IOException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: InvalidKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        }
    }


    override fun startFingerprintTracking(callback: FingertipDialogFragmentPresenter.FingerprintResultCallback) {
        mCallback = callback
        fManager.authenticate(mCryptoObj, 0, mSignal, this, null)
    }

    override fun stopFingerprintTracking() {
        mSignal.cancel()
    }

    override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
        super.onAuthenticationSucceeded(result)
        Log.d("Repo", "Auth success")
        mCallback.onSuccess()
    }

    override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
        super.onAuthenticationError(errMsgId, errString)
        Log.d("Repo", "Auth Error")
        mCallback.onFailed(errString.toString())
    }

    override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
        super.onAuthenticationHelp(helpMsgId, helpString)
        Log.d("Repo", "Auth Help")
        mCallback.onFailed(helpString.toString())
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        Log.d("Repo", "Auth failed")
        mCallback.onFailed("Fingerprint not recognized. Try again")
    }

}