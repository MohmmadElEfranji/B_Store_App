package com.dev_mohmmadelefranji.b_store_app.ui.activites.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.AuthRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.auth_response.AuthResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.cities_response.CitiesResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class SignUpViewModel(private val repo: AuthRepository) : ViewModel() {

    //region sign up
    private val _signUp: MutableStateFlow<ApiResponse<AuthResponse>> =
        MutableStateFlow(ApiResponse.Empty())

    val signUp = _signUp.asStateFlow()


    fun signUp(
        userName: String,
        mobileNumber: String,
        password: String,
        gender: String,
        storeApiKey: String,
        cityID: Int,
    ) {
        viewModelScope.launch {
            try {
                _signUp.value = ApiResponse.Loading()

                val response = repo.signUp(
                    userName,
                    mobileNumber,
                    password,
                    gender,
                    storeApiKey,
                    cityID
                )

                when {
                    response.isSuccessful -> {
                        val data = response.body()
                        data?.let {
                            _signUp.value = ApiResponse.Success(it)
                        }
                    }

                    else -> {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val errorMsg = jObjError.getString("message")
                        _signUp.value = ApiResponse.Failure(errorMsg)
                    }

                }

            } catch (exception: Exception) {
                _signUp.value = ApiResponse.Failure(message = exception.message!!)
            }


        }
    }

    //endregion

    //region get All Cities
    private val _getAllCities: MutableStateFlow<ApiResponse<CitiesResponse>> =
        MutableStateFlow(ApiResponse.Empty())

    val getAllCities = _getAllCities.asStateFlow()

    fun getAllCities() {
        viewModelScope.launch {
            try {
                _getAllCities.value = ApiResponse.Loading()
                val response = repo.getAllCities()

                when {
                    response.isSuccessful -> {
                        val data = response.body()
                        data?.let {
                            _getAllCities.value = ApiResponse.Success(it)
                        }
                    }

                    else -> {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val errorMsg = jObjError.getString("message")
                        _getAllCities.value = ApiResponse.Failure(errorMsg)
                    }
                }

            } catch (exception: Exception) {
                _getAllCities.value = ApiResponse.Failure(message = exception.message!!)
            }
        }
    }
    //endregion
}