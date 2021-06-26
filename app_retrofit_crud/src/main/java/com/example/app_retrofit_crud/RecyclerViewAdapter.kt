package com.example.app_retrofit_crud

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_retrofit_crud.info.Employees
import kotlinx.android.synthetic.main.row.view.*

class RecyclerViewAdapter(val listener: RowClickListener): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    var items: List<Employees> = ArrayList<Employees>()
    fun setListData(items: List<Employees>) {
        this.items = items
    }
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name = view.tv_name
        val basic = view.tv_basic

        fun bind(employees: Employees) {
            name.text = employees.name
            basic.text = employees.salary.basic.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onItemClickListener(items[position])
        }
        holder.bind(items[position])
    }

    interface RowClickListener {
        fun onItemClickListener(employees: Employees)
    }

}