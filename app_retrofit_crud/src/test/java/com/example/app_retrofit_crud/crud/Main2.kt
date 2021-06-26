package com.example.app_retrofit_crud.crud

import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Url

interface OpenWeatherService {

    @GET
    fun getStringResponse(@Url url: String?): Call<String>


}

fun main() {
    // http://api.openweathermap.org/data/2.5/weather?q=taoyuan,tw&appid=29f2b281a63b488da3604490be29b298
    // 透過 retrofit 可以取得 temp 現在溫度
    val path = "weather?q=taoyuan,tw&appid=29f2b281a63b488da3604490be29b298"
    val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl("http://api.openweathermap.org/data/2.5/")
        .build()
    val api = retrofit.create(OpenWeatherService::class.java)
    //println(api.getStringResponse(path).execute().body())

    val json = api.getStringResponse(path).execute().body()
    val root = JsonParser.parseString(json).asJsonObject
    val main = root.getAsJsonObject("main")
    var temp = main.get("temp").asDouble
    temp -= 273.15
    println("temp: ${temp}")

}