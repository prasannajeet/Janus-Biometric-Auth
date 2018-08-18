package com.praszapps.easyfingerprint.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.praszapps.easyfingerprint.MVP.FingerprintMVPContract;

import dagger.android.AndroidInjection;

public class FingerprintActivity extends AppCompatActivity implements FingerprintMVPContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
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
