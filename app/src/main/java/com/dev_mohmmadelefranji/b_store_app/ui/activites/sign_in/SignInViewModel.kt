package com.dev_mohmmadelefranji.b_store_app.ui.activites.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.AuthRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.auth_response.AuthResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class SignInViewModel(private val repo: AuthRepository) : ViewModel() {


    private val _signIn: MutableStateFlow<ApiResponse<AuthResponse>> =
        MutableStateFlow(ApiResponse.Empty())

    val signIn = _signIn.asStateFlow()

    fun signIn(
        mobileNumber: String,
        password: String,
        fcmToken: String
    ) {
        viewModelScope.launch {
            try {


                _signIn.value = ApiResponse.Loading()

                val response = repo.signIn(
                    mobileNumber,
                    password,
                    fcmToken
                )

                when {
                    response.isSuccessful -> {
                        val data = response.body()
                        data?.let {
                            _signIn.emit(ApiResponse.Success(it))
                        }
                    }

                    else -> {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val errorMsg = jObjError.getString("message")
                        _signIn.value = ApiResponse.Failure(errorMsg)
                    }

                }

            } catch (exception: Exception) {
                _signIn.value = ApiResponse.Failure(message = exception.message!!)
            }


        }
    }
}