package com.example.helloandroid.service

import com.example.helloandroid.response.ProdukRespon
import retrofit2.Call
import retrofit2.http.GET

interface ProdukService {
    @GET("products")
    fun getData() : Call<List<ProdukRespon>>
}