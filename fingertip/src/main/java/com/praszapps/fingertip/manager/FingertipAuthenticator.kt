package com.praszapps.fingertip.manager

import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v7.app.AppCompatActivity
import com.praszapps.fingertip.MVP.ManagerViewInteractor
import com.praszapps.fingertip.view.FingertipDialogFragment

/**
 * Authenticator class for Fingertip. Main entry point for the library. Provides functions for authentication and verifying is authentication is possible
 * @author Prasannajeet Pani
 * @since 0.1.0
 */
@TargetApi(23)
enum class FingertipAuthenticator : ManagerViewInteractor {

    INSTANCE;

    lateinit var mListener: FingertipAuthenticationResult

    /**
     * The authentication function that initiated the desired type of fingerprint authentication. If the API level of the application on which this function is called is less than 23
     * the method will invoke the failure callback [FingertipAuthenticationResult.onFingertipAuthFailed] passing the [FingertipErrorType.DeviceApiLevelBelow23] object
     * @param mConfig [FingertipAuthConfig] object which specifies the [AuthenticationStyle] to be done alongwith the [Context] object of the calling Activity
     * @param listener [FingertipAuthenticationResult] callback to denote the calling application for success and failure scenarios
     */
    fun doFingerprintAuthentication(mConfig: FingertipAuthConfig, listener: FingertipAuthenticationResult) {

        mListener = listener
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
                listener.onFingertipAuthFailed(FingertipErrorType.ErrorDuringFingerprintAuthentication("Either device not support fingerprint or no fingerprint added"))
            }
        } else {
            mListener.onFingertipAuthFailed(FingertipErrorType.DeviceApiLevelBelow23)
        }
    }

    /**
     * Helper function to inform application if Fingertip supports authentication in the current API
     * @return true if API level is below 23 (Marshmallow) else returns false
     */
    fun isApiLevelSupported(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    override fun onAuthSuccess() {
        mListener.onFingertipAuthSuccess()
    }

    override fun onAuthFailure(message: String) {
        mListener.onFingertipAuthFailed(FingertipErrorType.ErrorDuringFingerprintAuthentication(message))
    }
}