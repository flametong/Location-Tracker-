package com.example.locationtrackerkotlin.room

import androidx.room.*

@Dao
interface UserLocationDao {
    @Query("SELECT * FROM UserLocation")
    suspend fun getAll(): List<UserLocation>

    @Query("SELECT * FROM UserLocation WHERE userId IN (:userIds)")
    suspend fun loadAllByIds(userIds: IntArray): List<UserLocation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userLocation: UserLocation)

    @Update
    suspend fun update(userLocation: UserLocation)

    @Delete
    suspend fun delete(userLocation: UserLocation)
}