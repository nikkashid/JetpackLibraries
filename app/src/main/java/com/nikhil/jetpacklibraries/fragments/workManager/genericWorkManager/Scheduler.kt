package com.nikhil.jetpacklibraries.fragments.workManager.genericWorkManager

import androidx.work.Worker
import kotlin.reflect.KClass

interface Scheduler {

    /**
     * Schedules a [Worker] execution
     *
     * @param workerClass A [KClass] of a [Worker] to be executed.
     * @param workMetaData A[WorkMetaData] instance which contains details on how a task should be executed.
     */
    fun schedule(workerClass: KClass<out Worker>, workMetaData: WorkMetaData)

    fun cancelPeriodicScheduledWork(workName: String)

    fun cancelOneTimeScheduledWork(workName: String)

}