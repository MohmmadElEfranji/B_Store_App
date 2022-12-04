package com.dev_mohmmadelefranji.b_store_app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev_mohmmadelefranji.b_store_app.utils.Constants.TABLE_NAME


@Entity(tableName = TABLE_NAME)
data class Cart(
    @PrimaryKey
    var id: Int,
    var userID: Int,
    var nameEn: String,
    var infoEn: String,
    var price: Float,
    var quantity: Int,
    var productRate: Int,
    var isFavorite: Boolean,
    var imageUrl: String,
    var quantityOfOrder: Int = 0,
)
