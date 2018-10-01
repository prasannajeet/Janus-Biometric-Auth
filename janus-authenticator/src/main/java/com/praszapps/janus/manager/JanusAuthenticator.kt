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
import androidx.annotation.Keep
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.praszapps.janus.R
import com.praszapps.janus.model.JanusResponseModel
import com.praszapps.janus.util.JanusUtil
import com.praszapps.janus.view.JanusFingerprintPrompt
import org.jetbrains.annotations.NotNull

/**
 * Authenticator class for Janus. Main entry point for the library. Provides functions for authentication and verifying is authentication is possible
 * @author Prasannajeet Pani
 * @since 0.1.0
 */
@TargetApi(23)
@Keep
object JanusAuthenticator {

    /**
     * The authentication function that initiated the desired type of fingerprint authentication.
     * If the API level of the application on which this function is called is less than 23
     * the method will invoke the failure callback [JanusAuthenticationCallback.onAuthenticationResponse] passing the [JanusAuthenticationResponse.DeviceApiLevelBelow23] object
     * @since 0.3.3
     * @param janusAuthenticationStyle [JanusAuthenticationStyle] type to specify which type of UI to show
     * @param activity Calling [Activity]
     * @param listener [JanusAuthenticationCallback] callback to denote the calling application for success and failure scenarios
     */
    fun authenticate(janusAuthenticationStyle: JanusAuthenticationStyle, activity: FragmentActivity, listener: JanusAuthenticationCallback) {

        if (JanusUtil.initiate(activity)) {

            when (janusAuthenticationStyle) {

                JanusAuthenticationStyle.BIOMETRIC_DIALOG -> {
                    val liveData = MutableLiveData<JanusResponseModel>()
                    liveData.observe(activity, Observer<JanusResponseModel> { response ->

                        if (response.isSuccess) {
                            listener.onAuthenticationResponse(JanusAuthenticationResponse.Success)
                        } else {
                            listener.onAuthenticationResponse(JanusAuthenticationResponse.ErrorDuringFingerprintAuthentication(response.message))
                        }
                    })
                    val fingerDialog = JanusFingerprintPrompt(liveData)
                    fingerDialog.show(activity.supportFragmentManager, JanusUtil.tag)
                }
            }
        } else {
            listener.onAuthenticationResponse(JanusAuthenticationResponse.ErrorDuringFingerprintAuthentication(activity.getString(R.string.no_fp)))
        }
    }

    /**
     * The authentication function that initiated the desired type of fingerprint authentication and return a [MutableLiveData] of [JanusAuthenticationResponse] type
     * If the API level of the application on which this function is called is less than 23
     * the method will return the [JanusAuthenticationResponse.DeviceApiLevelBelow23] object via the LiveData object
     * @since 0.4.7
     * @param janusAuthenticationStyle [JanusAuthenticationStyle] type to specify which type of UI to show
     * @param activity Calling [Activity]
     * @return [MutableLiveData] of [JanusAuthenticationResponse] type to calling app to observe
     */
    fun authenticate(@NotNull janusAuthenticationStyle: JanusAuthenticationStyle, @NotNull activity: FragmentActivity): MutableLiveData<JanusAuthenticationResponse> {

        val resultLiveData = MutableLiveData<JanusAuthenticationResponse>()

        authenticate(janusAuthenticationStyle, activity, object : JanusAuthenticationCallback {
            override fun onAuthenticationResponse(authenticationResponse: JanusAuthenticationResponse) {
                resultLiveData.value = authenticationResponse
            }
        })
        return resultLiveData
    }
}