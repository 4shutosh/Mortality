package com.planner.mortality.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.planner.mortality.data.entities.RoutineEntity

@Dao
abstract class RoutineDao : EntityDao<RoutineEntity>() {

    @Query("Select * from routines")
    abstract fun getAllRoutines(): RoutineEntity

}