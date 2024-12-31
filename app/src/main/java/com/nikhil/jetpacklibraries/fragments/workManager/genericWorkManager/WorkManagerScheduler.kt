package com.nikhil.jetpacklibraries.fragments.workManager.genericWorkManager

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.Worker
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.reflect.KClass

class WorkManagerScheduler @Inject constructor(private val context: Context) : Scheduler {

    companion object {
        private const val PERIODIC = "_periodic"
        const val ONE_TIME = "_oneTime"
    }

    @Suppress("UNCHECKED_CAST")
    override fun schedule(workerClass: KClass<out Worker>, workMetaData: WorkMetaData) {
        val workManager = WorkManager.getInstance(context)

        if (workMetaData.periodicIntervalInMillis > 0) {
            if (workMetaData.isExpedite) throw IllegalArgumentException("Periodic scheduler cannot be expedite")

            val periodicWorkRequest = PeriodicWorkRequest.Builder(
                workerClass.java as Class<ListenableWorker>,
                workMetaData.periodicIntervalInMillis,
                TimeUnit.MILLISECONDS
            ).setWorkMetaData(workMetaData).build()
            workManager.enqueueUniquePeriodicWork(
                getPeriodicWorkName(workMetaData.workName),
                ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
                periodicWorkRequest
            )
        } else {
            val oneTimeWorkRequest =
                OneTimeWorkRequest.Builder(workerClass.java as Class<ListenableWorker>)
                    .setWorkMetaData(workMetaData).build()
            workManager.enqueueUniqueWork(
                getOneTimeWorkName(workMetaData.workName),
                ExistingWorkPolicy.REPLACE,
                oneTimeWorkRequest
            )
        }
    }

    override fun cancelPeriodicScheduledWork(workName: String) {
        WorkManager.getInstance(context).cancelAllWorkByTag(getPeriodicWorkName(workName))
    }

    override fun cancelOneTimeScheduledWork(workName: String) {
        WorkManager.getInstance(context).cancelAllWorkByTag(getOneTimeWorkName(workName))
    }

    private fun <T : WorkRequest.Builder<*, *>> T.setWorkMetaData(workMetaData: WorkMetaData): T {
        setConstraints(createWorkRequestConstraints(workMetaData))

        workMetaData.initialDelayInMillis?.let {
            setInitialDelay(it, TimeUnit.MILLISECONDS)
        }
        workMetaData.retryIntervalInMillis?.let {
            setBackoffCriteria(BackoffPolicy.LINEAR, it, TimeUnit.MILLISECONDS)
        }
        workMetaData.inputData?.let {
            setInputData(it)
        }
        if (workMetaData.isExpedite) {
            setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
        }
        return this
    }

    private fun createWorkRequestConstraints(workMetaData: WorkMetaData) =
        Constraints.Builder().apply {
            if (workMetaData.isNetworkRequired) {
                setRequiredNetworkType(NetworkType.CONNECTED)
            }
        }.build()

    private fun getPeriodicWorkName(workName: String) = workName.plus(PERIODIC)

    private fun getOneTimeWorkName(workName: String) = workName.plus(ONE_TIME)
}