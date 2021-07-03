package com.example.app_databinding_retrofit2.api





import com.example.app_databinding_retrofit2.model.Post
import retrofit2.Call
import retrofit2.http.*

interface PostApi {

    // 查詢 Post
    @GET("/posts")
    fun getPosts(): Call<List<Post>>

    @GET("/posts/{id}")
    fun getPost(@Path("id") id: Int): Call<Post>

    @POST("/posts")
    fun addPost(@Body post: Post): Call<Post>

    @DELETE("/posts")
    fun deletePost(@Body post: Post): Call<Post>

}