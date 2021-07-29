package com.day.timer.di

import com.day.timer.data.PreferenceRepository
import com.day.timer.data.PreferenceRepositoryImpl
import com.day.timer.data.TimeRepository
import com.day.timer.data.TimeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun providesPreferenceRepository(preferenceRepositoryImpl: PreferenceRepositoryImpl):
            PreferenceRepository = preferenceRepositoryImpl

    @Provides
    @ViewModelScoped
    fun providesTimeRepository(timeRepositoryImpl: TimeRepositoryImpl):
            TimeRepository = timeRepositoryImpl
}