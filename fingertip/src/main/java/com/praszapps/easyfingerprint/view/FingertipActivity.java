package com.praszapps.easyfingerprint.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.praszapps.easyfingerprint.MVP.FingertipMVPContract;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.DaggerAppCompatActivity;

public class FingertipActivity extends DaggerAppCompatActivity implements FingertipMVPContract.View {

    @Inject
    @Named("activityPresenter")
    public FingertipMVPContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setUpFingerprintViews() {

    }

    @Override
    public void handleNoFingerprintAuthPossible() {

    }

    @Override
    public void onFingerPrintAuthenticationSuccess() {

    }

    @Override
    public void onFingerprintAuthenticationFailed(String text) {

    }
}
