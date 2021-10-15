package com.nagwa.task.presentation.di.component

import android.app.Application
import com.nagwa.task.presentation.NagwaApp
import com.nagwa.task.presentation.di.module.ActivityBuilder
import com.nagwa.task.presentation.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilder::class,
        AppModule::class
    ]
)
interface AppComponent {

    fun inject(app: NagwaApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}