package com.tehronshoh.todolist.presentation.util

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.Calendar

class NotificationWorker(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        try {
            context.sendLocalNotification(
                "Notification Title",
                "Time: " + Calendar.getInstance().getDateString()
            )
        } catch (e: Exception) {
            return Result.failure()
        }
        return Result.success()
    }

}