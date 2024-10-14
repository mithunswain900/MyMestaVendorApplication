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
import com.mymestavendorapplication.R
import com.mymestavendorapplication.database.dao.MenuItemsDao
import com.mymestavendorapplication.database.dao.VendorDao
import com.mymestavendorapplication.database.database.AppDatabase
import com.mymestavendorapplication.database.entity.MenuItemEntity
import com.mymestavendorapplication.database.entity.VendorEntity
import com.mymestavendorapplication.databinding.FragmentCartBinding
import com.mymestavendorapplication.databinding.FragmentWhishListBinding
import com.mymestavendorapplication.ui.dashboad.adpters.CartAdapter
import com.mymestavendorapplication.ui.dashboad.adpters.WishListAdpter
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class WishListFragment : Fragment() {


    private lateinit var vendorDao: VendorDao
    private lateinit var wishlistItems: ArrayList<VendorEntity>
    private lateinit var wishListAdpter: WishListAdpter


    private var _wishbinding: FragmentWhishListBinding? = null
    private val wishbinding get() = _wishbinding!!
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
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_whish_list, container, false)
        _wishbinding = FragmentWhishListBinding.inflate(inflater,container,false)
        val db = context?.let { Room.databaseBuilder(it.applicationContext, AppDatabase::class.java, "app_database").build() }
        if (db != null) {
            vendorDao = db.vendorDao()
        }
        // Initialize cart items list
        wishlistItems = arrayListOf()

        // Initialize adapter
        wishListAdpter = WishListAdpter(wishlistItems)

        // Setup RecyclerView
        val recyclerView: RecyclerView = wishbinding.wishlistRecycler
        recyclerView.adapter = wishListAdpter
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Load cart items
        loadWishListItems()

        return wishbinding.root

    }

    private fun loadWishListItems() {




            // Launch a coroutine to fetch cart items from the database
            CoroutineScope(Dispatchers.IO).launch {
                val items = vendorDao.getAllVendorList() // Fetch from DAO

                // Switch back to the main thread to update UI
                withContext(Dispatchers.Main) {
                    wishlistItems.clear()
                    wishlistItems.addAll(items)  // Add items to the list
                    wishListAdpter.notifyDataSetChanged()  // Notify adapter
                }
            }
        }



}