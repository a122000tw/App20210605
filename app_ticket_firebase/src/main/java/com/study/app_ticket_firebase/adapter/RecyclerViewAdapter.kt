package com.study.app_ticket_firebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.study.app_ticket_firebase.R
import com.study.app_ticket_firebase.models.Order
import kotlinx.android.synthetic.main.order.view.*

// 適配器 (配置每一筆訂單紀錄)    (第二步) -> : RecyclerView.Adapter<RecyclerViewAdapter.OrderViewHolder>()
class RecyclerViewAdapter(): RecyclerView.Adapter<RecyclerViewAdapter.OrderViewHolder>() {
    private var orderList: List<Order> = ArrayList()
    fun setOrderList(orderList: List<Order>) {
        this.orderList = orderList
    }

    // View 配置方式(第一步)
    class OrderViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val userName = view.tv_userName
        private val allTickets = view.tv_allTickets
        private val roundTrip = view.tv_roundTrip
        private val oneWay = view.tv_oneWay
        private val total = view.tv_total
        fun bind(order: Order) {
            userName.text = order.ticket.userName
            allTickets.text = order.ticket.allTickets.toString()
            roundTrip.text = order.ticket.roundTrip.toString()
            oneWay.text = order.ticket.oneWay.toString()
            total.text = order.ticket.total.toString()

        }

    }
    // (第三步)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

}