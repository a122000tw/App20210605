package com.study.model

data class OpenWeather(val name: String,
                       val country: String,
                       val weatherMain: String,
                       val weatherDescription: String,
                       val weatherIcon: String,
                       val mainTemp: Double,
                       val mainFeelsLike: Double,
                       val mainHumidity: Double,
                       val cloudsAll: Int,
                       val dt: Int
) {
    val weatherIconUrl: String = "http://openweathermap.org/img/wn/${weatherIcon}@4x.png"

}