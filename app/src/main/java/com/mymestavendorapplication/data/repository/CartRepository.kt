package com.mymestavendorapplication.data.repository

import com.mymestavendorapplication.database.dao.CartDao
import com.mymestavendorapplication.database.entity.CartItemEntity

class CartRepository(private val cartDao: CartDao) {

    suspend fun insert(cartItem: CartItemEntity) {
        cartDao.insert(cartItem)
    }

    suspend fun getAllCartItems(): List<CartItemEntity> {
        return cartDao.getAllCartItems()
    }

    suspend fun delete(cartItem: CartItemEntity) {
        cartDao.delete(cartItem)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}
