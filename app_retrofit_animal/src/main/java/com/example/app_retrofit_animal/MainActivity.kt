package com.example.app_retrofit_animal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.app_retrofit_animal.manager.AnimalManager
import com.example.app_retrofit_animal.model.AnimalInfo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初始配置 recyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerViewAdapter = RecyclerViewAdapter()
            adapter = recyclerViewAdapter
            val divider = DividerItemDecoration(applicationContext, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }


        GlobalScope.launch {
            val api = AnimalManager.instance.api
            val uid = resources.getString(R.string.uid)
            val animals: List<AnimalInfo>? = api.getAnimals(uid).execute().body()
            // Log.d("MainActivity", api.getAnimals("QcbUEzN6E6DL").execute().body().toString())
            runOnUiThread {
                title = "寵物領養筆數: ${animals!!.size}"
                recyclerViewAdapter.setListData(animals!!)
                recyclerViewAdapter.notifyDataSetChanged()
            }

        }
    }
}