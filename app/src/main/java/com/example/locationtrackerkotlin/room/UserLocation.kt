package com.example.locationtrackerkotlin.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.locationtrackerkotlin.util.Constants

@Entity(tableName = Constants.TABLE_NAME)
data class UserLocation(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.USER_ID_DB) val userId: Long,
    @ColumnInfo(name = Constants.USER_EMAIL) val userEmail: String?,
    @ColumnInfo(name = Constants.LATITUDE) val latitude: Double?,
    @ColumnInfo(name = Constants.LONGITUDE) val longitude: Double?,
    @ColumnInfo(name = Constants.TIME_IN_MILLIS) val timeInMillis: Long?
)