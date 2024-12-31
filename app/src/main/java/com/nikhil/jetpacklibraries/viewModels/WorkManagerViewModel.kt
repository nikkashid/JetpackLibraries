package com.nikhil.jetpacklibraries.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.nikhil.jetpacklibraries.fragments.workManager.UploadWorker
import dagger.hilt.android.internal.Contexts

class WorkManagerViewModel(application: Application) : AndroidViewModel(application) {

    private val tag = "WorkMangerViewModel"
}