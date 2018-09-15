package com.praszapps.fingertip.manager;

import com.praszapps.fingertip.model.repository.FingertipRepository;

import javax.inject.Inject;

public enum FingertipAuthenticator {

    INSTANCE;

    @Inject
    FingertipRepository mRepository;

    public void doAuth(FingertipAuthConfig mConfig, FingertipAuthenticationResult callback) throws IllegalStateException {

        if(mRepository.isDeviceFingerprintAuthReady()) {
            switch (mConfig.getmAuthStyle()) {
                case DEVICE_LOCK:
                    break;
                case FINGERPRINT_DIALOG:
                    break;
                case FINGERPRINT_ACTIVITY:
                    break;
            }
            callback.onFingertipAuthSuccess();
        } else {
            callback.onFingertipAuthFailed("Device doesn't have any fingerprints");
        }
    }
}
