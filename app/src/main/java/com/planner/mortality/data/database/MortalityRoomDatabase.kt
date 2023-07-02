package com.planner.mortality.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.planner.mortality.data.entities.RoutineEntity
import com.planner.mortality.data.entities.UserDataEntity

@Database(
    version = 6,
    exportSchema = false,
    entities = [UserDataEntity::class, RoutineEntity::class]
)
abstract class MortalityRoomDatabase : RoomDatabase(), MortalityAppDatabase