package com.dev_mohmmadelefranji.b_store_app.model.remote.api.api_services.retrofit_builder

import com.dev_mohmmadelefranji.b_store_app.model.remote.api.api_services.*
import com.dev_mohmmadelefranji.b_store_app.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // To parse json to pojo
            .build() // until here; We made Retrofit Instance
    }

    val authApi: AuthServiceApi by lazy {
        retrofit.create(AuthServiceApi::class.java) // here to create and specify the class of our api
    }

    val homeApi: HomeServiceApi by lazy {
        retrofit.create(HomeServiceApi::class.java) // here to create and specify the class of our api
    }
    val categoryApi: CategoryServiceApi by lazy {
        retrofit.create(CategoryServiceApi::class.java) // here to create and specify the class of our api
    }

    val product: ProductServiceApi by lazy {
        retrofit.create(ProductServiceApi::class.java) // here to create and specify the class of our api
    }

    val paymentCard: PaymentCardServiceApi by lazy {
        retrofit.create(PaymentCardServiceApi::class.java) // here to create and specify the class of our api
    }
    val notification: NotificationAPI by lazy {
        retrofit.create(NotificationAPI::class.java) // here to create and specify the class of our api
    }

    val orderApi: OrdersServiceApi by lazy {
        retrofit.create(OrdersServiceApi::class.java) // here to create and specify the class of our api
    }
}