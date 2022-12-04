package com.dev_mohmmadelefranji.b_store_app.model.local.repository

import com.dev_mohmmadelefranji.b_store_app.model.entity.Cart

interface LocalRepository {

    suspend fun insertOrUpdateCart(cart: Cart)

    suspend fun deleteCart(cart: Cart)

    suspend fun getCart(userID: Int): List<Cart>

    suspend fun updateQuantity(quantity: Int, id: Int)
}