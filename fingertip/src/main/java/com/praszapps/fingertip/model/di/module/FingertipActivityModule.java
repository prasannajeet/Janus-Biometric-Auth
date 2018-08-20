package com.praszapps.fingertip.model.di.module;

import com.praszapps.fingertip.MVP.FingertipMVPContract;
import com.praszapps.fingertip.presenter.FingertipActivityPresenter;
import com.praszapps.fingertip.view.FingertipActivity;

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