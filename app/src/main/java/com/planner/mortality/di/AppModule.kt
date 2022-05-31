package com.planner.mortality.di

import com.planner.mortality.utils.AppCoroutineDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun provideCoroutineDispatchers(): AppCoroutineDispatcher = AppCoroutineDispatcher()

}