package com.dev_mohmmadelefranji.b_store_app.model.entity

import com.dev_mohmmadelefranji.b_store_app.utils.Constants.PRODUCTS

sealed class Headers {

    data class CategoryHeader(
        val name: String = "Category",
        val commonKey: Boolean
    ) : Headers()

    data class LatestProductsHeader(
        val name: String = "Latest Products",
        val commonKey: String = "LatestProduct"
    ) : Headers()

    data class FamousProductsHeader(
        val name: String = "Famous Products",
        val commonKey: String = PRODUCTS
    ) : Headers()
}

