package com.mymestavendorapplication.di.module

import com.mymestavendorapplication.ui.dashboad.CartFragment
import com.mymestavendorapplication.ui.dashboad.HomeFragment
import com.mymestavendorapplication.ui.dashboad.WishListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {


    @ContributesAndroidInjector
    abstract fun contributeHomeFragment() : HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeCartFragment():CartFragment

    @ContributesAndroidInjector
    abstract fun contributeWishFragment():WishListFragment


}