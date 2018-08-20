package com.praszapps.fingertip.MVP;

public interface FingertipMVPContract {

    interface View {
        void setUpFingerprintViews();
        void handleNoFingerprintAuthPossible();
        void onFingerPrintAuthenticationSuccess();
        void onFingerprintAuthenticationFailed(String text);

    }

    interface Presenter {
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
