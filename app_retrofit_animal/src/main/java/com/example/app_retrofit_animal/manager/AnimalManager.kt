package com.example.app_retrofit_animal.manager

import com.example.app_retrofit_animal.api.JsonAnimalApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AnimalManager {
    private  val animalApi: JsonAnimalApi
    val api: JsonAnimalApi
        get() = animalApi

    companion object {
        val instance = AnimalManager()
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://data.coa.gov.tw/Service/OpenData/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        animalApi = retrofit.create(JsonAnimalApi::class.java)


    }

}