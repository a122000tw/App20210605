package com.study.service

import com.google.gson.JsonParser
import com.study.model.OpenWeather
import okhttp3.OkHttpClient
import okhttp3.Request

class OpenWeatherService(val appid: String, var path: String) {
    fun getOpenWeather(q: String): OpenWeather {

        path = path.format(q, appid)
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(path)
            .build()

        client.newCall(request).execute().use {
            val json = it.body!!.string()
            val root = JsonParser.parseString(json).asJsonObject
            val name = root.get("name").toString().replace("\"", "")
            val country = root.getAsJsonObject("sys").get("country").toString().replace("\"", "")
            val weather = root.getAsJsonArray("weather")[0]
                .asJsonObject
            val weatherMain = weather.get("main").toString().replace("\"", "")
            val weatherDescription = weather.get("description").toString().replace("\"", "")
            val weatherIcon = weather.get("icon").toString().replace("\"", "")
            val main = root.getAsJsonObject("main")
            val mainTemp = main.get("temp").asDouble
            val mainFeelsLike = main.get("feels_like").asDouble
            val mainHumidity = main.get("humidity").asDouble
            val cloudsAll = root.getAsJsonObject("clouds").get("all").asInt
            val dt = root.get("dt").asInt

            val ow = OpenWeather(name, country, weatherMain, weatherDescription, weatherIcon, mainTemp, mainFeelsLike, mainHumidity, cloudsAll, dt)

            return ow
        }
    }
}