package com.praszapps.fingertip.model.di.module

import com.praszapps.fingertip.model.repository.FingertipRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FingertipRepoModule {

    @Provides
    @Singleton
    fun provideRepository(): FingertipRepository {
        return FingertipRepository()
    }
}