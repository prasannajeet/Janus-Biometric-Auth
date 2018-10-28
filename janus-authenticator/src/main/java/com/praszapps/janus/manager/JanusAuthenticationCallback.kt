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

import androidx.annotation.Keep

/**
 * Callback methods for to pass results of the biometric authentication done with
 * [JanusAuthenticator.authenticate] method
 * @since 0.2.1
 */
@Keep
interface JanusAuthenticationCallback {

    /**
     * Callback method for failed authentication
     * @since 0.3.3
     * @param authenticationResponse [JanusAuthenticationResponse] response sealed class object
     * that passes the relevant authentication result.
     * [JanusAuthenticationResponse.BioMetricAuthenticationSuccessful]
     * if biometric authentication successful
     * [JanusAuthenticationResponse.BiometricsUnsupported]
     * if device doesn't support biometric authentication
     * [JanusAuthenticationResponse.BiometricsChanged]
     * if the biometrics of the device was changes after previous authentication
     * [JanusAuthenticationResponse.BiometricAuthenticationError]
     * if there was an error during biometric authentication
     */
    fun onAuthenticationResponse(authenticationResponse: JanusAuthenticationResponse)
}