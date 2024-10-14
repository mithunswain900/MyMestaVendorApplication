package com.mymestavendorapplication.di.component

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.mymestavendorapplication.MestaApplication
import com.mymestavendorapplication.data.api.NetworkService
import com.mymestavendorapplication.database.dao.MenuItemsDao
import com.mymestavendorapplication.database.dao.VendorDao
import com.mymestavendorapplication.di.ApplicationContext
import com.mymestavendorapplication.di.module.ApplicationModule
import com.mymestavendorapplication.di.module.FragmentModule
import com.mymestavendorapplication.di.module.RoomModule
import com.mymestavendorapplication.ui.dashboad.adpters.VendorListAdapter
import com.mymestavendorapplication.ui.productdetails.adpters.MenuAdapter
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class,
    FragmentModule::class,
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    RoomModule::class,
])
interface ApplicationComponent: AndroidInjector<MestaApplication> {

    // fun inject(application: MestaApplication)
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun applicationModule(module: ApplicationModule): Builder

        fun build(): ApplicationComponent
    }

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService


    fun getupdateList(): VendorListAdapter
    fun getVendorDao(): VendorDao
    fun getMenuDao(): MenuItemsDao
    fun getMenuAdpter(): MenuAdapter


}