package com.planner.mortality.data.database

import com.planner.mortality.data.dao.UserDataDao

interface MortalityAppDatabase {

    fun userDataDao(): UserDataDao
}