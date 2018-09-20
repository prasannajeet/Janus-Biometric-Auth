package com.praszapps.fingertip.presenter


import android.app.KeyguardManager
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import com.praszapps.fingertip.contract.FingertipMVPContract
import com.praszapps.fingertip.model.repository.FingertipSecureModel
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch

internal class FingertipDialogFragmentPresenter(IView: FingertipMVPContract.IView) : FingertipMVPContract.IPresenter {

    private val mView = IView

    private val mFingerprintRepository = FingertipSecureModel()

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