package com.dev_mohmmadelefranji.b_store_app.model.local.repository

import com.dev_mohmmadelefranji.b_store_app.model.entity.Cart
import com.dev_mohmmadelefranji.b_store_app.model.local.room_dp_builder.CartDataBase

class LocalRepositoryImp(private val db: CartDataBase) : LocalRepository {
    override suspend fun insertOrUpdateCart(cart: Cart) {
        db.cartDao().insertOrUpdateCart(cart)
    }

    override suspend fun deleteCart(cart: Cart) {
        db.cartDao().deleteCart(cart)
    }

    override suspend fun getCart(userID: Int): MutableList<Cart> {
        return db.cartDao().getCart(userID)
    }

    override suspend fun updateQuantity(quantity: Int, id: Int) {
        db.cartDao().updateQuantity(quantity, id)
    }

}