package com.mymestavendorapplication.ui.dashboad.adpters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mymestavendorapplication.R
import com.mymestavendorapplication.database.entity.VendorEntity
import javax.inject.Inject

class WishListAdpter @Inject constructor(private var wishlist: List<VendorEntity>) :
    RecyclerView.Adapter<WishListAdpter.ViewHolder>() {

    inner  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.text_vendor_name)
        val category: TextView = view.findViewById(R.id.text_vendor_vandorcatagories)
        // More views here

        init {
            itemView.setOnClickListener {
                val wishlist = wishlist[adapterPosition]
            }
        }
    }

    fun updateList(newVendorList: List<VendorEntity>) {
        wishlist = newVendorList

        notifyDataSetChanged()  // Notify the adapter to refresh the UI
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_wish_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vendor = wishlist[position]


        holder.name.text = vendor.name
        holder.category.text = vendor.category
        // Set other fields...
    }

    override fun getItemCount() = wishlist.size
}