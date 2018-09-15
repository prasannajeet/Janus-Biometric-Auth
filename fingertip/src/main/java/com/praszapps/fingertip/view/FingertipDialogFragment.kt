package com.praszapps.fingertip.view

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.praszapps.fingertip.MVP.FingertipMVPContract
import com.praszapps.fingertip.R
import dagger.android.DaggerDialogFragment
import javax.inject.Inject
import javax.inject.Named

class FingertipDialogFragment : DaggerDialogFragment(), FingertipMVPContract.View {

    @Inject
    @Named("dialogFragmentPresenter")
    lateinit var mPresenter: FingertipMVPContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(activity).inflate(R.layout.fingerprint_dialog, container, false)
    }

    override fun setUpFingerprintViews() {

    }

    override fun handleNoFingerprintAuthPossible() {

    }

    override fun onFingerPrintAuthenticationSuccess() {

    }

    override fun onFingerprintAuthenticationFailed(text: String) {

    }
}
