package com.mymestavendorapplication.ui.productdetails

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.mymestavendorapplication.MestaApplication
import com.mymestavendorapplication.R
import com.mymestavendorapplication.data.model.Vendor
import com.mymestavendorapplication.database.dao.MenuItemsDao
import com.mymestavendorapplication.database.dao.VendorDao
import com.mymestavendorapplication.database.database.AppDatabase
import com.mymestavendorapplication.database.entity.MenuItemEntity
import com.mymestavendorapplication.database.entity.VendorEntity
import com.mymestavendorapplication.databinding.ActivityProductDetailsBinding
import com.mymestavendorapplication.di.component.DaggerActivityComponent
import com.mymestavendorapplication.di.module.ActivityModule
import com.mymestavendorapplication.ui.dashboad.MainActivity
import com.mymestavendorapplication.ui.productdetails.adpters.MenuAdapter
import com.mymestavendorapplication.ui.productdetails.productdetailsviewmodel.ProductdetailsViewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductDetailsActivity : AppCompatActivity() {


    @Inject
    lateinit var menuAdapter: MenuAdapter

    @Inject
    lateinit var productdetailsViewModels: ProductdetailsViewModels

    private lateinit var menuItemsDao: MenuItemsDao
    private lateinit var vendorDao: VendorDao

    lateinit var productDetailsBinding: ActivityProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()

        super.onCreate(savedInstanceState)

        productDetailsBinding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(productDetailsBinding.root)
        val vendor = intent.getParcelableExtra<Vendor>("vendor")


        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_database")
            .build()
        menuItemsDao = db.menuItemsDao()
        vendorDao = db.vendorDao()

        vendor?.let {
            displayVendorDetails(it)
            checkVendorFavorityes(it)
        }

        getCountCartData()

        productDetailsBinding.imgBackButton.setOnClickListener {
            onBackPressed()
        }

        productDetailsBinding.imgCartCount.setOnClickListener {
            var intent = Intent(this@ProductDetailsActivity, MainActivity::class.java)
            intent.putExtra("cart_fragment", R.id.cartFragment)
            startActivity(intent)
        }


        productDetailsBinding.buttonFavorite.setOnClickListener {
            if (vendor != null) {
                productdetailsViewModels.toggleFavorite(vendor)
            }
        }
        productdetailsViewModels.isFavorite.observe(this, Observer { isFavorite ->
            updateFavoriteButton(isFavorite,vendor)
        })

    }

    private fun checkVendorFavorityes(vendor: Vendor) {
        lifecycleScope.launch {
            val vendorEntity = withContext(Dispatchers.IO) {
                vendorDao.getVendorById(vendor!!.id)
            }
            if (vendorEntity != null) {
                updateFavoriteButton(vendorEntity.isFavorite, vendor)
            } else {
                println("Vendor not found in the database")
            }
        }
    }


    private fun getCountCartData(): Int? {
        var cartItemsCount = 0
        CoroutineScope(Dispatchers.IO).launch {
            try {
                cartItemsCount = menuItemsDao.getAllMenuItems().size  // Example

                withContext(Dispatchers.Main) {
                    productDetailsBinding.textCountCartItems.text = cartItemsCount.toString()

                }
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return cartItemsCount
    }



    private fun injectDependencies() {
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as MestaApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)

    }

    private  fun displayVendorDetails(vendor: Vendor) {
        productDetailsBinding.textVendorName.text = vendor.name
        productDetailsBinding.textVendorDescription.text = vendor.description
        productDetailsBinding.textVendorRating.text = vendor.rating.toString()
        productDetailsBinding.textVendorCategory.text = vendor.category
        productDetailsBinding.textVendorDeliveryTime.text = "${vendor.delivery_time} min"

        productDetailsBinding.recyclerViewMenu.layoutManager = LinearLayoutManager(this)

        val menuItems = vendor.menu

        menuAdapter = MenuAdapter(menuItems!!, itemClick = { menuItem ->
            var menuItemEntity = MenuItemEntity(
                itemId = menuItem.item_id,
                name = menuItem.name,
                price = menuItem.price,
                description = menuItem.description,
                available = menuItem.available,
                quantity = menuItem.quantity
            )

            insertMenuItem(menuItemEntity)

        })
        productDetailsBinding.recyclerViewMenu.adapter = menuAdapter
        menuAdapter.notifyDataSetChanged()


    }

    private fun insertMenuItem(menuItem: MenuItemEntity) {

        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getDatabase(this@ProductDetailsActivity).menuItemsDao().insertMenuItem(
                menuItem
            )
            withContext(Dispatchers.Main) {
                Toast.makeText(this@ProductDetailsActivity, "${menuItem.name} added to cart!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateFavoriteButton(isFavorite: Boolean, vendor: Vendor?,) {
        if (isFavorite) {
            productDetailsBinding.buttonFavorite.setImageResource(R.drawable.inactive_favorite_icon) // Active state
            addfavoriteDataInRoom(vendor)

        } else {
            productDetailsBinding.buttonFavorite.setImageResource(R.drawable.active_favorite_icon) // Inactive state

            // deleteDataInRoom(vendor)
        }
    }

    private fun deleteDataInRoom(vendor: Vendor?) {
        TODO("Not yet implemented")
    }

    private fun addfavoriteDataInRoom(vendor: Vendor?) {
        lifecycleScope.launch {
            val vendorEntity = withContext(Dispatchers.IO) {

                var vendorEntity = VendorEntity(
                    vendor!!.id,
                    vendor.name.toString(),
                    vendor.category.toString(),
                    vendor.description.toString(),
                    vendor.rating,
                    vendor.delivery_time,
                    vendor.isFavorite,

                    )
                vendorDao.insertVendor(
                    vendorEntity
                )
            }
            if (vendorEntity != null) {
                updateFavoriteButton(true, vendor)
            } else {
                updateFavoriteButton(false,vendor)
            }
        }
    }


}



