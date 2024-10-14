package com.mymestavendorapplication.data.model

import android.os.Parcel
import android.os.Parcelable


data class Vendor(
    val id: Int,
    val name: String?,
    val category: String?,
    val rating: Double,
    val delivery_time: Int,
    val description: String?,
    val menu: List<MenuItem>?,
    var isFavorite: Boolean = false
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString(),
        parcel.createTypedArrayList(MenuItem.CREATOR),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(category)
        parcel.writeDouble(rating)
        parcel.writeInt(delivery_time)
        parcel.writeString(description)
        parcel.writeTypedList(menu)


    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Vendor> {
        override fun createFromParcel(parcel: Parcel): Vendor {
            return Vendor(parcel)
        }

        override fun newArray(size: Int): Array<Vendor?> {
            return arrayOfNulls(size)
        }
    }
}
