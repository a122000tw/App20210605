package com.example.app_retrofit_crud.crud_db


import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface JsonDBService {
    @GET("/users")
    fun getUsers(): Call<List<User>>

    @POST("/users")
    fun createUsers(@Body user: User): Call<User>

    @GET("/users/{id}")
    fun getUsers(@Path("id") id: Int): Call<User>

    @PUT("/users/{id}")
    fun updateUsers(@Path("id") id: Int, @Body user: User): Call<User>

    @PATCH("/users/{id}")
    fun patchUsers(@Path("id") id: Int, @Body user: User): Call<User>

    @DELETE("/users/{id}")
    fun deleteUsers(@Path("id") id: Int): Call<Void>
}

fun main() {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)

    val okHttpclient = OkHttpClient().newBuilder()
        .addInterceptor(logging)
        .addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                // User-Agent 加上 "我是 chrome etc..."
                val oldRequest = chain.request()
                val newRequest = oldRequest.newBuilder()
                    .header("User-Agent", "chrome")
                    .build()
                return chain.proceed(newRequest)
            }

        })
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpclient)
        .build()

    val api = retrofit.create(JsonDBService::class.java)
    // 查詢
//    println(api.getUsers().execute().isSuccessful)
//
//    // 新增
//    // id = 2 name = Mary age = 19
//    val user = User(2, "Mary", 19)
//    println(api.createUsers(user).execute().isSuccessful)

    // 修改 PUT id = 2, name = Jane age = 20
    // 修改 PATCH id = 2 age = 20
//    var user = api.getUsers(2).execute().body()
//    println(user)
//    if (user != null) {
//        user.id = 2
//        user.name = "Jane"
//        user.age = 20
//        println(api.updateUsers(2, user).execute().isSuccessful)
//    }
    // 修改 PATCH id = 2 age = 20
    //var user = User(null, null, 21)
    //println(api.patchUsers(2, user).execute().isSuccessful)

    // 刪除 id = 2
    println(api.deleteUsers(2).execute().isSuccessful)

}