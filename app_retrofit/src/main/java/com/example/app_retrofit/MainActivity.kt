package com.example.app_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.app_retrofit.manager.RetrofitManager
import com.example.app_retrofit.model.Comment
import com.example.app_retrofit.model.Post
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            val api = RetrofitManager.instance.api

            //val call: Call<List<Post>> = api.getPosts()

            // 方法一 :
//            val posts = call.execute().body()
//            Log.d("MainActivity", posts.toString())
            // 方法二 :
            //call.enqueue(getPosts())
            btn_posts.setOnClickListener {
               //api.getPosts().enqueue(getPosts())
                api.getPost(2).enqueue(getPost())
            }
            btn_comments.setOnClickListener {
                //api.getComments().enqueue(getComments())
                //api.getComments("/posts/2/comments").enqueue(getComments())
                val params = HashMap<String, String>()
                params.put("postId", "4")
                params.put("_sort", "id")
                params.put("_order", "desc")
                api.getComments(params).enqueue(getComments())
            }
            btn_posts2_order.setOnClickListener {
                //api.getPosts(2, "id", "desc").enqueue(getPosts())
                api.getPosts(arrayOf(2, 4), "id", "desc").enqueue(getPosts())

            }
            btn_comments_by_id.setOnClickListener {
                api.getComments(3).enqueue(getComments())
            }

        }

    }

    fun getPost(): Callback<Post> {
        val cb = object: Callback<Post> {

            // Server 端有回應
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (!response.isSuccessful) {
                    // (Ex: 404 找不到 page 的錯誤)
                    Log.d("MainActivity", "Is not successful:  ${response.code()}")
                    return
                }
                val posts = response.body()
                Log.d("MainActivity", posts.toString())
                Log.d("MainActivity", posts!!.toString())
                // UI 呈現
                runOnUiThread {
                    tv_posts.text = posts!!.toString() +  "\n" + posts.toString()
                }

            }
            // 無法連線 (Ex: 找不到主機 hostname)
            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.d("MainActivity", "Fail: ${t.message}")
            }
        }
        return cb
    }

    fun getComments(): Callback<List<Comment>> {
        val cb = object: Callback<List<Comment>> {

            // Server 端有回應
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (!response.isSuccessful) {
                    // (Ex: 404 找不到 page 的錯誤)
                    Log.d("MainActivity", "Is not successful:  ${response.code()}")
                    return
                }
                val comments = response.body()
                Log.d("MainActivity", comments.toString())
                Log.d("MainActivity", comments!!.size.toString())
                // UI 呈現
                runOnUiThread {
                    tv_posts.text = comments!!.size.toString() +  "\n" + comments.toString()
                }

            }
            // 無法連線 (Ex: 找不到主機 hostname)
            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.d("MainActivity", "Fail: ${t.message}")
            }
        }
        return cb
    }


    fun getPosts(): Callback<List<Post>> {
        val cb = object: Callback<List<Post>> {

            // Server 端有回應
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (!response.isSuccessful) {
                    // (Ex: 404 找不到 page 的錯誤)
                    Log.d("MainActivity", "Is not successful:  ${response.code()}")
                    return
                }
                val posts = response.body()
                Log.d("MainActivity", posts.toString())
                Log.d("MainActivity", posts!!.size.toString())
                // UI 呈現
                runOnUiThread {
                    tv_posts.text = posts!!.size.toString() +  "\n" + posts.toString()
                }

            }
            // 無法連線 (Ex: 找不到主機 hostname)
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.d("MainActivity", "Fail: ${t.message}")
            }
        }
        return cb
    }

}