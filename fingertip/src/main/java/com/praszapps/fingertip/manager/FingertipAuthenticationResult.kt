package com.praszapps.fingertip.manager

interface FingertipAuthenticationResult {

    fun onFingertipAuthSuccess()
    fun onFingertipAuthFailed(errorString: String)
}
