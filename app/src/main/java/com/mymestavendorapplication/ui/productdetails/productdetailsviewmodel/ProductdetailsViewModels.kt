package com.mymestavendorapplication.ui.productdetails.productdetailsviewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mymestavendorapplication.data.model.Vendor
import com.mymestavendorapplication.database.dao.MenuItemsDao
import com.mymestavendorapplication.database.dao.VendorDao
import com.mymestavendorapplication.database.entity.MenuItemEntity
import com.mymestavendorapplication.database.entity.VendorEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductdetailsViewModels @Inject constructor(
    private val vendorDao: VendorDao,
    private val menuItemDao: MenuItemsDao
) : ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

   fun toggleFavorite(vendor: Vendor?) {
        vendor?.let {
            val updatedVendor = it.copy(isFavorite = !it.isFavorite) // Toggle the favorite status
            viewModelScope.launch {
                // Update the database with the new favorite status
                withContext(Dispatchers.IO) {

                    vendorDao.insertVendor(
                        VendorEntity(
                            id = updatedVendor.id,
                            name = updatedVendor.name!!,
                            description = updatedVendor.description!!,
                            rating = updatedVendor.rating!!,
                            category = updatedVendor.category!!,
                            deliveryTime = updatedVendor.delivery_time!!,
                            isFavorite = updatedVendor.isFavorite
                        )
                    )
                }

                    // Post the new favorite status to LiveData
                    _isFavorite.postValue(updatedVendor.isFavorite)

            }
        } ?: run {
            _isFavorite.postValue(false) // Handle case when vendor is null
        }
    }


    fun checkFavoriteStatus(vendorId: Int) {
        viewModelScope.launch {
            val vendor = withContext(Dispatchers.IO) {
                vendorDao.getVendorById(vendorId)  // This runs on an I/O thread
            }
            _isFavorite.postValue(vendor?.isFavorite ?: false)  // This runs on the main thread
        }
    }



    // Function to toggle the favorite status of a vendor
  /* fun toggleFavorite(vendor: Vendor?) {
        vendor?.let {
            val updatedVendor = it.copy(isFavorite = !it.isFavorite) // Update the favorite status
            viewModelScope.launch {
               // vendorDao.updateVendor(updatedVendor) // Update in Room
                _isFavorite.postValue(updatedVendor.isFavorite) // Update LiveData
            }
        } ?: run {
            // Handle null vendor case, e.g., log an error or show a message
            _isFavorite.postValue(false) // or handle appropriately
        }
    }

    // Function to check if the vendor is favorite
    fun checkFavoriteStatus(vendorId: Int) {
        viewModelScope.launch {
            val vendor = vendorDao.getVendorById(vendorId)
            _isFavorite.postValue(vendor?.isFavorite ?: false) // Set initial favorite status
        }
    }*/

    // Fetch available menu items as LiveData
/*
    fun getAvailableMenuItems(): LiveData<List<MenuItemEntity>> {
        val menuItemsLiveData = MutableLiveData<List<MenuItemEntity>>()
        viewModelScope.launch {
            try {
                val menuItems = menuItemDao.getAvailableMenuItems()
                menuItemsLiveData.postValue(menuItems)
            } catch (e: Exception) {
                // Handle error, possibly logging or showing a message
                menuItemsLiveData.postValue(emptyList()) // or handle accordingly
            }
        }
        return menuItemsLiveData
    }
*/

    fun addMenuItem(menuItem: MenuItemEntity) {
        viewModelScope.launch {
            menuItemDao.insertMenuItem(menuItem)
        }
    }
}
