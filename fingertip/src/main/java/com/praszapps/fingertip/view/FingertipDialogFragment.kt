package com.praszapps.fingertip.view

import android.app.KeyguardManager
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.praszapps.fingertip.MVP.FingertipMVPContract
import com.praszapps.fingertip.R
import com.praszapps.fingertip.presenter.FingertipDialogFragmentIPresenter
import kotlinx.android.synthetic.main.fingerprint_dialog.*

internal class FingertipDialogFragment : DialogFragment(), FingertipMVPContract.IView {

    lateinit var fManager: FingerprintManagerCompat
    lateinit var kManager: KeyguardManager

    private val mIPresenter: FingertipMVPContract.IPresenter by lazy {
        FingertipDialogFragmentIPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog)
        return LayoutInflater.from(activity).inflate(R.layout.fingerprint_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mIPresenter.initialize(fManager, kManager)
    }

    override fun onDestroy() {
        super.onDestroy()
        mIPresenter.onViewDestroyed()
    }

    override fun setUpFingerprintViews() {
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        mIPresenter.authenticateViaFingerprint()
    }

    override fun onFingerPrintAuthenticationSuccess() {

    }

    override fun onFingerprintAuthenticationFailed(text: String) {

    }
}
