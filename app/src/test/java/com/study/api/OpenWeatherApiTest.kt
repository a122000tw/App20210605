package com.study.api

import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.Request

fun main() {

    val q = "taoyuan,tw"
    val appid = "29f2b281a63b488da3604490be29b298"
    val path = "http://api.openweathermap.org/data/2.5/weather?q=${q}&appid=${appid}"
    val client = OkHttpClient()

    val request = Request.Builder().url(path).build()

    client.newCall(request).execute().use {
        val json = it.body!!.string()
        println("json: ${json}")
        // 利用 Gson 分析出需要的資料
        // sys.country. name
        // weather.main, weather.description, weather.icon
        // main.temp, main.humidity, main.feels_like
        // clouds.all
        // dt
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

        println(name)
        println(country)
        println(weatherMain)
        println(weatherDescription)
        println(weatherIcon)
        println(mainTemp)
        println(mainFeelsLike)
        println(mainHumidity)
        println(cloudsAll)
        println(dt)

        val ow = OpenWeather(name, country, weatherMain, weatherDescription, weatherIcon, mainTemp, mainFeelsLike,mainHumidity, cloudsAll, dt)

        print(ow)
        println(ow.weatherIconUrl)


    }


}