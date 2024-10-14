package com.mymestavendorapplication.ui.productdetails.adpters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mymestavendorapplication.R
import com.mymestavendorapplication.data.model.MenuItem
import javax.inject.Inject

class MenuAdapter @Inject constructor(
    private var menuItems: List<MenuItem>,
    private val itemClick: (MenuItem) -> Unit,
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.text_menu_name)
        val price: TextView = view.findViewById(R.id.text_menu_price)
        val description: TextView = view.findViewById(R.id.text_menu_description)
        val addquantity: ImageView = view.findViewById(R.id.img_plus_button)
        val minusquantity: ImageView = view.findViewById(R.id.img_minus_button)
        val quantity: TextView = view.findViewById(R.id.text_count_quantity)
        val lladtocartbtn: LinearLayout = view.findViewById(R.id.ll_addcartbtn)
        val llquantitybtn: LinearLayout = view.findViewById(R.id.ll_quantity_btn)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_menu_item, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menuItem = menuItems[position]
        holder.name.text = menuItem.name
        holder.price.text = "${menuItem.price}"
        holder.description.text = menuItem.description
        holder.quantity.text = menuItem.quantity.toString()

        holder.addquantity.setOnClickListener {
            menuItem.quantity++
            holder.quantity.text = menuItem.quantity.toString()
            notifyItemChanged(position)
        }

        holder.minusquantity.setOnClickListener {
            if (menuItem.quantity > 0) {
                menuItem.quantity--
                holder.quantity.text = menuItem.quantity.toString()
                notifyItemChanged(position)
            }
        }

        holder.lladtocartbtn.setOnClickListener {
            holder.lladtocartbtn.visibility = View.GONE
            holder.llquantitybtn.visibility = View.VISIBLE

            itemClick(menuItem)

        }

    }

    override fun getItemCount() = menuItems.size
}