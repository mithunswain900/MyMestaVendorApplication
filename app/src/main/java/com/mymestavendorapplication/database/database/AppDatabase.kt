package com.mymestavendorapplication.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mymestavendorapplication.database.Converters
import com.mymestavendorapplication.database.dao.CartDao
import com.mymestavendorapplication.database.dao.MenuItemsDao
import com.mymestavendorapplication.database.dao.VendorDao
import com.mymestavendorapplication.database.entity.CartItemEntity
import com.mymestavendorapplication.database.entity.MenuItemEntity
import com.mymestavendorapplication.database.entity.VendorEntity
import dagger.Provides


@Database(entities = [VendorEntity::class, CartItemEntity::class,MenuItemEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vendorDao(): VendorDao
    abstract fun cartDao(): CartDao
    abstract fun menuItemsDao():MenuItemsDao


    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "app_database"
                ).build()
            }
    }
}