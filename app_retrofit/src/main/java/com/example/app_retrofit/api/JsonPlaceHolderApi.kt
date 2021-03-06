package com.example.app_retrofit.api

import com.example.app_retrofit.model.Comment
import com.example.app_retrofit.model.Photo
import com.example.app_retrofit.model.Post
import com.example.app_retrofit.model.users.User
import retrofit2.Call
import retrofit2.http.*

// API 的目的是為了給 client 調用
interface JsonPlaceHolderApi {
    // 查詢 posts
    @GET("posts")
    fun getPosts(): Call<List<Post>>

    // 查詢 posts?userId=2&_sort=id&_order=desc
    @GET("posts")
    fun getPosts(@Query("userId") userId: Int,
                 @Query("_sort") _sort: String,
                 @Query("_order") _order: String): Call<List<Post>>

    // 查詢 posts?userId=2&userId=4_sort=id&_order=desc
    @GET("posts")
    fun getPosts(@Query("userId") userId: Array<Int>,
                 @Query("_sort") _sort: String,
                 @Query("_order") _order: String): Call<List<Post>>

    // 查詢 /posts/3/comments
    @GET("/posts/{id}/comments")
    fun getComments(@Path("id") postId: Int): Call<List<Comment>>

    // 查詢指定 URL 字串
    @GET
    fun getComments(@Url url: String): Call<List<Comment>>

    // 查詢 comments
    @GET("comments")
    fun getComments(): Call<List<Comment>>

    // 查詢 comments (ex: comments?postId=4&_sort=id&_order=desc)
    @GET("comments")
    fun getComments(@QueryMap params: Map<String, String>): Call<List<Comment>>

    // 查詢 users
    @GET(value = "users")
    fun getUsers(): Call<List<User>>

    // 查詢 users
    @GET(value = "photos")
    fun getPhotos(): Call<List<Photo>>

    // 單筆查詢 ex: posts/2
    @GET("/posts/{id}")
    fun getPost(@Path("id") id: Int):Call<Post>

    // 單筆查詢 users/1
    @GET(value = "users/{id}")
    fun getUser(@Path("id") id: Int): Call<User>

}