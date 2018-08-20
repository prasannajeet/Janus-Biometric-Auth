package com.praszapps.fingertip.presenter;


import com.praszapps.fingertip.MVP.FingertipMVPContract;
import com.praszapps.fingertip.model.repository.FingertipRepository;

import javax.inject.Inject;
import javax.inject.Named;

public class FingertipDialogFragmentPresenter implements FingertipMVPContract.Presenter {

    @Inject
    @Named("dialogFragmentView")
    public FingertipMVPContract.View mView = null;
    @Inject
    public FingertipRepository mFingerprintRepository = null;

    public void init(FingertipMVPContract.View view) {
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
