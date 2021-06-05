package com.example.app_transservice.api

import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.Request

fun main() {

    val path = "https://data.coa.gov.tw/Service/OpenData/TransService.aspx?UnitId=QcbUEzN6E6DL"
    val client = OkHttpClient()
    val request = Request.Builder().url(path).build()

    client.newCall(request).execute().use {
        val json = it.body!!.string()
        // Array to list
        val animal: List<Animal> = Gson().fromJson(json, Array<Animal>::class.java).toList()
        print("animal = ${animal}")

//        val root = JsonParser.parseString(json).asJsonArray[0].asJsonObject
//        val album_file = root.get("album_file").toString().replace("\"", "")
//        val animal_kind = root.get("animal_kind").toString().replace("\"", "")
//        val animal_sex = root.get("animal_sex").toString().replace("\"", "")
//        val animal_colour = root.get("animal_colour").toString().replace("\"", "")

    }


}