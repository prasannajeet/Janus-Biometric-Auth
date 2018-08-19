package com.praszapps.easyfingerprint.model.di.module;

import com.praszapps.easyfingerprint.MVP.FingertipMVPContract;
import com.praszapps.easyfingerprint.model.repository.FingertipRepository;
import com.praszapps.easyfingerprint.presenter.FingertipDialogFragmentPresenter;
import com.praszapps.easyfingerprint.view.FingertipDialogFragment;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class FingertipDialogFragmentModule {

    @Provides
    @Named("dialogFragmentView")
    FingertipMVPContract.View provideFingertipView(FingertipDialogFragment dialogFragment) {
        return dialogFragment;
    }

    @Provides
    FingertipMVPContract.Presenter provideFingertipPresenter(FingertipMVPContract.View view, FingertipRepository repository) {
        return new FingertipDialogFragmentPresenter(view, repository);
    }
}
