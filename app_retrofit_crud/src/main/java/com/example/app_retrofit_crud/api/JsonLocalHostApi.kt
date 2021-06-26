package com.example.app_retrofit_crud.api

import com.example.app_retrofit_crud.info.Employees
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path


interface JsonLocalHostApi {

    @GET("/employees")
    fun getEmployees(): Call<List<Employees>>

    @GET("/employees/{id}")
    fun getEmployee(@Path("id") id: Int): Call<Employees>

    @PATCH("/employees/{id}")
    fun updateSalary(@Path("id") id: Int, @Body employees: Employees): Call<Employees>

}