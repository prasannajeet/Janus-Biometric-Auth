package com.praszapps.fingertip.presenter;

import com.praszapps.fingertip.MVP.FingertipMVPContract;
import com.praszapps.fingertip.model.repository.FingertipRepository;

import javax.inject.Inject;
import javax.inject.Named;

public class FingertipActivityPresenter implements FingertipMVPContract.Presenter {

    @Inject
    @Named("activityView")
    public FingertipMVPContract.View view;

    @Inject
    FingertipRepository repository;

    @Override
    public void authenticateViaFingerprint() {

    }

    @Override
    public void stopFingerprintTracking() {

    }
}
