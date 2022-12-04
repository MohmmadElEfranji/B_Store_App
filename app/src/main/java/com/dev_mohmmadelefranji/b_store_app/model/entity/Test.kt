package com.dev_mohmmadelefranji.b_store_app.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Test(var n: String, var name: String? = null) : Parcelable
