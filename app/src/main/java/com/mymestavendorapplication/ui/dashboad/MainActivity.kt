package com.mymestavendorapplication.ui.dashboad

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mymestavendorapplication.MestaApplication
import com.mymestavendorapplication.R
import com.mymestavendorapplication.database.dao.MenuItemsDao
import com.mymestavendorapplication.database.database.AppDatabase
import com.mymestavendorapplication.databinding.ActivityMainBinding
import com.mymestavendorapplication.di.component.DaggerActivityComponent
import com.mymestavendorapplication.di.module.ActivityModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

        lateinit var mainBinding: ActivityMainBinding
        private lateinit var menuItemsDao: MenuItemsDao

        private lateinit var navController: NavController


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)


        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_database")
            .build()
        menuItemsDao = db.menuItemsDao()
        getCountCartData()

        mainBinding.imgDrawerNavigation.setOnClickListener {
            mainBinding.drawerlayout.openDrawer(GravityCompat.START)
        }

        mainBinding.rrCartItem.setOnClickListener {
           // navController.navigate(2)
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.homeFragment, false) // Adjust to your fragment
                .build()

            navController.navigate(R.id.cartFragment, null, navOptions)
        }

    }

    private fun injectDependencies() {
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as MestaApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }

    override fun onResume() {
        super.onResume()

    }

    private fun getCountCartData(): Int? {

        // Use Coroutine to query the database in the background
        var cartItemsCount = 0
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Query cart data from the database
                cartItemsCount = menuItemsDao.getAllMenuItems().size  // Example

                // Update UI on the main thread
                withContext(Dispatchers.Main) {
                    // Update UI with the cart count (or other data)
                    mainBinding.textCountCartItems.text = cartItemsCount.toString()

                }
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return cartItemsCount
    }


}
