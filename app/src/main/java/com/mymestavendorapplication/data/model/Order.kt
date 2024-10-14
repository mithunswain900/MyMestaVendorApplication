package com.mymestavendorapplication.data.model

data class Order(

    val items: List<CartItem>,
    val totalPrice: Double
)
