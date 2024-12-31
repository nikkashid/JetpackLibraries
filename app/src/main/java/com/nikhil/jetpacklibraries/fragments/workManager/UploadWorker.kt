package com.nikhil.jetpacklibraries.fragments.workManager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val TAG = "UploadWorker"

    companion object {
        const val UPLOAD_WORKER_WORKER_NAME = "UploadWorker"
    }

    override fun doWork(): Result {
        return try {
            for (i: Int in 0..50) Log.d(TAG, "doWork: Uploading $i")
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}