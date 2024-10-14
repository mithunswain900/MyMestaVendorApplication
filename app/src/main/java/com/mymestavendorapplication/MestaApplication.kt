package com.mymestavendorapplication

import android.app.Application
import com.mymestavendorapplication.di.component.ApplicationComponent
import com.mymestavendorapplication.di.component.DaggerApplicationComponent
import com.mymestavendorapplication.di.module.ApplicationModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MestaApplication : Application(), HasAndroidInjector {

    lateinit var applicationComponent: ApplicationComponent


    @Inject
    lateinit var mInject: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }
    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .application(this)
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return mInject
    }
}