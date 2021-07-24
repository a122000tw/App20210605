package com.study.app_ticket_firebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.study.app_ticket_firebase.R
import com.study.app_ticket_firebase.models.Order
import kotlinx.android.synthetic.main.order.view.*

// 適配器 (配置每一筆訂單紀錄)    (第二步) -> : RecyclerView.Adapter<RecyclerViewAdapter.OrderViewHolder>()
class RecyclerViewAdapter(val listener: OrderOnItemClickListener): RecyclerView.Adapter<RecyclerViewAdapter.OrderViewHolder>() {
    private var orderList: List<Order> = ArrayList<Order>()
    fun setOrderList(orderList: List<Order>) {

        this.orderList = orderList
    }

    fun getOrderList(): List<Order> {
        return this.orderList
    }

    // View 配置方式(第一步)
    class OrderViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val key = view.tv_key
        private val userName = view.tv_userName
        private val allTickets = view.tv_allTickets
        private val roundTrip = view.tv_roundTrip
        private val oneWay = view.tv_oneWay
        private val total = view.tv_total
        fun bind(order: Order) {
            key.text = "key: ${order.key}"
            userName.text = order.ticket.userName
            allTickets.text = order.ticket.allTickets.toString()
            roundTrip.text = order.ticket.roundTrip.toString()
            oneWay.text = order.ticket.oneWay.toString()
            total.text = order.ticket.total.toString()
            total.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
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
        holder.itemView.setOnClickListener {
            listener.OnItemClickListener(order)
        }
        holder.itemView.setOnLongClickListener {
            listener.OnItemLongClickListener(order)
            true
        }
        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    interface OrderOnItemClickListener {
        fun OnItemClickListener(order: Order)
        fun OnItemLongClickListener(order: Order)
    }

}