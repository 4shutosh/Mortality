package com.planner.mortality.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userData")
data class UserDataEntity(
    @PrimaryKey override val id: Long = 0L,
    val dateOfBirthTimeStamp: Long,
    val lifeExpectancyYears: Int,
) : BaseEntity {
}