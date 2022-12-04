package com.dev_mohmmadelefranji.b_store_app.model.local.dao

import androidx.room.*
import com.dev_mohmmadelefranji.b_store_app.model.entity.Cart

@Dao
interface CartDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCart(cart: Cart)

    @Delete
    suspend fun deleteCart(cart: Cart)

    @Query("select * from CART_TABLE WHERE userID = :userID ")
    suspend fun getCart(userID: Int): MutableList<Cart>


    /**
     * Updating only Quantity
     * By order id
     */
    @Query("UPDATE cart_table SET quantityOfOrder=:quantity WHERE id = :id")
    suspend fun updateQuantity(quantity: Int, id: Int)


}