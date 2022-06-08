package com.planner.mortality.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.planner.mortality.data.entities.UserDataEntity

@Database(
    version = 2,
    exportSchema = false,
    entities = [UserDataEntity::class]
)
abstract class MortalityRoomDatabase : RoomDatabase(), MortalityAppDatabase