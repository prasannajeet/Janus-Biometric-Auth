package com.praszapps.fingertip.manager

import android.support.annotation.Keep

/**
 * This sealed class represents the different error scenarios for the [FingertipAuthenticationResult.onFingertipAuthFailed] callback in [FingertipAuthenticator]
 * @author Prasannajeet Pani
 * @since 0.2.1
 */
@Keep
sealed class FingertipErrorType {
    /**
     * Error instance for device API level less than 23
     */
    @Keep
    object DeviceApiLevelBelow23 : FingertipErrorType()

    /**
     * Error class for any error message to be passed to the app during the failure of authentication via fingerprint
     */
    @Keep
    data class ErrorDuringFingerprintAuthentication(val message:String): FingertipErrorType()
}