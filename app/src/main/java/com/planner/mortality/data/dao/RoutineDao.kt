package com.planner.mortality.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.planner.mortality.data.entities.RoutineEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RoutineDao : EntityDao<RoutineEntity>() {

    @Query("Select * from routines")
    abstract fun getAllRoutines(): Flow<List<RoutineEntity>>

}