package com.praszapps.fingertip.presenter


import android.app.KeyguardManager
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import com.praszapps.fingertip.MVP.FingertipMVPContract
import com.praszapps.fingertip.model.repository.FingertipSecureProvider

internal class FingertipDialogFragmentIPresenter(IView: FingertipMVPContract.IView) : FingertipMVPContract.IPresenter {

    private val mView = IView

    private val mFingerprintRepository = FingertipSecureProvider()


    override fun initialize(mFingerprintManager: FingerprintManagerCompat, mKeyguardManager: KeyguardManager) {
        mFingerprintRepository.initialize(mFingerprintManager, mKeyguardManager)
        mView.setUpFingerprintViews()
    }

    override fun authenticateViaFingerprint() {
        mFingerprintRepository.startFingerprintTracking()
    }

    override fun onViewDestroyed() {
        mFingerprintRepository.stopFingerprintTracking()
    }
}
