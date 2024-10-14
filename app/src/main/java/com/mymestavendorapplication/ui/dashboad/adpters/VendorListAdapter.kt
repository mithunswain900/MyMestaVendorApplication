package com.mymestavendorapplication.ui.dashboad.adpters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mymestavendorapplication.R
import com.mymestavendorapplication.data.model.Vendor
import javax.inject.Inject

class VendorListAdapter @Inject constructor(private var vendors: List<Vendor>, private val onClick: (Vendor) -> Unit) :
    RecyclerView.Adapter<VendorListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.text_vendor_name)
        val category: TextView = view.findViewById(R.id.text_vendor_vandorcatagories)
        val rating: TextView = view.findViewById(R.id.text_vendor_rating)
        val estimatedTime: TextView = view.findViewById(R.id.text_vendor_estimatetime)

        init {
            itemView.setOnClickListener {
                val vendor = vendors[adapterPosition]
                onClick(vendor) // Trigger the click listener
            }
        }
    }

    fun updateList(newVendorList: List<Vendor>) {
        vendors = newVendorList
        notifyDataSetChanged() // Notify the adapter to refresh the UI
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_vendor_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vendor = vendors[position]
        holder.name.text = vendor.name
        holder.category.text = vendor.category
        holder.estimatedTime.text = vendor.delivery_time.toString()
        holder.rating.text = vendor.rating.toString()

        // If you have an ImageView for displaying vendor images, set it here
        // Glide.with(holder.itemView.context).load(vendor.imageUrl).into(holder.imageView)
    }

    override fun getItemCount() = vendors.size
}
