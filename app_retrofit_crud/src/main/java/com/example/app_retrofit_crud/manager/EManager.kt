package com.example.app_retrofit_crud.manager

import com.example.app_retrofit_crud.api.JsonLocalHostApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EManager {
    private val localHostApi: JsonLocalHostApi
    val api: JsonLocalHostApi
        get() = localHostApi

    companion object { // 相當於 java 的 static class
        val instance = EManager()
    }
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        localHostApi = retrofit.create(JsonLocalHostApi::class.java)
    }

}