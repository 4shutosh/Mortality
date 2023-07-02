package com.planner.mortality.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routines")
data class RoutineEntity(
    @PrimaryKey override val id: Long = 0L,
    val title: String,
    val description: String,
    val endTimeStamp: Long,
    val isActive: Boolean = true,
) : BaseEntity