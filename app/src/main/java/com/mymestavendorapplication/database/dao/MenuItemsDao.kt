package com.mymestavendorapplication.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mymestavendorapplication.database.entity.MenuItemEntity


@Dao
interface MenuItemsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenuItem(menuItem: MenuItemEntity)

    @Query("SELECT * FROM menu_items")
     fun getAllMenuItems():List<MenuItemEntity>

    @Query("SELECT * FROM menu_items WHERE itemId = :id")
    suspend fun getMenuItemById(id: Int): MenuItemEntity?

    @Delete
    suspend fun deleteMenuItem(menuItem: MenuItemEntity)

    @Query("SELECT * FROM menu_items WHERE available = 1")
    suspend fun getAvailableMenuItems(): List<MenuItemEntity>
}
