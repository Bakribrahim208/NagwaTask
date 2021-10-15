package com.nagwa.task.presentation.di.module

import com.nagwa.task.presentation.view.activity.DownloadFileActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [FilesModule::class])
    abstract fun bindDownLoadFileActivity(): DownloadFileActivity
}