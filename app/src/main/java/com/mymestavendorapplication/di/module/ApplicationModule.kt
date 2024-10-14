package com.mymestavendorapplication.di.module

import android.app.Application
import android.content.Context
import com.mymestavendorapplication.MestaApplication
import com.mymestavendorapplication.data.api.NetworkService
import com.mymestavendorapplication.data.model.MenuItem
import com.mymestavendorapplication.data.model.Vendor
import com.mymestavendorapplication.database.dao.MenuItemsDao
import com.mymestavendorapplication.database.dao.VendorDao
import com.mymestavendorapplication.database.entity.MenuItemEntity
import com.mymestavendorapplication.di.ApplicationContext
import com.mymestavendorapplication.di.BaseUrl
import com.mymestavendorapplication.ui.dashboad.adpters.VendorListAdapter
import com.mymestavendorapplication.ui.dashboad.dashboardviewmodel.DashBoardViewModel
import com.mymestavendorapplication.ui.dashboad.dashboardviewmodel.DashBoardViewModelFactory
import com.mymestavendorapplication.ui.productdetails.adpters.MenuAdapter
import com.mymestavendorapplication.ui.productdetails.productdetailsviewmodel.ProductdetailsViewModels
import dagger.Module
import dagger.Provides

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MestaApplication) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return application
    }

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = ""

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)
    }


    @Provides
    @Singleton
    fun dashboardViewModel(): DashBoardViewModel {
        return DashBoardViewModel(application)
    }

    @Provides
    @Singleton
    fun provideDashboardViewModelFactory(application: Application): DashBoardViewModelFactory {
        return DashBoardViewModelFactory(application)
    }

    @Provides
    @Singleton
    fun provideProductViewModel(vendorDao: VendorDao,menuItemsDao: MenuItemsDao): ProductdetailsViewModels {
        return ProductdetailsViewModels( vendorDao,menuItemsDao)
    }

    @Provides
    @Singleton
    fun provideProductListViewModelFactory(vendorDao: VendorDao,menuItemsDao: MenuItemsDao): ProductdetailsViewModels {
        return ProductdetailsViewModels(vendorDao,menuItemsDao)
    }


   /* @Provides
    @Singleton
    fun provideMenuItems(menuItemDao: MenuItemsDao): List<MenuItem> {
        return menuItemDao.getAllMenuItems().map { menuItemEntity ->
            // Assuming a mapping from MenuItemEntity to MenuItem
            MenuItem(
                id = menuItemEntity.id,
                name = menuItemEntity.name,
                price = menuItemEntity.price,
                description = menuItemEntity.description,
                availability = menuItemEntity.availability
            )
        }
    }*/


    @Provides
    @Singleton
    fun provideVendorListAdapter(): VendorListAdapter {
        val onClickListener: (Vendor) -> Unit = { vendor ->
            println("Vendor clicked: ${vendor.name}")
        }
        return VendorListAdapter(ArrayList(), onClickListener)
    }



    @Provides
    @Singleton
    @JvmSuppressWildcards
    fun provideMenuItemsAdapter(
    ): MenuAdapter {
        val onClickListener: (MenuItem) -> Unit = { menuitmes ->
            println("Vendor clicked: ${menuitmes.name}")
        }
        return MenuAdapter(ArrayList(), onClickListener )
    }

    @Provides
    @JvmSuppressWildcards
    suspend fun provideMenuItems(menuItemDao: MenuItemsDao): List<MenuItemEntity> {
        // Assuming menuItemDao provides a list of MenuItem from the database
        return menuItemDao.getAllMenuItems() // Replace with your appropriate method
    }


    @Provides
    @Singleton
    @JvmSuppressWildcards
    fun provideOnClickListener(): (MenuItem) -> Unit {
        return { menuItem ->
            println("Menu Item clicked: ${menuItem.quantity}")
        }
    }


}
