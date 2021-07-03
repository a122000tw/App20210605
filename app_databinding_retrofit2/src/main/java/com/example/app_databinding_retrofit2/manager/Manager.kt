package com.example.app_databinding_retrofit2.manager

import com.example.app_databinding_retrofit2.api.PostApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Manager {
    private val postApi: PostApi
    val api: PostApi
        get() = postApi

    companion object {
        val instance = Manager()
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        postApi = retrofit.create(PostApi::class.java)
    }

}