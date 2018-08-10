package com.praszapps.easyfingerprint.model.di.component;

import com.praszapps.easyfingerprint.model.di.module.ContextModule;
import com.praszapps.easyfingerprint.view.FingerprintDialogFragment;

import dagger.Component;

@Component(modules = ContextModule.class)
public interface ContextComponent {
    void inject(FingerprintDialogFragment fingerprintDialogFragment);
}
