package com.praszapps.janus.manager

import android.support.annotation.Keep

/**
 * Callback methods for [JanusAuthenticator.doFingerprintAuthentication] method
 * @since 0.2.1
 */
@Keep
interface JanusAuthResultListener {

    /**
     * Callback method for successful authentication
     * @since 0.2.1
     */
    fun onAuthenticationSuccess()

    /**
     * Callback method for failed authentication
     * @since 0.2.1
     * @param errorType [JanusErrorType] sealed object representing the error ocurred
     */
    fun onAuthenticationFail(errorType: JanusErrorType)
}