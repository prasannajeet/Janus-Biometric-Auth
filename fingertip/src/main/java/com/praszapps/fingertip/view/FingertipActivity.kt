package com.praszapps.fingertip.view

import com.praszapps.fingertip.MVP.FingertipMVPContract
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import javax.inject.Named

class FingertipActivity : DaggerAppCompatActivity(), FingertipMVPContract.View {

    @Inject
    @Named("activityPresenter")
    lateinit var mPresenter: FingertipMVPContract.Presenter

    override fun setUpFingerprintViews() {

    }

    override fun handleNoFingerprintAuthPossible() {

    }

    override fun onFingerPrintAuthenticationSuccess() {

    }

    override fun onFingerprintAuthenticationFailed(text: String) {

    }
}
