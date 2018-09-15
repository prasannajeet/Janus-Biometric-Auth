package com.praszapps.fingertip.model.di.module

import com.praszapps.fingertip.MVP.FingertipMVPContract
import com.praszapps.fingertip.presenter.FingertipDialogFragmentPresenter
import com.praszapps.fingertip.view.FingertipDialogFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class FingertipDialogFragmentModule {

    @Provides
    @Named("dialogFragmentView")
    internal fun provideFingertipDialogView(dialogFragment: FingertipDialogFragment): FingertipMVPContract.View {
        return dialogFragment
    }

    @Provides
    @Named("dialogFragmentPresenter")
    internal fun provideFingertipDialogPresenter(): FingertipMVPContract.Presenter {
        return FingertipDialogFragmentPresenter()
    }
}