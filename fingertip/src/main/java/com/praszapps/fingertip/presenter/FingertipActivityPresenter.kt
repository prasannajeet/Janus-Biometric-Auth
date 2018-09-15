package com.praszapps.fingertip.presenter

import com.praszapps.fingertip.MVP.FingertipMVPContract
import com.praszapps.fingertip.model.repository.FingertipRepository

import javax.inject.Inject
import javax.inject.Named

class FingertipActivityPresenter : FingertipMVPContract.Presenter {

    @Inject
    @Named("activityView")
    lateinit var view: FingertipMVPContract.View

    @Inject
    lateinit var repository: FingertipRepository

    override fun authenticateViaFingerprint() {

    }

    override fun stopFingerprintTracking() {

    }
}
