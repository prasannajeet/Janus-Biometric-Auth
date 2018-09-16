package com.praszapps.fingertip.manager

import android.app.KeyguardManager
import android.content.Context
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v7.app.AppCompatActivity
import com.praszapps.fingertip.MVP.ManagerViewInteractor
import com.praszapps.fingertip.view.FingertipDialogFragment

enum class FingertipAuthenticator : ManagerViewInteractor {

    INSTANCE;

    lateinit var mListener: FingertipAuthenticationResult

    fun doFingerprintAuthentication(mConfig: FingertipAuthConfig, listener: FingertipAuthenticationResult) {

        mListener = listener
        val fManager = FingerprintManagerCompat.from(mConfig.context)
        val kManager = mConfig.context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (fManager.isHardwareDetected && fManager.hasEnrolledFingerprints() && kManager.isDeviceSecure && kManager.isKeyguardSecure) {
            when (mConfig.mAuthStyle) {

                //AuthenticationStyle.DEVICE_LOCK -> {}
                AuthenticationStyle.FINGERPRINT_DIALOG -> {
                    val activity: AppCompatActivity = mConfig.context as AppCompatActivity

                    val fingerdialog = FingertipDialogFragment()
                    fingerdialog.fManager = fManager
                    fingerdialog.kManager = kManager
                    fingerdialog.listener = this@FingertipAuthenticator
                    fingerdialog.show(activity.supportFragmentManager, "fingertipdialog")

                }
                //AuthenticationStyle.FINGERPRINT_ACTIVITY -> {}
            }
        } else {
            listener.onFingertipAuthFailed("Either device not support fingerprint or no fingerprint added")
        }
    }

    override fun onAuthSuccess() {
        mListener.onFingertipAuthSuccess()
    }

    override fun onAuthFailure(message: String) {
        mListener.onFingertipAuthFailed(message)
    }
}
