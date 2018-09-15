package com.praszapps.fingertip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.praszapps.fingertip.manager.AuthenticationStyle;
import com.praszapps.fingertip.manager.FingertipAuthConfig;
import com.praszapps.fingertip.manager.FingertipAuthenticationResult;
import com.praszapps.fingertip.manager.FingertipAuthenticator;

public class SampleUsage extends AppCompatActivity {
    FingertipAuthConfig config = new FingertipAuthConfig();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        config.setAuthenticationStyle(AuthenticationStyle.FINGERPRINT_DIALOG);
        FingertipAuthenticator.INSTANCE.doAuth(config, new FingertipAuthenticationResult() {
            @Override
            public void onFingertipAuthSuccess() {

            }

            @Override
            public void onFingertipAuthFailed(String errorString) {

            }
        });
    }
}