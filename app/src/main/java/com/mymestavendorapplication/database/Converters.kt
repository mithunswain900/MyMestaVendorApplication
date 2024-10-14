package com.mymestavendorapplication.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mymestavendorapplication.database.entity.MenuItemEntity

class Converters {

    @TypeConverter
    fun fromMenuItemList(menuItems: List<MenuItemEntity>?): String? {
        return menuItems?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toMenuItemList(menuItemString: String?): List<MenuItemEntity>? {
        return menuItemString?.let {
            val listType = object : TypeToken<List<MenuItemEntity>>() {}.type
            Gson().fromJson(it, listType)
        }
    }
}