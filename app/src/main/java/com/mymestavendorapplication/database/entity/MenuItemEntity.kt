package com.mymestavendorapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu_items")
data class MenuItemEntity(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int,
    val name: String,
    val price: Double,
    val description: String,
    val available: Boolean,
    var quantity: Int = 0)
