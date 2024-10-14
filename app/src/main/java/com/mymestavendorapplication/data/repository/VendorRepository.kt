package com.mymestavendorapplication.data.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mymestavendorapplication.data.model.Vendor
import com.mymestavendorapplication.utills.JsonUtils
import javax.inject.Inject

class VendorRepository(private val context: Context) {

    fun loadVendors(): List<Vendor> {
        val jsonString = JsonUtils.loadJSONFromAsset(context, "vendors.json")
        return if (jsonString != null) {
            val gson = Gson()
            val vendorListType = object : TypeToken<List<Vendor>>() {}.type
            try {
                gson.fromJson(jsonString, vendorListType)
            } catch (e: Exception) {
                Log.e("VendorRepository", "Error parsing JSON", e)
                emptyList() // Return an empty list on error
            }
        } else {
            Log.e("VendorRepository", "JSON file not found!")
            emptyList()
        }
    }
}