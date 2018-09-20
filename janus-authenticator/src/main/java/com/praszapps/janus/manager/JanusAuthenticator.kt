package com.praszapps.janus.manager

import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.support.annotation.Keep
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v7.app.AppCompatActivity
import com.praszapps.janus.contract.ManagerViewInteractor
import com.praszapps.janus.view.JanusV23FingerprintSlidingMenu

/**
 * Authenticator class for Janus. Main entry point for the library. Provides functions for authentication and verifying is authentication is possible
 * @author Prasannajeet Pani
 * @since 0.1.0
 */
@TargetApi(23)
@Keep
enum class JanusAuthenticator : ManagerViewInteractor {

    INSTANCE;

    lateinit var mListener: JanusAuthResultListener

    /**
     * The authentication function that initiated the desired type of fingerprint authentication. If the API level of the application on which this function is called is less than 23
     * the method will invoke the failure callback [JanusAuthResultListener.onAuthenticationFail] passing the [JanusErrorType.DeviceApiLevelBelow23] object
     * @param mConfig [JanusAuthConfig] object which specifies the [AuthenticationStyle] to be done alongwith the [Context] object of the calling Activity
     * @param listener [JanusAuthResultListener] callback to denote the calling application for success and failure scenarios
     */
    fun doFingerprintAuthentication(mConfig: JanusAuthConfig, listener: JanusAuthResultListener) {

        mListener = listener
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val fManager = FingerprintManagerCompat.from(mConfig.context)
            val kManager = mConfig.context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

            if (fManager.isHardwareDetected && fManager.hasEnrolledFingerprints() && kManager.isDeviceSecure && kManager.isKeyguardSecure) {
                when (mConfig.mAuthStyle) {

                    //AuthenticationStyle.DEVICE_LOCK -> {}
                    AuthenticationStyle.BIOMETRIC_DIALOG -> {
                        val activity: AppCompatActivity = mConfig.context as AppCompatActivity

                        val fingerdialog = JanusV23FingerprintSlidingMenu()
                        fingerdialog.fManager = fManager
                        fingerdialog.kManager = kManager
                        fingerdialog.listener = this@JanusAuthenticator
                        fingerdialog.show(activity.supportFragmentManager, "Janusdialog")
                    }
                    //AuthenticationStyle.FINGERPRINT_ACTIVITY -> {}
                }
            } else {
                listener.onAuthenticationFail(JanusErrorType.ErrorDuringFingerprintAuthentication("Either device not support fingerprint or no fingerprint added"))
            }
        } else {
            mListener.onAuthenticationFail(JanusErrorType.DeviceApiLevelBelow23)
        }
    }

    /**
     * Helper function to inform application if Janus supports authentication in the current API
     * @return true if API level is below 23 (Marshmallow) else returns false
     */
    fun isApiLevelSupported(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    override fun onAuthSuccess() {
        mListener.onAuthenticationSuccess()
    }

    override fun onAuthFailure(message: String) {
        mListener.onAuthenticationFail(JanusErrorType.ErrorDuringFingerprintAuthentication(message))
    }
}