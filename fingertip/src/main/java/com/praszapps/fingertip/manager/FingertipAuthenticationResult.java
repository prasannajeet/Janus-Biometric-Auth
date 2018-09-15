package com.praszapps.fingertip.manager;

public interface FingertipAuthenticationResult {

    void onFingertipAuthSuccess();
    void onFingertipAuthFailed(String errorString);
}
