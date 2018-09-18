package com.praszapps.fingertip.manager

import android.support.annotation.Keep

/**
 * Callback methods for [FingertipAuthenticator.doFingerprintAuthentication] method
 * @since 0.2.1
 */
@Keep
interface FingertipAuthenticationResult {

    /**
     * Callback method for successful authentication
     * @since 0.2.1
     */
    fun onFingertipAuthSuccess()

    /**
     * Callback method for failed authentication
     * @since 0.2.1
     * @param errorType [FingertipErrorType] sealed object representing the error ocurred
     */
    fun onFingertipAuthFailed(errorType: FingertipErrorType)
}