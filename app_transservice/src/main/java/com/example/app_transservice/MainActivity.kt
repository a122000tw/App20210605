package com.example.app_transservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app_transservice.model.TransService
import com.example.app_transservice.service.TransServiceService
import com.example.app_transservice.viewmodel.TransServiceViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: TransServiceViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(TransServiceViewModel::class.java)
        // 觀察
        viewModel.currentImageURL.observe(this, Observer {
            Picasso.get().load(it).into(iv_album)
        })
        viewModel.currentInfo.observe(this, Observer {
            tv_info.text = it.toString()

        })

        GlobalScope.launch {
            val path = resources.getString(R.string.path)
            viewModel.animals = TransServiceService(path).getAnimals()
            val n = Random.nextInt(viewModel.animals!!.size)
            viewModel.ts = viewModel.animals!![n]

            viewModel.ts = viewModel.animals!![0]
            runOnUiThread {
                viewModel.currentImageURL.value = viewModel.ts!!.album_file
                viewModel.currentInfo.value = viewModel.ts.toString()
            }
         }

        iv_album.setOnClickListener {
            val n = Random.nextInt(viewModel.animals!!.size)
            viewModel.ts = viewModel.animals!![n]
            viewModel.currentImageURL.value = viewModel.ts!!.album_file
            viewModel.currentInfo.value = viewModel.ts.toString()
        }

        iv_album.setOnLongClickListener {
            if(tv_info.visibility == View.VISIBLE) {
                tv_info.visibility = View.GONE
            } else {
                tv_info.visibility = View.VISIBLE
            }

            return@setOnLongClickListener true
        }

    }
}