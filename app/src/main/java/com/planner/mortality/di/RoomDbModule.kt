package com.planner.mortality.di

import android.content.Context
import android.os.Debug
import androidx.room.Room
import com.planner.mortality.data.database.MortalityRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomDbModule {

    @Singleton
    @Provides
    fun providesRoomDb(
        @ApplicationContext applicationContext: Context,
    ): MortalityRoomDatabase {
        val builder = Room.databaseBuilder(applicationContext,
            MortalityRoomDatabase::class.java,
            DATABASE_NAME).fallbackToDestructiveMigration()
        return builder.build()
    }

    companion object {
        const val DATABASE_NAME = "mortality.db"
    }
}

@InstallIn(SingletonComponent::class)
@Module
class DaoModule {



}