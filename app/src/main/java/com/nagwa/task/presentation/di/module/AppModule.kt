package com.nagwa.task.presentation.di.module

import android.app.Application
import android.content.Context
import com.nagwa.task.presentation.di.qualifier.IoScheduler
import com.nagwa.task.presentation.di.qualifier.MainScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module()
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @MainScheduler
    @Provides
    @Singleton
    fun provideMainScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @IoScheduler
    @Provides
    @Singleton
    fun provideIoScheduler(): Scheduler {
        return Schedulers.io()
    }
}