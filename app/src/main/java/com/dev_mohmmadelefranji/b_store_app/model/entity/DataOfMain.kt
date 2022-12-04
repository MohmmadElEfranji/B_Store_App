package com.dev_mohmmadelefranji.b_store_app.model.entity

data class DataOfMain(
    val name: Boolean = true,
    var categorylist: MutableList<Category> = arrayListOf(),
)
