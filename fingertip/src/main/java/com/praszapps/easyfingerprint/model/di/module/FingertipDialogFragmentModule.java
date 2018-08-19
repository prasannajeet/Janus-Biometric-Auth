package com.praszapps.easyfingerprint.model.di.module;

import com.praszapps.easyfingerprint.MVP.FingertipMVPContract;
import com.praszapps.easyfingerprint.presenter.FingertipDialogFragmentPresenter;
import com.praszapps.easyfingerprint.view.FingertipDialogFragment;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class FingertipDialogFragmentModule {

    @Provides
    @Named("dialogFragmentView")
    FingertipMVPContract.View provideFingertipDialogView(FingertipDialogFragment dialogFragment) {
        return dialogFragment;
    }

    @Provides
    @Named("dialogFragmentPresenter")
    FingertipMVPContract.Presenter provideFingertipDialogPresenter() {
        return new FingertipDialogFragmentPresenter();
    }
}