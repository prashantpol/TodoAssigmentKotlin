package com.example.kotlintest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitTodoServices {


    companion object {

        private val BASE_URL = "https://jsonplaceholder.typicode.com/"

           var retrofit: Retrofit? = null

          fun getApiClient(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit
        }
    }
}
