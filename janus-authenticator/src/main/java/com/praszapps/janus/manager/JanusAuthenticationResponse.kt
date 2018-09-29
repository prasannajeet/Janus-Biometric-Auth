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

package com.praszapps.janus.manager

import androidx.annotation.Keep

/**
 * This sealed class represents the different error scenarios for the [JanusAuthenticationCallback.onAuthenticationResponse] callback in [JanusAuthenticator]
 * @author Prasannajeet Pani
 * @since 0.2.1
 */
@Keep
sealed class JanusAuthenticationResponse {

    /**
     * Success instance
     */
    @Keep
    object Success : JanusAuthenticationResponse()


    /**
     * Error instance for device API level less than 23
     */
    @Keep
    object DeviceApiLevelBelow23 : JanusAuthenticationResponse()

    /**
     * Error class for any error message to be passed to the app during the failure of authentication via fingerprint
     */
    @Keep
    data class ErrorDuringFingerprintAuthentication(
            /**Error message**/
            val errorMessage: String) : JanusAuthenticationResponse()
}