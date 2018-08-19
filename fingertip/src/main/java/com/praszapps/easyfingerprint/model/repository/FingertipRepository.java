package com.praszapps.easyfingerprint.model.repository;

import android.content.Context;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;

import com.praszapps.easyfingerprint.MVP.FingertipMVPContract;

public class FingertipRepository extends FingerprintManagerCompat.AuthenticationCallback implements FingertipMVPContract.IFingerprintRepository {

    private static FingertipRepository INSTANCE = null;
    private FingerprintManagerCompat mFingerprintManager;
    private CancellationSignal mSignal = null;
    private Context mContext;

    private FingertipRepository() {
    }


    private FingertipRepository(Context context) {
        this.mContext = context;
    }

    public static FingertipRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new FingertipRepository(context);
        }
        return INSTANCE;
    }

    @Override
    public void initalize() {
        mFingerprintManager = FingerprintManagerCompat.from(mContext);
        mSignal = new CancellationSignal();
    }

    @Override
    public void startFingerprintTracking() {

    }

    @Override
    public void stopFingerprintTracking() {

    }

    @Override
    public boolean isFingerprintAvailable() {
        return mFingerprintManager != null && mFingerprintManager.isHardwareDetected() && mFingerprintManager.hasEnrolledFingerprints();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        super.onAuthenticationHelp(helpMsgId, helpString);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        super.onAuthenticationError(errMsgId, errString);
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
    }
}
