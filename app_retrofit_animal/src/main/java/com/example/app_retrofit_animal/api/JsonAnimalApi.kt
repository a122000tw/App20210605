package com.example.app_retrofit_animal.api

import com.example.app_retrofit_animal.model.AnimalInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JsonAnimalApi {

    @GET("TransService.aspx")
    fun getAnimals(@Query("UnitId") uid: String): Call<List<AnimalInfo>>
}