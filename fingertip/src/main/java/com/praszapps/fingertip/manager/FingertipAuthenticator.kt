package com.praszapps.fingertip.manager

import com.praszapps.fingertip.model.repository.FingertipRepository

import javax.inject.Inject

enum class FingertipAuthenticator {

    INSTANCE;

    @Inject
    lateinit var mRepository: FingertipRepository

    @Throws(IllegalStateException::class)
    fun doAuth(mConfig: FingertipAuthConfig, callback: FingertipAuthenticationResult) {

        if (mRepository.isDeviceFingerprintAuthReady) {
            when (mConfig.mAuthStyle) {
                AuthenticationStyle.DEVICE_LOCK -> {
                }
                AuthenticationStyle.FINGERPRINT_DIALOG -> {
                }
                AuthenticationStyle.FINGERPRINT_ACTIVITY -> {
                }
            }
            callback.onFingertipAuthSuccess()
        } else {
            callback.onFingertipAuthFailed("Device doesn't have any fingerprints")
        }
    }
}
