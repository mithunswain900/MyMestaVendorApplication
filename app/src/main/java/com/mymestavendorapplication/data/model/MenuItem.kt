package com.mymestavendorapplication.data.model

import android.os.Parcel
import android.os.Parcelable


data class MenuItem(
    val item_id: Int,
    val name: String,
    val price: Double,
    val description: String,
    val available: Boolean,
    var quantity: Int = 1 // Default to 0

) : Parcelable {

    // Secondary constructor for Parcelable
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "", // Provide a default value if null
        parcel.readDouble(),
        parcel.readString() ?: "", // Provide a default value if null
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(item_id)
        parcel.writeString(name)
        parcel.writeDouble(price)
        parcel.writeString(description)
        parcel.writeByte(if (available) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MenuItem> {
        override fun createFromParcel(parcel: Parcel): MenuItem {
            return MenuItem(parcel)
        }

        override fun newArray(size: Int): Array<MenuItem?> {
            return arrayOfNulls(size)
        }
    }
}
