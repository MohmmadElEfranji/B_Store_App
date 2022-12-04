package com.dev_mohmmadelefranji.b_store_app.ui.fragments.sub_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.CategoryRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.category_response.CategoryResponse
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class CategoryDetailsViewModel(private val repo: CategoryRepository) : ViewModel() {


    private val _getSubCategory: MutableStateFlow<ApiResponse<CategoryResponse>> =
        MutableStateFlow(ApiResponse.Empty())

    val getSubCategory = _getSubCategory.asStateFlow()


    fun getSubCategory(token: String, categoryID: Int) {
        viewModelScope.launch {
            try {
                _getSubCategory.value = ApiResponse.Loading()
                val response =
                    repo.getSubCategory(
                        token = token,
                        categoryID = categoryID
                    )

                when {
                    response.isSuccessful -> {
                        val data = response.body()
                        data?.let {
                            _getSubCategory.value = ApiResponse.Success(it)
                        }
                    }

                    else -> {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val errorMsg = jObjError.getString("message")
                        _getSubCategory.value = ApiResponse.Failure(errorMsg)
                    }
                }

            } catch (exception: Exception) {
                _getSubCategory.value = ApiResponse.Failure(message = exception.message!!)
            }
        }
    }

}