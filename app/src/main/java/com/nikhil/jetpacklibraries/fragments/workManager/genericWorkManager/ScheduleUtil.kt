package com.nikhil.jetpacklibraries.fragments.workManager.genericWorkManager

import com.nikhil.jetpacklibraries.fragments.workManager.UploadWorker

object ScheduleUtil {

    const val periodicInterval = 1000 * 60 * 5

    fun handleOneTimeDataUploadSyncWork(scheduler: Scheduler) {
        scheduler.schedule(
            UploadWorker::class, WorkMetaData(workName = UploadWorker.UPLOAD_WORKER_WORKER_NAME)
        )
    }

    fun handlePeriodicDataUploadSyncWor(scheduler: Scheduler) {
        scheduler.schedule(
            UploadWorker::class,
            WorkMetaData(
                workName = UploadWorker.UPLOAD_WORKER_WORKER_NAME,
                periodicIntervalInMillis = periodicInterval.toLong()
            )
        )
    }
}