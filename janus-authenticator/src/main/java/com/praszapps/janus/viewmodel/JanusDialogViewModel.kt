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

package com.praszapps.janus.viewmodel

import android.annotation.TargetApi
import android.app.Application
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.praszapps.janus.R
import com.praszapps.janus.model.FingerprintInitializationKeyInvalidation
import com.praszapps.janus.model.JanusResponseModel
import com.praszapps.janus.model.JanusResult
import com.praszapps.janus.model.repository.JanusSecureProvider
import kotlinx.coroutines.runBlocking

/**
 * [AndroidViewModel] class for Fragment
 */
internal class JanusDialogViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private val fingerprintManager = FingerprintManagerCompat.from(context)
    private val mFingerprintRepository = JanusSecureProvider()

    /**
     * Check if device is capable to doing biometric authentication, if true then initate keystore and return true
     */
    @TargetApi(24)
    internal fun initiateFingerprintAuthentication(): JanusResult<FingerprintInitializationKeyInvalidation> = runBlocking {
        return@runBlocking mFingerprintRepository.initialize()
    }

    /**
     * Perform biometric authentication
     */
    internal fun authenticateViaFingerprint(): LiveData<JanusResponseModel> {

        val biometricAuthLiveData = MutableLiveData<JanusResponseModel>()

        mFingerprintRepository.startFingerprintTracking(fingerprintManager, object : FingerprintManagerCompat.AuthenticationCallback() {

            override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
                biometricAuthLiveData.value = JanusResponseModel(true)
            }

            override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
                biometricAuthLiveData.value = JanusResponseModel(messageId = errMsgId, message = errString.toString())
            }

            override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
                biometricAuthLiveData.value = JanusResponseModel(messageId = helpMsgId, message = helpString.toString())
            }

            override fun onAuthenticationFailed() {
                biometricAuthLiveData.value = JanusResponseModel(message = context.getString(R.string.fingerprint_auth_failed))
            }
        })
        return biometricAuthLiveData
    }

    /**
     * Cancel biometric authentication
     */
    internal fun cancelFingerprintDetection() {
        mFingerprintRepository.stopFingerprintTracking()
    }

}