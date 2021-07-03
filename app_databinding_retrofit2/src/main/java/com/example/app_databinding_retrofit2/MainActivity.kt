package com.example.app_databinding_retrofit2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_databinding_retrofit2.manager.Manager
import com.example.app_databinding_retrofit2.model.Post
import com.example.app_databinding_retrofit2.viewmodel.PostViewModel
import com.github.javafaker.Faker
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        viewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        viewModel.posts.observe(this, Observer {
            GlobalScope.launch {
                recyclerView.adapter = PostAdapter(it)
            }
        })


    }
    fun list(view: View) {
        GlobalScope.launch {
            val api = Manager.instance.api
            val post: List<Post> = api.getPosts().execute().body()!!
            runOnUiThread {
                recyclerView.adapter = PostAdapter(post)
            }

        }
    }

    fun add(view: View) {
        GlobalScope.launch {
            val faker = Faker()
            val api = Manager.instance.api
            val id = Random.nextInt(10000)
            val post = Post(id, faker.book().title(), faker.book().author())
            val status = api.addPost(post).execute().isSuccessful
            runOnUiThread {
                Toast.makeText(applicationContext, status.toString(), Toast.LENGTH_SHORT).show()
                viewModel.posts.value!!.add(post)
                recyclerView.adapter?.notifyDataSetChanged()
            }

        }
    }


    class PostAdapter(private var list: List<Post>) :
        RecyclerView.Adapter<PostAdapter.ViewHolder>() {

        inner class ViewHolder(var dataBinding: ViewDataBinding) :
            RecyclerView.ViewHolder(dataBinding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.recyclerview_row, parent, false
                )
            )
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val binding: ViewDataBinding = holder.dataBinding
            // 数据绑定库在該App module 中生成一个名为 BR 的类，其中包含用于数据绑定的资源的 ID。
            binding.setVariable(BR.post, list[position])
        }
    }
}