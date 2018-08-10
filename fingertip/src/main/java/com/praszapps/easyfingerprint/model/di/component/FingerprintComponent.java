package com.praszapps.easyfingerprint.model.di.component;

import com.praszapps.easyfingerprint.presenter.FingerPrintPresenter;
import com.praszapps.easyfingerprint.model.di.module.FingerprintRepoModule;

import dagger.Component;

@Component(modules = {FingerprintRepoModule.class})
public interface FingerprintComponent {

    void inject(FingerPrintPresenter presenter);

}
