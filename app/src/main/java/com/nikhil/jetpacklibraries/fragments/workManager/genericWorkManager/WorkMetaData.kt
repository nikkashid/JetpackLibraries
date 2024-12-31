package com.nikhil.jetpacklibraries.fragments.workManager.genericWorkManager

import androidx.work.Data

/**
 * The meta data for Worker.
 *
 * @constructor Creates instance of [WorkMetaData]
 * @property workName A unique work name.
 * @property periodicIntervalInMillis A periodic interval between execution or 0 if it is one time work request.
 * @property initialDelayInMillis A delay after which the worker should be run for the first time or null if no delay is required.
 * @property retryIntervalInMillis Retry interval for the case when the worker fails.
 * @property inputData Input data needed for the worker.
 * @property isNetworkRequired If network is required for the task.
 * @property isExpedite True to run work request immediately.
 */

data class WorkMetaData(
    val workName: String,
    val periodicIntervalInMillis: Long = 0,
    val initialDelayInMillis: Long? = null,
    val retryIntervalInMillis: Long? = null,
    val inputData: Data? = null,
    val isNetworkRequired: Boolean = true,
    val isExpedite: Boolean = false
)