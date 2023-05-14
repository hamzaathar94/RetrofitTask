package com.example.retrofittask

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET


interface ApiInterface {
    @GET("/posts")
    fun getPosts():Call<MutableList<PostModel>>
   // fun getData():Call<MyData>

}