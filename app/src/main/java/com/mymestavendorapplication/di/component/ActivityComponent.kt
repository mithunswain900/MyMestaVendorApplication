package com.mymestavendorapplication.di.component

import com.mymestavendorapplication.ui.dashboad.MainActivity
import com.mymestavendorapplication.di.ActivityScope
import com.mymestavendorapplication.di.module.ActivityModule
import com.mymestavendorapplication.ui.productdetails.ProductDetailsActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

    fun inject(activity:ProductDetailsActivity)

}