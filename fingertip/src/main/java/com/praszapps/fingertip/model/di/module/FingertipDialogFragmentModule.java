package com.praszapps.fingertip.model.di.module;

import com.praszapps.fingertip.MVP.FingertipMVPContract;
import com.praszapps.fingertip.presenter.FingertipDialogFragmentPresenter;
import com.praszapps.fingertip.view.FingertipDialogFragment;

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