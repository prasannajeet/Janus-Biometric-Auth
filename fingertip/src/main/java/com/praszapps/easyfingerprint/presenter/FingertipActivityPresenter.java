package com.praszapps.easyfingerprint.presenter;

import com.praszapps.easyfingerprint.MVP.FingertipMVPContract;
import com.praszapps.easyfingerprint.model.repository.FingertipRepository;

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
