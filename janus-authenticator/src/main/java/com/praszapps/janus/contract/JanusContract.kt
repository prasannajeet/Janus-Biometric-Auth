/*
 *    Copyright [2018] [Prasannajeet Pani]
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

package com.praszapps.janus.contract

import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import com.praszapps.janus.model.JanusResponseModel

internal interface JanusContract {

    interface IView {
        fun initialize()
        fun setUpFingerprintViews()
        fun onFingerPrintAuthenticationSuccess()
        fun onFingerprintAuthenticationFailed(id: Int = -2000, text: String)

    }

    interface IPresenter {
        fun initialize()
        fun authenticateViaFingerprint()
        fun cancelFingerprintDetection()
    }

    interface IModel {
        suspend fun initialize(): JanusResponseModel
        fun startFingerprintTracking(listener: FingerprintManagerCompat.AuthenticationCallback)
        fun stopFingerprintTracking()
    }
}
