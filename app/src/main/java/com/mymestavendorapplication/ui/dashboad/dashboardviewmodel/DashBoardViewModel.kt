package com.mymestavendorapplication.ui.dashboad.dashboardviewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mymestavendorapplication.data.model.Vendor
import com.mymestavendorapplication.data.model.VendorWrapper
import com.mymestavendorapplication.data.repository.CartRepository
import com.mymestavendorapplication.database.dao.CartDao
import com.mymestavendorapplication.utills.JsonUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashBoardViewModel(application: Application) : AndroidViewModel(application){

    private val _vendors = MutableLiveData<List<Vendor>>()
    val vendors: LiveData<List<Vendor>>
        get() = _vendors

    private var allVendors: List<Vendor> = emptyList()

    // Function to load and parse JSON
    fun loadVendors() {
        viewModelScope.launch(Dispatchers.IO) {
            // Load JSON string from assets
            val jsonFileString = JsonUtils.loadJSONFromAsset(getApplication(), "vendors.json")

            if (jsonFileString != null) {
                try {
                    // Parse JSON to list of VendorWrapper
                    val gson = Gson()
                    val listVendorType = object : TypeToken<List<VendorWrapper>>() {}.type
                    val vendorWrapperList: List<VendorWrapper> = gson.fromJson(jsonFileString, listVendorType)

                    // Access the vendors list from the first VendorWrapper
                    val vendorList = vendorWrapperList.firstOrNull()?.vendors ?: emptyList()
                    allVendors = vendorList // Store the loaded vendors
                    _vendors.postValue(vendorList) // Post the vendor list once

                    // Debugging logs to confirm data flow
                    Log.d("DashBoardViewModel", "Loaded ${vendorList.size} vendors from JSON.")
                } catch (e: Exception) {
                    Log.e("DashBoardViewModel", "Error parsing JSON", e)
                }
            } else {
                Log.e("DashBoardViewModel", "JSON file not found!")
            }
        }
    }



    // Filter vendors based on search query
    fun filterVendors(query: String) {
        val filteredVendors = if (query.isEmpty()) {
            allVendors // Return all vendors if the query is empty
        } else {
            allVendors.filter { vendor ->
                vendor.name!!.contains(query, ignoreCase = true) ||
                        vendor.category!!.contains(query, ignoreCase = true)
            }
        }

        Log.d("DashBoardViewModel", "Filtered vendors count: ${filteredVendors.size}") // Log the count of filtered vendors
        _vendors.value = filteredVendors // Update the live data with filtered results
    }



}