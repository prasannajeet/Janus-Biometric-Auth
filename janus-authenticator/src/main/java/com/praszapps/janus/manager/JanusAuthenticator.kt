/*
 *    Copyright 2018 Prasannajeet Pani
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.praszapps.janus.manager

import android.annotation.TargetApi
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Build.VERSION_CODES.P
import android.os.CancellationSignal
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.praszapps.janus.R
import com.praszapps.janus.model.JanusResponseModel
import com.praszapps.janus.view.JanusFingerprintPrompt
import org.jetbrains.annotations.NotNull

/**
 * Authenticator manager for Janus. Main entry point for the library. Provides functions for biometric authentication and
 * verifying if biometric authentication is possible
 * @author Prasannajeet Pani
 * @since 0.1.0
 */
@TargetApi(23)
@Keep
object JanusAuthenticator {

    /**
     * The authentication function to initiate the desired type of biometric authentication as
     * specified by the [JanusAuthenticationStyle] enum. The method will invoke the
     * callback [JanusAuthenticationCallback.onAuthenticationResponse] with various sealed class instances as
     * results of the biometric authentication result.
     * If the authentication is successful, the response class will
     * pass the [JanusAuthenticationResponse.BiometricAuthenticationSuccessful] sealed class.
     * If the device calling this function does not support biometric authentication
     * (either due to unsupported hardware or API level), the response class will
     * pass the [JanusAuthenticationResponse.BiometricsUnsupported] sealed class.
     * If the device has previously performed biometric authentication and then the biometric was changed
     * (fingerprint added/removed), the response class will
     * pass the [JanusAuthenticationResponse.BiometricAuthenticationSuccessful] sealed class.
     * If there is any error during authentication the error message, the response class will
     * pass the [JanusAuthenticationResponse.BiometricAuthenticationError] sealed class.
     * @since 0.3.3
     * @param janusAuthenticationStyle [JanusAuthenticationStyle] type to specify which type of UI to show
     * @param activity [FragmentActivity] that calls the function, either through itself or a fragment
     * @param listener [JanusAuthenticationCallback] callback to denote the calling application for success and failure scenarios
     */
    fun authenticate(janusAuthenticationStyle: JanusAuthenticationStyle, activity: FragmentActivity,
                     listener: JanusAuthenticationCallback) {
        when (janusAuthenticationStyle) {
            JanusAuthenticationStyle.BIOMETRIC_DIALOG -> {
                if (isBiometricAuthenticationSupported(activity)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        setupBiometricPrompt(activity, listener)
                    } else {
                        setupFingerprintPrompt(activity, listener)
                    }
                } else {
                    listener.onAuthenticationResponse(JanusAuthenticationResponse.BiometricsUnsupported)
                }
            }
        }
    }

    /**
     * The authentication function to initiate the desired type of biometric authentication as
     * specified by the [JanusAuthenticationStyle] enum. The method will return [LiveData] wrapper
     * of [JanusAuthenticationResponse] type with various sealed class instances as
     * results of the biometric authentication result.
     * @since 0.4.7
     * @param janusAuthenticationStyle [JanusAuthenticationStyle] type to specify which type of UI to show
     * @param activity [FragmentActivity] that calls the function, either through itself or a fragment
     * @return [LiveData] wrapper of [JanusAuthenticationResponse] type to calling app to observe.
     * If the authentication is successful, the response class will
     * pass the [JanusAuthenticationResponse.BiometricAuthenticationSuccessful] sealed class.
     * If the device calling this function does not support biometric authentication
     * (either due to unsupported hardware or API level), the response class will
     * pass the [JanusAuthenticationResponse.BiometricsUnsupported] sealed class.
     * If the device has previously performed biometric authentication and then the biometric was changed
     * (fingerprint added/removed), the response class will
     * pass the [JanusAuthenticationResponse.BiometricAuthenticationSuccessful] sealed class.
     * If there is any error during authentication the error message, the response class will
     * pass the [JanusAuthenticationResponse.BiometricAuthenticationError] sealed class.
     */
    fun authenticate(@NotNull janusAuthenticationStyle: JanusAuthenticationStyle,
                     @NotNull activity: FragmentActivity): LiveData<JanusAuthenticationResponse> {
        val resultLiveData = MutableLiveData<JanusAuthenticationResponse>()
        authenticate(janusAuthenticationStyle, activity, object : JanusAuthenticationCallback {
            override fun onAuthenticationResponse(authenticationResponse: JanusAuthenticationResponse) {
                resultLiveData.value = authenticationResponse
            }
        })
        return resultLiveData
    }

    /**
     * The authentication function to initiate biometric authentication. The method will return [LiveData] wrapper
     * of [JanusAuthenticationResponse] type with various sealed class instances as
     * results of the biometric authentication result.
     * @since 0.6.0
     * @param activity [FragmentActivity] that calls the function, either through itself or a fragment
     * @return [LiveData] wrapper of [JanusAuthenticationResponse] type to calling app to observe.
     * If the authentication is successful, the response class will
     * pass the [JanusAuthenticationResponse.BiometricAuthenticationSuccessful] sealed class.
     * If the device calling this function does not support biometric authentication
     * (either due to unsupported hardware or API level), the response class will
     * pass the [JanusAuthenticationResponse.BiometricsUnsupported] sealed class.
     * If the device has previously performed biometric authentication and then the biometric was changed
     * (fingerprint added/removed), the response class will
     * pass the [JanusAuthenticationResponse.BiometricAuthenticationSuccessful] sealed class.
     * If there is any error during authentication the error message, the response class will
     * pass the [JanusAuthenticationResponse.BiometricAuthenticationError] sealed class.
     */
    fun authenticate(@NotNull activity: FragmentActivity): LiveData<JanusAuthenticationResponse> {
        return authenticate(JanusAuthenticationStyle.BIOMETRIC_DIALOG, activity)
    }

    /**
     * The authentication function to initiate biometric authentication. The method will invoke the
     * callback [JanusAuthenticationCallback.onAuthenticationResponse] with various sealed class instances as
     * results of the biometric authentication result.
     * If the authentication is successful, the response class will
     * pass the [JanusAuthenticationResponse.BiometricAuthenticationSuccessful] sealed class.
     * If the device calling this function does not support biometric authentication
     * (either due to unsupported hardware or API level), the response class will
     * pass the [JanusAuthenticationResponse.BiometricsUnsupported] sealed class.
     * If the device has previously performed biometric authentication and then the biometric was changed
     * (fingerprint added/removed), the response class will
     * pass the [JanusAuthenticationResponse.BiometricAuthenticationSuccessful] sealed class.
     * If there is any error during authentication the error message, the response class will
     * pass the [JanusAuthenticationResponse.BiometricAuthenticationError] sealed class.
     * @since 0.6.0
     * @param activity Calling [Activity]
     * @param listener [JanusAuthenticationCallback] callback to denote the calling application for success and failure scenarios
     */
    fun authenticate(activity: FragmentActivity, listener: JanusAuthenticationCallback) {
        authenticate(JanusAuthenticationStyle.BIOMETRIC_DIALOG, activity, listener)
    }

    /**
     * Utility method to check whether biometric authentication is supported on the device
     * @since 0.6.0
     * @param context [Context] of the activity/fragment calling the method
     * @return `true` if biometric authentication is supported, `false` otherwise
     */
    private fun isBiometricAuthenticationSupported(context: Context): Boolean {
        val fingerprintManager = FingerprintManagerCompat.from(context)
        val keyGuardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            (context.packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT) && keyGuardManager.isDeviceSecure
                    && keyGuardManager.isKeyguardSecure)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            (context.packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT) && fingerprintManager.isHardwareDetected
                    && fingerprintManager.hasEnrolledFingerprints() && keyGuardManager.isDeviceSecure
                    && keyGuardManager.isKeyguardSecure && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        } else {
            false
        }
    }

    private fun setupFingerprintPrompt(activity: FragmentActivity, listener: JanusAuthenticationCallback) {
        val liveData = MutableLiveData<JanusResponseModel>()
        liveData.observe(activity, Observer<JanusResponseModel> { response ->
            when {
                response.isSuccess -> listener.onAuthenticationResponse(JanusAuthenticationResponse.BiometricAuthenticationSuccessful)
                response.isKeyInvalidated -> listener.onAuthenticationResponse(JanusAuthenticationResponse.BiometricAuthenticationSuccessful)
                else -> listener.onAuthenticationResponse(JanusAuthenticationResponse.BiometricAuthenticationError(response.message))
            }
        })
        val fingerDialog = JanusFingerprintPrompt(liveData)
        fingerDialog.show(activity.supportFragmentManager, null)
    }

    @RequiresApi(P)
    private fun setupBiometricPrompt(context: Context, listener: JanusAuthenticationCallback) {

        val biometricPrompt = BiometricPrompt.Builder(context)
                .setTitle(context.getString(R.string.sign_in))
                .setSubtitle(context.getString(R.string.confirm_fingerprint_to_continue))
                .setDescription(context.getString(R.string.touch_sensor))
                .setNegativeButton(context.getString(R.string.cancel), context.mainExecutor,
                        DialogInterface.OnClickListener { _, _ -> })
                .build()

        biometricPrompt.authenticate(CancellationSignal(), context.mainExecutor, object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                listener.onAuthenticationResponse(JanusAuthenticationResponse.BiometricAuthenticationSuccessful)
            }

            override fun onAuthenticationFailed() {
                listener.onAuthenticationResponse(JanusAuthenticationResponse.BiometricAuthenticationError("Unknown error"))
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                listener.onAuthenticationResponse(JanusAuthenticationResponse.BiometricAuthenticationError(errString.toString()))
            }

            override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                listener.onAuthenticationResponse(JanusAuthenticationResponse.BiometricAuthenticationError(helpString.toString()))
            }
        })
    }
}