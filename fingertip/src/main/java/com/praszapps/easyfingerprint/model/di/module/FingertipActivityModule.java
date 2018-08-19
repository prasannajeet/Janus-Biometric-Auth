package com.praszapps.easyfingerprint.model.di.module;

import com.praszapps.easyfingerprint.MVP.FingertipMVPContract;
import com.praszapps.easyfingerprint.presenter.FingertipActivityPresenter;
import com.praszapps.easyfingerprint.view.FingertipActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class FingertipActivityModule {

    @Provides
    @Named("activityView")
    FingertipMVPContract.View provideFingerprintView(FingertipActivity activity) {
        return activity;
    }

    @Provides
    @Named("activityPresenter")
    FingertipMVPContract.Presenter provideFingertipPresenter() {
        return new FingertipActivityPresenter();
    }
}