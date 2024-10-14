package com.mymestavendorapplication.ui.dashboad

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.mymestavendorapplication.data.model.CartItem
import com.mymestavendorapplication.database.dao.MenuItemsDao
import com.mymestavendorapplication.database.database.AppDatabase
import com.mymestavendorapplication.database.entity.MenuItemEntity
import com.mymestavendorapplication.databinding.FragmentCartBinding
import com.mymestavendorapplication.ui.dashboad.adpters.CartAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class CartFragment : Fragment() {

    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartItems: MutableList<CartItem>

    private lateinit var menuCartItems: ArrayList<MenuItemEntity>
    private lateinit var menuItemsDao: MenuItemsDao

    private var _cartbinding: FragmentCartBinding? = null
    private val cartbinding get() = _cartbinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _cartbinding = FragmentCartBinding.inflate(inflater,container,false)


        val db = context?.let { Room.databaseBuilder(it.applicationContext, AppDatabase::class.java, "app_database").build() }
        if (db != null) {
            menuItemsDao = db.menuItemsDao()
        }


        menuCartItems = arrayListOf()

        cartAdapter = CartAdapter(menuCartItems)


        val recyclerView: RecyclerView = cartbinding.cartRecycler
        recyclerView.adapter = cartAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)


        loadCartItems()

        return cartbinding.root
    }

    private fun loadCartItems() {
        CoroutineScope(Dispatchers.IO).launch {
            val items = menuItemsDao.getAllMenuItems() // Fetch from DAO

            withContext(Dispatchers.Main) {
                menuCartItems.clear()
                menuCartItems.addAll(items)
                cartAdapter.notifyDataSetChanged()  // Notify adapter
            }
        }
    }

    private fun updateQuantity(cartItem: CartItem) {
        // Update quantity logic
    }

    private fun placeOrder() {
        // Implement order submission logic
    }
}



    private fun updateQuantity(cartItem: CartItem) {
        // Update quantity logic
    }

    private fun placeOrder() {
        // Implement order submission logic
    }






