package com.example.app_databinding_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.app_databinding_retrofit.databinding.ActivityMainBinding
import com.example.app_databinding_retrofit.manager.Manager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        mBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

    }
    fun click(view: View) {
        GlobalScope.launch {
            val api = Manager.instance.api
            val count = api.getPosts().execute().body()!!.size
            val id = Random.nextInt(count)
            val post = api.getPost(id).execute().body()
            runOnUiThread {
                mBinding.post = post
            }
        }
    }

}