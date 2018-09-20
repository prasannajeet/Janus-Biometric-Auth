package com.praszapps.janus.view

import android.app.KeyguardManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.DialogFragment
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.praszapps.janus.R
import com.praszapps.janus.contract.JanusContract
import com.praszapps.janus.contract.ManagerViewInteractor
import com.praszapps.janus.presenter.JanusBiometricPresenter
import kotlinx.android.synthetic.main.fingerprint_dialog.*

internal class JanusV23FingerprintSlidingMenu : DialogFragment(), JanusContract.IView {

    lateinit var fManager: FingerprintManagerCompat
    lateinit var kManager: KeyguardManager
    lateinit var listener: ManagerViewInteractor

    private val mPresenter: JanusContract.IPresenter by lazy {
        JanusBiometricPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(activity).inflate(R.layout.fingerprint_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.initialize(fManager, kManager)
    }


    override fun setUpFingerprintViews() {
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        mPresenter.authenticateViaFingerprint()
    }

    override fun onFingerPrintAuthenticationSuccess() {
        iconFAB.setImageResource(R.drawable.ic_check_white_24dp)
        errorTextView.text = getString(R.string.auth_success_message)
        dismissAfterHalfSecond()

    }

    override fun onFingerprintAuthenticationFailed(text: String) {
        iconFAB.setImageResource(R.drawable.ic_error_white_24dp)
        errorTextView.text = text
        if (text.startsWith(getString(R.string.too_many_attempts))) {
            dismissAfterHalfSecond(false, text)
        }
    }

    private fun dismissAfterHalfSecond(isSuccess: Boolean = true, message: String? = null) {
        Handler().postDelayed({
            mPresenter.cancelFingerprintDetection()
            if (isSuccess) {
                listener.onAuthSuccess()
            } else {
                if (message != null) {
                    listener.onAuthFailure(message)
                }
            }
            dismiss()
        }, 700)
    }
}