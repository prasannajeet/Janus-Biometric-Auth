package com.praszapps.fingertip.model.repository

import android.annotation.TargetApi
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
import io.reactivex.Observable
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

@TargetApi(23)
internal class FingertipSecureProvider : FingerprintManagerCompat.AuthenticationCallback(), FingertipMVPContract.IProvider {

    private val DEFAULT_KEY_NAME = "FingertipKeyName"
    private val mSignal = CancellationSignal()

    private lateinit var fManager: FingerprintManagerCompat
    private lateinit var kManager: KeyguardManager
    private lateinit var keyStore: KeyStore
    private lateinit var keyGenerator: KeyGenerator
    private lateinit var mCryptoObj: FingerprintManagerCompat.CryptoObject
    private lateinit var mCallback: FingertipDialogFragmentPresenter.FingerprintResultCallback

    override fun initialize(mFingerprintManager: FingerprintManagerCompat, mKeyguardManager: KeyguardManager): Observable<ErrorModel> {

        fManager = mFingerprintManager
        kManager = mKeyguardManager

        var initObservable: Observable<ErrorModel>

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

            initObservable = Observable.just(ErrorModel(true))

        } catch (e: NoSuchAlgorithmException) {
            initObservable = Observable.just(ErrorModel(message = e.localizedMessage))
        } catch (e: NoSuchProviderException) {
            initObservable = Observable.just(ErrorModel(message = e.localizedMessage))
        } catch (e: InvalidAlgorithmParameterException) {
            initObservable = Observable.just(ErrorModel(message = e.localizedMessage))
        } catch (e: CertificateException) {
            initObservable = Observable.just(ErrorModel(message = e.localizedMessage))
        } catch (e: IOException) {
            initObservable = Observable.just(ErrorModel(message = e.localizedMessage))
        } catch (e: NoSuchPaddingException) {
            initObservable = Observable.just(ErrorModel(message = e.localizedMessage))
        } catch (e: KeyPermanentlyInvalidatedException) {
            initObservable = Observable.just(ErrorModel(message = e.localizedMessage))
        } catch (e: UnrecoverableKeyException) {
            initObservable = Observable.just(ErrorModel(message = e.localizedMessage))
        } catch (e: InvalidKeyException) {
            initObservable = Observable.just(ErrorModel(message = e.localizedMessage))
        } catch (e: KeyStoreException) {
            initObservable = Observable.just(ErrorModel(message = e.localizedMessage))
        }
        return initObservable
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