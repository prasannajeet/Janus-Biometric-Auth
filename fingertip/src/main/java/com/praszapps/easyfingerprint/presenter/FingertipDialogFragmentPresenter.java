package com.praszapps.easyfingerprint.presenter;


import com.praszapps.easyfingerprint.MVP.FingertipMVPContract;
import com.praszapps.easyfingerprint.model.repository.FingertipRepository;

import javax.inject.Inject;
import javax.inject.Named;

public class FingertipDialogFragmentPresenter implements FingertipMVPContract.Presenter {

    private FingertipMVPContract.View mView = null;
    private FingertipRepository mFingerprintRepository = null;

    @Inject
    public FingertipDialogFragmentPresenter(@Named("dialogFragmentView") FingertipMVPContract.View mView, FingertipRepository mFingerprintRepository) {
        this.mView = mView;
        this.mFingerprintRepository = mFingerprintRepository;
    }

    private FingertipDialogFragmentPresenter() {
    }

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
