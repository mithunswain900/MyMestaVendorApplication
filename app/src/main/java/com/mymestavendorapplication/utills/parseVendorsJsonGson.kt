package com.mymestavendorapplication.utills

import android.content.Context
import com.google.gson.Gson
import com.mymestavendorapplication.data.model.Vendor
import com.mymestavendorapplication.data.model.VendorList
import java.io.InputStreamReader

fun parseVendorsJsonGson(context: Context): List<Vendor> {
    val inputStream = context.assets.open("vendors.json")
    val reader = InputStreamReader(inputStream)

    // Use Gson to parse JSON
    val vendorList = Gson().fromJson(reader, VendorList::class.java)
    return vendorList.vendors

}