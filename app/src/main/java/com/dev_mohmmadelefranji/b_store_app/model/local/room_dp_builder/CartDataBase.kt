package com.dev_mohmmadelefranji.b_store_app.model.local.room_dp_builder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dev_mohmmadelefranji.b_store_app.model.entity.Cart
import com.dev_mohmmadelefranji.b_store_app.model.local.dao.CartDAO

@Database(entities = [Cart::class], version = 1)
abstract class CartDataBase : RoomDatabase() {

    abstract fun cartDao(): CartDAO

    companion object {
        @Volatile
        private var instatance: CartDataBase? = null

        fun getInstance(context: Context): CartDataBase {
            return instatance ?: synchronized(Any()) {
                instatance ?: buildDatabase(context).also { instatance = it }
            }
        }

        private fun buildDatabase(context: Context): CartDataBase {
            return Room.databaseBuilder(
                context.applicationContext, CartDataBase::class.java,
                "Cart_DB"
            ).build()
        }

    }
}