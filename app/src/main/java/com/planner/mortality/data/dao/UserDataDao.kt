package com.planner.mortality.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.planner.mortality.data.entities.UserDataEntity

@Dao
abstract class UserDataDao : EntityDao<UserDataEntity>() {

    @Query("Select * from userData where id is :userId")
    abstract fun getUserData(userId: Long = 0): UserDataEntity?


}