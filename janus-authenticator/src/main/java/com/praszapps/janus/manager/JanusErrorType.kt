package com.praszapps.janus.manager

import android.support.annotation.Keep

/**
 * This sealed class represents the different error scenarios for the [JanusAuthResultListener.onAuthenticationFail] callback in [JanusAuthenticator]
 * @author Prasannajeet Pani
 * @since 0.2.1
 */
@Keep
sealed class JanusErrorType {
    /**
     * Error instance for device API level less than 23
     */
    @Keep
    object DeviceApiLevelBelow23 : JanusErrorType()

    /**
     * Error class for any error message to be passed to the app during the failure of authentication via fingerprint
     */
    @Keep
    data class ErrorDuringFingerprintAuthentication(val message: String) : JanusErrorType()
}