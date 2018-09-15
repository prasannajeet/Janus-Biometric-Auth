package com.praszapps.fingertip.presenter


import com.praszapps.fingertip.MVP.FingertipMVPContract
import com.praszapps.fingertip.model.repository.FingertipRepository

import javax.inject.Inject
import javax.inject.Named

class FingertipDialogFragmentPresenter : FingertipMVPContract.Presenter {

    @Inject
    @Named("dialogFragmentView")
    lateinit var mView: FingertipMVPContract.View
    @Inject
    lateinit var mFingerprintRepository: FingertipRepository

    fun init(view: FingertipMVPContract.View) {
        this.mView = view

        if (!mFingerprintRepository.isFingerprintAvailable) {
            mView.setUpFingerprintViews()
        } else {
            mView.handleNoFingerprintAuthPossible()
        }
    }

    override fun authenticateViaFingerprint() {
        mFingerprintRepository.startFingerprintTracking()
    }

    override fun stopFingerprintTracking() {
        mFingerprintRepository.stopFingerprintTracking()
    }
}
