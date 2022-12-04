package com.dev_mohmmadelefranji.b_store_app.ui.fragments.cart

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

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private var localRepositoryImp: LocalRepositoryImp


    init {
        val db = CartDataBase.getInstance(application)
        localRepositoryImp = LocalRepositoryImp(db)
    }

    private val _getProductOfCart: MutableStateFlow<RoomDbResponse<MutableList<Cart>>> =
        MutableStateFlow(RoomDbResponse.Empty)

    val getProductOfCart = _getProductOfCart.asStateFlow()


    fun getProductOfCart(userID: Int) {
        viewModelScope.launch {
            try {
                _getProductOfCart.value = RoomDbResponse.Loading

                val list = localRepositoryImp.getCart(userID)

                _getProductOfCart.value = RoomDbResponse.Success(list)


            } catch (exception: Exception) {
                _getProductOfCart.value = RoomDbResponse.Failure(message = exception.message!!)
            }
        }
    }


    private val _updateQuantity: MutableStateFlow<RoomDbResponse<Boolean>> =
        MutableStateFlow(RoomDbResponse.Empty)

    val updateQuantity = _updateQuantity.asStateFlow()


    fun updateQuantity(quantity: Int, id: Int) {
        viewModelScope.launch {
            try {
                _updateQuantity.value = RoomDbResponse.Loading

                localRepositoryImp.updateQuantity(quantity, id)

                _updateQuantity.value = RoomDbResponse.Success(true)


            } catch (exception: Exception) {
                _updateQuantity.value = RoomDbResponse.Failure(message = exception.message!!)
            }
        }
    }
}