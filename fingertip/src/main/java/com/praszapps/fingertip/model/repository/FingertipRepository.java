package com.praszapps.fingertip.model.repository;

import android.app.KeyguardManager;
import android.content.Context;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;

import com.praszapps.fingertip.MVP.FingertipMVPContract;

import javax.inject.Inject;

public class FingertipRepository extends FingerprintManagerCompat.AuthenticationCallback implements FingertipMVPContract.IFingerprintRepository {

    private FingerprintManagerCompat mFingerprintManager;
    private CancellationSignal mSignal = null;
    @Inject
    Context mContext;
    private KeyguardManager mKeyguardManager = null;

    @Override
    public void initalize() {
        mFingerprintManager = FingerprintManagerCompat.from(mContext);
        mSignal = new CancellationSignal();
        mKeyguardManager = (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
    }

    public boolean isDeviceFingerprintAuthReady() {
        return mFingerprintManager != null && mKeyguardManager != null && mFingerprintManager.isHardwareDetected() && mFingerprintManager.hasEnrolledFingerprints() && mKeyguardManager.isKeyguardSecure();
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
