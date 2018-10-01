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

package com.praszapps.janus.presenter

import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import com.praszapps.janus.contract.JanusContract
import com.praszapps.janus.model.repository.JanusSecureProvider
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch

internal class JanusBiometricPresenter(IView: JanusContract.IView) : JanusContract.IPresenter {

    private val mView = IView

    private val mFingerprintRepository = JanusSecureProvider()

    private val listener = object : FingerprintManagerCompat.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
            mView.onFingerPrintAuthenticationSuccess()
        }

        override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
            mView.onFingerprintAuthenticationFailed(errMsgId, errString.toString())
        }

        override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
            mView.onFingerprintAuthenticationFailed(helpMsgId, helpString.toString())
        }

        override fun onAuthenticationFailed() {
            mView.onFingerprintAuthenticationFailed(text = "Fingerprint not recognized. Try again")
        }
    }

    override fun initialize() {

        GlobalScope.launch {
            val model = mFingerprintRepository.initialize()

            if (model.isSuccess) {
                mView.setUpFingerprintViews()
            } else {
                mView.onFingerprintAuthenticationFailed(text = model.message)
            }
        }
    }

    override fun authenticateViaFingerprint() {

        mFingerprintRepository.startFingerprintTracking(listener)
    }

    override fun cancelFingerprintDetection() {
        mFingerprintRepository.stopFingerprintTracking()
    }
}