package com.praszapps.fingertip.model.repository

import android.app.KeyguardManager
import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v4.os.CancellationSignal
import com.praszapps.fingertip.MVP.FingertipMVPContract
import java.security.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey
import javax.inject.Inject

class FingertipRepository : FingerprintManagerCompat.AuthenticationCallback(), FingertipMVPContract.IFingerprintRepository {

    private val KEY_NAME = ""
    private var mFingerprintManager: FingerprintManagerCompat? = null
    private var mSignal: CancellationSignal? = null
    @Inject
    lateinit var mContext: Context
    private var mKeyguardManager: KeyguardManager? = null
    private var mKeyStore: KeyStore? = null
    private var mKeyGenerator: KeyGenerator? = null
    private var mCipher: Cipher? = null
    private var mCryptoObj: FingerprintManagerCompat.CryptoObject? = null

    val isDeviceFingerprintAuthReady: Boolean
        get() = mFingerprintManager != null && mKeyguardManager != null && mFingerprintManager!!.isHardwareDetected && mFingerprintManager!!.hasEnrolledFingerprints() && mKeyguardManager!!.isDeviceSecure

    override val isFingerprintAvailable: Boolean
        get() = mFingerprintManager != null && mFingerprintManager!!.isHardwareDetected && mFingerprintManager!!.hasEnrolledFingerprints()

    override fun initalize() {
        mFingerprintManager = FingerprintManagerCompat.from(mContext)
        mSignal = CancellationSignal()
        mKeyguardManager = mContext.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore")

            mKeyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")

            mKeyGenerator!!.init(KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build())

            mKeyGenerator!!.generateKey()
            mCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7)
            val key = mKeyStore!!.getKey(KEY_NAME, null) as SecretKey
            mCipher!!.init(Cipher.ENCRYPT_MODE, key)

            mCryptoObj = FingerprintManagerCompat.CryptoObject(mCipher!!)

        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: NoSuchProviderException) {
            e.printStackTrace()
        } catch (e: InvalidAlgorithmParameterException) {
            e.printStackTrace()
        } catch (e: UnrecoverableKeyException) {
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        }


    }

    override fun startFingerprintTracking() {

    }

    override fun stopFingerprintTracking() {

    }

}
