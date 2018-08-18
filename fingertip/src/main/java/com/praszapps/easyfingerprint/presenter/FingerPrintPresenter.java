package com.praszapps.easyfingerprint.presenter;


import com.praszapps.easyfingerprint.MVP.FingerprintMVPContract;
import com.praszapps.easyfingerprint.model.repository.FingerPrintRepository;

import javax.inject.Inject;

public class FingerPrintPresenter implements FingerprintMVPContract.Presenter<FingerprintMVPContract.View> {


    private FingerprintMVPContract.View mView;

    @Inject
    public FingerPrintRepository mFingerprintRepository;


    @Override
    public void init(FingerprintMVPContract.View view) {
        this.mView = view;

        if(!mFingerprintRepository.isFingerprintAvailable()) {
            mView.setUpFingerprintViews();
        } else {
            mView.handleNoFingerprintAuthPossible();
        }
    }

    @Override
    public void authenticateViaFingerprint() {
        mFingerprintRepository.startFingerprintTracking();
    }

    @Override
    public void stopFingerprintTracking() {
        mFingerprintRepository.stopFingerprintTracking();
    }
}
