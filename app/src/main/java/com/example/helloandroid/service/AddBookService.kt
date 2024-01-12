package com.example.helloandroid.service


import com.example.helloandroid.data.AddBookDataWrapper
import com.example.helloandroid.response.AddBookRespon
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface AddBookService {
    @POST("products")
    fun getData(@Body body: AddBookDataWrapper) : Call<AddBookRespon>
}