package com.example.locationtrackerkotlin.util

import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.locationtrackerkotlin.App
import java.util.concurrent.ExecutionException

object Util {
    fun isWorkScheduled(tag: String): Boolean {
        val instance = WorkManager.getInstance(App.context)
        val statuses = instance.getWorkInfosByTag(tag)
        return try {
            var isRunning = false
            val workInfoList = statuses.get()
            workInfoList.forEach {
                val state = it.state
                isRunning = state == WorkInfo.State.RUNNING || state == WorkInfo.State.ENQUEUED
            }
            isRunning
        } catch (e: ExecutionException) {
            e.printStackTrace()
            false
        } catch (e: InterruptedException) {
            e.printStackTrace()
            false
        }
    }
}