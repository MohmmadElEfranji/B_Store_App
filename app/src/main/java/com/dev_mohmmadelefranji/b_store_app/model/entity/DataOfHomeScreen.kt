package com.dev_mohmmadelefranji.b_store_app.model.entity

import com.google.gson.annotations.SerializedName

data class DataOfHomeScreen(

    @SerializedName("slider")
    var slider: ArrayList<Slider> = arrayListOf(),
    @SerializedName("categories")
    var categories: ArrayList<Category> = arrayListOf(),
    @SerializedName("latest_products")
    var latestProducts: ArrayList<LatestProduct> = arrayListOf(),
    @SerializedName("famous_products")
    var famousProducts: ArrayList<Product> = arrayListOf()

)