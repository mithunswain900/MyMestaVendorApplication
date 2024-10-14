package com.mymestavendorapplication.di.module

import android.content.Context
import androidx.room.Room
import com.mymestavendorapplication.database.dao.CartDao
import com.mymestavendorapplication.database.dao.MenuItemsDao
import com.mymestavendorapplication.database.dao.VendorDao
import com.mymestavendorapplication.database.database.AppDatabase
import com.mymestavendorapplication.di.ApplicationContext

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "myapp-database")
            .build()
    }

   @Provides
    @Singleton
    fun provideCartDao(database: AppDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    @Singleton
    fun provideVendorDao(appDatabase: AppDatabase): VendorDao {
        return appDatabase.vendorDao()
    }
    @Provides
    @Singleton
    fun provideMenuItemsDao(appDatabase: AppDatabase): MenuItemsDao {
        return appDatabase.menuItemsDao()
    }
}