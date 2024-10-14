package com.mymestavendorapplication.ui.dashboad.adpters

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mymestavendorapplication.R
import com.mymestavendorapplication.database.database.AppDatabase
import com.mymestavendorapplication.database.entity.MenuItemEntity
import com.mymestavendorapplication.ui.dashboad.CartFragment

class CartAdapter(
    private var cartItems: ArrayList<MenuItemEntity>,
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.text_cart_item_name)
        val quantity: TextView = view.findViewById(R.id.text_cart_item_quantity)
        val totalPrice: TextView = view.findViewById(R.id.text_cart_item_total_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_cart_items, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {


        val cartItem = cartItems[position]
        holder.name.text = cartItem.name
        holder.quantity.text = "Quantity: ${cartItem.quantity}"
        holder.totalPrice.text = "Total: ${cartItem.price * cartItem.quantity}"


        // Handle item click to update quantity
        holder.itemView.setOnClickListener {
        }
    }

    override fun getItemCount() = cartItems.size
}
