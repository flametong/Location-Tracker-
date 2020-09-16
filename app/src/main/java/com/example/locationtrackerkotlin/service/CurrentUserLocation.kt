package com.example.locationtrackerkotlin.service

import java.sql.Timestamp

data class CurrentUserLocation(
    val userEmail: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val timeInMillis: Long? = null,
    val timestamp: Timestamp? = null
)