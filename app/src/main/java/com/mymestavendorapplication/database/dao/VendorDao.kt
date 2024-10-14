package com.mymestavendorapplication.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mymestavendorapplication.data.model.Vendor
import com.mymestavendorapplication.database.entity.VendorEntity

@Dao
interface VendorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVendor(vendor: VendorEntity)

    @Update
    fun updateVendor(vendor: VendorEntity)

    @Delete
    fun deleteVendor(vendor: VendorEntity)

    @Query("SELECT * FROM vendors WHERE id = :id LIMIT 1")
   // @Query("SELECT * FROM vendors WHERE id = :id")
    fun getVendorById(id: Int): VendorEntity

    @Query("SELECT * FROM vendors")
    fun getAllVendorList():List<VendorEntity>
}