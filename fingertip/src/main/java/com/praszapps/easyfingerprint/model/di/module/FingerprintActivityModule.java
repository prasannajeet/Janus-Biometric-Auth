package com.praszapps.easyfingerprint.model.di.module;

import com.praszapps.easyfingerprint.MVP.FingerprintMVPContract;
import com.praszapps.easyfingerprint.model.repository.FingerPrintRepository;
import com.praszapps.easyfingerprint.presenter.FingerprintActivityPresenter;
import com.praszapps.easyfingerprint.view.FingerprintActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class FingerprintActivityModule {

    @Provides
    FingerprintMVPContract.View provideFingerprintView(FingerprintActivity activity) {
        return activity;
    }

    @Provides
    FingerprintMVPContract.Presenter<FingerprintMVPContract.View> providerFingerprintPresenter(FingerprintMVPContract.View view, FingerPrintRepository repository) {
        return new FingerprintActivityPresenter(view, repository);
    }


}