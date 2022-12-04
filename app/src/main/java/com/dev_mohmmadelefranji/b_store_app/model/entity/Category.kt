package com.dev_mohmmadelefranji.b_store_app.model.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(

    @SerializedName("id")
    var categoryID: Int,
    @SerializedName("name_en")
    var nameEn: String,
    @SerializedName("name_ar")
    var nameAr: String,
    @SerializedName("image")
    var imageName: String,
    @SerializedName("image_url")
    var imageUrl: String,
) : Parcelable