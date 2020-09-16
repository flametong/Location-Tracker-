package com.example.locationtrackerkotlin.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserLocation")
data class UserLocation(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId") val userId: Long,
    @ColumnInfo(name = "userEmail") val userEmail: String?,
    @ColumnInfo(name = "latitude") val latitude: Double?,
    @ColumnInfo(name = "longitude") val longitude: Double?,
    @ColumnInfo(name = "timeInMillis") val timeInMillis: Long?
)