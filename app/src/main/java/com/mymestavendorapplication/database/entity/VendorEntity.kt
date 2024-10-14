package com.mymestavendorapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mymestavendorapplication.data.model.MenuItem


@Entity(tableName = "vendors")
data class VendorEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val category: String,
    val description:String,
    val rating: Double,
    val deliveryTime: Int,
    val isFavorite: Boolean = false
)
