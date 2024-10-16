package com.mymestavendorapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val menuItemId: Int,
    val quantity: Int
)
