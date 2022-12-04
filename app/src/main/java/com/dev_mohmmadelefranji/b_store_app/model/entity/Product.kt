package com.dev_mohmmadelefranji.b_store_app.model.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Product(

    @SerializedName("id")
    var id: Int,
    @SerializedName("name_en")
    var nameEn: String,
    @SerializedName("name_ar")
    var nameAr: String,
    @SerializedName("info_en")
    var infoEn: String,
    @SerializedName("info_ar")
    var infoAr: String,
    @SerializedName("price")
    var price: String,
    @SerializedName("quantity")
    var quantity: String,
    @SerializedName("overal_rate")
    var overalRate: String,
    @SerializedName("sub_category_id")
    var subCategoryId: String,
    @SerializedName("product_rate")
    var productRate: Int,
    @SerializedName("offer_price")
    var offerPrice: String?,
    @SerializedName("is_favorite")
    var isFavorite: Boolean,
    @SerializedName("image_url")
    var imageUrl: String,
) : Parcelable
