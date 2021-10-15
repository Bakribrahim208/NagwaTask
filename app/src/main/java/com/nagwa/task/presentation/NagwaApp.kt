package com.nagwa.task.presentation

import android.app.Application
import com.nagwa.task.presentation.di.component.AppComponent
import com.nagwa.task.presentation.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class NagwaApp : Application(), HasAndroidInjector {

    var appComponent: AppComponent? = null

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent?.inject(this)
    }
}