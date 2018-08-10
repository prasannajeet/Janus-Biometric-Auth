package com.praszapps.easyfingerprint.MVP;

import android.content.Context;

public interface FingerprintMVPContract {

    interface View {
        void setUpFingerprintViews();
        void handleNoFingerprintAuthPossible();
        void onFingerPrintAuthenticationSuccess();
        void onFingerprintAuthenticationFailed(String text);

    }

    interface Presenter<V extends View> {
        void init(V view);
        void authenticateViaFingerprint();
        void stopFingerprintTracking();
    }

    interface Model {
        void getStandardErrorText();
    }

    interface IFingerprintRepository {
        void initalize();
        void startFingerprintTracking();
        void stopFingerprintTracking();
        boolean isFingerprintAvailable();
    }
}
