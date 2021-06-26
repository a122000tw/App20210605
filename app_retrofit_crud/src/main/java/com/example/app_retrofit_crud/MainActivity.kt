package com.example.app_retrofit_crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.app_retrofit_crud.info.Employees
import com.example.app_retrofit_crud.manager.EManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity() : AppCompatActivity(), RecyclerViewAdapter.RowClickListener {
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerViewAdapter = RecyclerViewAdapter(this@MainActivity)
            adapter = recyclerViewAdapter
            val divider =
                DividerItemDecoration(applicationContext, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }

        GlobalScope.launch {
            val api = EManager.instance.api
            val employees: List<Employees>? = api.getEmployees().execute().body()
            runOnUiThread {
                title = "員工比數: ${employees!!.size}"
                recyclerViewAdapter.setListData(employees!!)
                recyclerViewAdapter.notifyDataSetChanged()
            }

        }
        btn_update.setOnClickListener {
            GlobalScope.launch {
                val api = EManager.instance.api
                // update
                val id = et_basic.getTag(R.id.emp_id).toString().toInt()
                val name = et_basic.getTag(R.id.emp_name).toString()
                val basic = et_basic.text.toString().toInt()
                val employee = api.getEmployee(id).execute().body()

                if (employee != null) {
                    employee.salary.basic = basic
                    if (api.updateSalary(id, employee).execute().isSuccessful) {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "$name 的薪資修改成功", Toast.LENGTH_SHORT)
                                .show()
                        }
                        // 重新查詢
                        val employees: List<Employees>? = api.getEmployees().execute().body()
                        runOnUiThread {
                            title = "員工筆數: ${employees!!.size}"
                            recyclerViewAdapter.setListData(employees!!)
                            recyclerViewAdapter.notifyDataSetChanged()
                        }
                    }

                }
            }

        }


    }
    override fun onItemClickListener(employees: Employees) {
        Toast.makeText(applicationContext, employees.toString(), Toast.LENGTH_SHORT).show()
        et_basic.setTag(R.id.emp_id, employees.id)
        et_basic.setTag(R.id.emp_name, employees.name)
        et_basic.setText(employees.salary.basic.toString())
    }
}
