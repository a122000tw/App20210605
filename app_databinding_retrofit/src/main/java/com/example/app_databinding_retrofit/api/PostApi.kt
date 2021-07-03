package com.example.app_databinding_retrofit.api




import com.example.app_databinding_retrofit.viewmodel.PostViewModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {

    // 查詢 Post
    @GET("posts/")
    fun getPosts(): Call<List<PostViewModel>>

    @GET("posts/{id}")
    fun getPost(@Path("id") id: Int): Call<PostViewModel>

}