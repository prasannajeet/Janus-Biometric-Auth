package com.praszapps.easyfingerprint.model.repository;

import android.content.Context;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;

import com.praszapps.easyfingerprint.MVP.FingerprintMVPContract;

public class FingerPrintRepository extends FingerprintManagerCompat.AuthenticationCallback implements FingerprintMVPContract.IFingerprintRepository {

    private FingerprintManagerCompat mFingerprintManager;
    private CancellationSignal mSignal = null;
    private Context mContext;

    private FingerPrintRepository(){}

    public FingerPrintRepository(Context context) {
        this.mContext = context;
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
