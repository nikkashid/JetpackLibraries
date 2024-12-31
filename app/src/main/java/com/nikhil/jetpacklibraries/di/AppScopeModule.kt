package com.nikhil.jetpacklibraries.di

import android.content.Context
import com.nikhil.jetpacklibraries.fragments.workManager.genericWorkManager.Scheduler
import com.nikhil.jetpacklibraries.fragments.workManager.genericWorkManager.WorkManagerScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppScopeModule {

    companion object {

        @Provides
        fun provideTaskScheduler(@ApplicationContext context: Context): Scheduler =
            WorkManagerScheduler(context)

    }

}