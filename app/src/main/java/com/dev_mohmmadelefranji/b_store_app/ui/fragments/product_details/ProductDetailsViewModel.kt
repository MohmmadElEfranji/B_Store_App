package com.dev_mohmmadelefranji.b_store_app.ui.fragments.product_details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dev_mohmmadelefranji.b_store_app.model.entity.Cart
import com.dev_mohmmadelefranji.b_store_app.model.local.repository.LocalRepositoryImp
import com.dev_mohmmadelefranji.b_store_app.model.local.response.RoomDbResponse
import com.dev_mohmmadelefranji.b_store_app.model.local.room_dp_builder.CartDataBase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel(application: Application) : AndroidViewModel(application) {


    private var localRepositoryImp: LocalRepositoryImp


    init {
        val db = CartDataBase.getInstance(application)
        localRepositoryImp = LocalRepositoryImp(db)
    }


    private val _insertAndUpdateCart: MutableStateFlow<RoomDbResponse<Boolean>> =
        MutableStateFlow(RoomDbResponse.Empty)

    val insertAndUpdateCart = _insertAndUpdateCart.asStateFlow()


    fun insertAndUpdateCart(cart: Cart) {
        viewModelScope.launch {
            try {
                _insertAndUpdateCart.value = RoomDbResponse.Loading

                localRepositoryImp.insertOrUpdateCart(cart = cart)

                _insertAndUpdateCart.value = RoomDbResponse.Success(true)

            } catch (exception: Exception) {
                _insertAndUpdateCart.value = RoomDbResponse.Failure(message = exception.message!!)
            }
        }
    }
}