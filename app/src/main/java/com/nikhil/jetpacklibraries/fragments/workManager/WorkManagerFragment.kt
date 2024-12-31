package com.nikhil.jetpacklibraries.fragments.workManager

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.nikhil.jetpacklibraries.databinding.FragmentWorkManagerBinding
import com.nikhil.jetpacklibraries.fragments.workManager.genericWorkManager.ScheduleUtil
import com.nikhil.jetpacklibraries.fragments.workManager.genericWorkManager.Scheduler
import com.nikhil.jetpacklibraries.fragments.workManager.genericWorkManager.WorkManagerScheduler.Companion.ONE_TIME
import javax.inject.Inject

class WorkManagerFragment : Fragment() {

    private val workFragmentArgs: WorkManagerFragmentArgs by navArgs()

    private lateinit var workManagerBinding: FragmentWorkManagerBinding

    @Inject
    lateinit var scheduler: Scheduler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        workManagerBinding = FragmentWorkManagerBinding.inflate(inflater, container, false)
        return workManagerBinding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        workManagerBinding.tvSafeArgs.text =
            workFragmentArgs.mainToWorkManagerString + " : Age :" + workFragmentArgs.mainToWorkManagerInt.toString()
        workManagerBinding.btnOneTimeRequest.setOnClickListener { oneTimeWorkRequestByLiveData() }
    }

    private fun oneTimeWorkRequestByLiveData() {
        Log.d(tag, "oneTimeWorkRequest: Started")
        val oneTimeWorkRequest: OneTimeWorkRequest =
            OneTimeWorkRequest.Builder(UploadWorker::class.java).build()
        val workManger = context?.let { WorkManager.getInstance(it) }
        workManger?.run {
            enqueueUniqueWork(
                UploadWorker.UPLOAD_WORKER_WORKER_NAME.plus(ONE_TIME),
                ExistingWorkPolicy.REPLACE,
                oneTimeWorkRequest
            )
            getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(
                viewLifecycleOwner
            ) {
                workManagerBinding.tvSafeArgs.text = it?.state?.name
            }
        }
    }

    private fun oneTimeWorkRequest() {
        ScheduleUtil.handleOneTimeDataUploadSyncWork(scheduler = scheduler)
    }

    private fun periodicWorkRequest() {
        ScheduleUtil.handlePeriodicDataUploadSyncWor(scheduler = scheduler)
    }

}