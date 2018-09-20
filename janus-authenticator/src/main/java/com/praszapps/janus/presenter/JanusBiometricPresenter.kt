package com.praszapps.janus.presenter


import android.app.KeyguardManager
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import com.praszapps.janus.contract.JanusContract
import com.praszapps.janus.model.repository.JanusSecureProvider
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch

internal class JanusBiometricPresenter(IView: JanusContract.IView) : JanusContract.IPresenter {

    private val mView = IView

    private val mFingerprintRepository = JanusSecureProvider()

    private val listener = object : FingerprintManagerCompat.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
            mView.onFingerPrintAuthenticationSuccess()
        }

        override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
            mView.onFingerprintAuthenticationFailed(errString.toString())
        }

        override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
            mView.onFingerprintAuthenticationFailed(helpString.toString())
        }

        override fun onAuthenticationFailed() {
            mView.onFingerprintAuthenticationFailed("Fingerprint not recognized. Try again")
        }
    }

    override fun initialize(mFingerprintManager: FingerprintManagerCompat, mKeyguardManager: KeyguardManager) {

        GlobalScope.launch {
            val model = mFingerprintRepository.initialize(mFingerprintManager, mKeyguardManager)

            if (model.isSuccess) {
                mView.setUpFingerprintViews()
            } else {
                mView.onFingerprintAuthenticationFailed(model.message)
            }
        }
    }

    override fun authenticateViaFingerprint() {

        mFingerprintRepository.startFingerprintTracking(listener)
    }

    override fun cancelFingerprintDetection() {
        mFingerprintRepository.stopFingerprintTracking()
    }
}