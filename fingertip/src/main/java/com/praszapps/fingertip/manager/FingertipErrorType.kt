package com.praszapps.fingertip.manager

/**
 * This sealed class represents the different error scenarios for the [FingertipAuthenticationResult.onFingertipAuthFailed] callback in [FingertipAuthenticator]
 * @author Prasannajeet Pani
 * @since 0.2.1
 */
sealed class FingertipErrorType {
    /**
     * Error instance for device API level less than 23
     */
    object DeviceApiLevelBelow23 : FingertipErrorType()

    /**
     * Error class for any error message to be passed to the app during the failure of authentication via fingerprint
     */
    data class ErrorDuringFingerprintAuthentication(val message:String): FingertipErrorType()
}