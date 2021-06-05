package com.example.app_transservice.service

import com.example.app_transservice.model.TransService
import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*

class TransServiceService (val path: String) {
    fun getAnimals(): List<TransService> {
        val client = OkHttpClient()

        val request = Request.Builder().url(path).build()

        client.newCall(request).execute().use {
            val json = it.body!!.string()
            return Gson().fromJson(json, Array<TransService>::class.java).toList()
        }

    }


}