package com.example.retrofittask

import android.app.Service
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {

   private val client=OkHttpClient.Builder().build()

    private val retrofit=Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T : Any> buildService(service:Class<T>): T {
        return retrofit.create(service)
    }
}