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
 * This sealed class that wraps the different result scenarios for
 * the [JanusAuthenticationCallback.onAuthenticationResponse] callback in [JanusAuthenticator]
 * authenticate functions
 * @author Prasannajeet Pani
 * @since 0.2.1
 */
@Keep
sealed class JanusAuthenticationResponse {

    /**
     * Success instance
     */
    @Keep
    object BiometricAuthenticationSuccessful : JanusAuthenticationResponse()

    /**
     * Error instance for biometric authentication unsupported
     */
    @Keep
    object BiometricsUnsupported : JanusAuthenticationResponse()

    /**
     * Instance for biometric credentials changed, such as fingerprint added or removed
     */
    /*@Keep
    object BiometricsChanged : JanusAuthenticationResponse()*/

    /**
     * Instance for any error occurred during authentication
     * @param errorMessage Display message to show to the user for the error
     */
    @Keep
    data class BiometricAuthenticationError(
            /**Error message**/
            val errorMessage: String) : JanusAuthenticationResponse()
}