package com.praszapps.easyfingerprint.presenter;

import com.praszapps.easyfingerprint.MVP.FingerprintMVPContract;
import com.praszapps.easyfingerprint.model.repository.FingerPrintRepository;

import javax.inject.Inject;

public class FingerprintActivityPresenter implements FingerprintMVPContract.Presenter<FingerprintMVPContract.View> {

    @Inject
    public FingerprintActivityPresenter(FingerprintMVPContract.View view, FingerPrintRepository repository) {
    }

    @Override
    public void init(FingerprintMVPContract.View view) {

    }

    @Override
    public void authenticateViaFingerprint() {

    }

    @Override
    public void stopFingerprintTracking() {

    }
}
