package com.example.app_retrofit_crud.info

data class Employees(
    var email: String,
    val id: Int,
    var name: String,
    var phone: String,
    var salary: Salary
)