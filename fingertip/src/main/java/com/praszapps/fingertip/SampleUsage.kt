package com.praszapps.fingertip

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.praszapps.fingertip.manager.AuthenticationStyle
import com.praszapps.fingertip.manager.FingertipAuthConfig
import com.praszapps.fingertip.manager.FingertipAuthenticationResult
import com.praszapps.fingertip.manager.FingertipAuthenticator

class SampleUsage : AppCompatActivity() {
    private val config = FingertipAuthConfig(AuthenticationStyle.FINGERPRINT_DIALOG, "Error")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FingertipAuthenticator.INSTANCE.doAuth(config, object : FingertipAuthenticationResult {
            override fun onFingertipAuthSuccess() {

            }

            override fun onFingertipAuthFailed(errorString: String) {

            }
        })
    }
}