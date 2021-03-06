package com.study.app_ticket_firebase

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.study.app_ticket_firebase.adapter.RecyclerViewAdapter
import com.study.app_ticket_firebase.models.Order
import com.study.app_ticket_firebase.models.Ticket
import kotlinx.android.synthetic.main.activity_order_list.*
import kotlinx.android.synthetic.main.order.*
import java.text.SimpleDateFormat
import java.util.*

class OrderListActivity : AppCompatActivity(), RecyclerViewAdapter.OrderOnItemClickListener {
    val database = Firebase.database
    val myRef = database.getReference("ticketsStock")

    lateinit var context: Context
    lateinit var userName: String
    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
        context = this
        // 設定 app title
        title = resources.getString(R.string.activity_order_list_title_txt)
        // 取得使用者名稱 (上一頁傳來的 userName 參數資料)
        userName = intent.getStringExtra("userName").toString()
        // 判斷是否有 userName 的資料
        title = if (userName == null || userName.equals("") || userName.equals("null")) {
            String.format(title.toString(), resources.getString(R.string.all_order_txt))
        } else {
            String.format(title.toString(), userName)
        }
        // Read from database
        myRef.addValueEventListener(object : ValueEventListener {
            lateinit var orderList: MutableList<Order>
            override fun onDataChange(snapshot: DataSnapshot) {
                orderList = mutableListOf<Order>()
                val children = snapshot.children
                children.forEach {
                    if (it.key.toString() == "transaction") {
                        // 未指名 userName
                        if (userName == null || userName.equals("") || userName.equals("null")) {
                            it.children.forEach {
                                record -> addRecord(it, record.key.toString())
                            }
                        } else { // 有指名 userName
                            addRecord(it, userName)
                        }
                    }
                }
                tv_info.text = orderList.toString()
                // 更新 recycler view 的 orderList資料
                recyclerViewAdapter.setOrderList(orderList)
                // 通知變更(刷新)
                recyclerViewAdapter.notifyDataSetChanged()
            }
            private fun addRecord(it: DataSnapshot, userName: String) {
                try { // try-catch 解決按下紀錄後返回訂票成功之後會發生 null 錯誤的問題
                    it.child(userName).children.forEach {
                        val key = it.key.toString()
                        val allTickets = it.child("allTickets").value.toString().toInt()
                        val roundTrip = it.child("roundTrip").value.toString().toInt()
                        val oneWay = it.child("oneWay").value.toString().toInt()
                        val total = it.child("total").value.toString().toInt()
                        val ticket = Ticket(userName, allTickets, roundTrip, oneWay, total)
                        val order = Order(key, ticket)
                        orderList.add(order)
                    }
                } catch (e: Exception) {

                }

            }
            override fun onCancelled(error: DatabaseError) {

            }

        })

        // Init RecyclerView
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = RecyclerViewAdapter(this@OrderListActivity)
            adapter = recyclerViewAdapter

        }

    }
    // 產生 QR-Code
    private fun genQRCode(order: Order) {
        // 產生 Json
        val orderJsonString = Gson().toJson(order).toString()
        Toast.makeText(context, "click" + orderJsonString, Toast.LENGTH_SHORT).show()
        // 產生 QR-Code
        val writer = QRCodeWriter()
        // 產生 bit 矩陣資料
        val bitMatrix = writer.encode(orderJsonString, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        // 產生 bitmap 圖像空間
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        // 將 bitMatrix 矩陣資料 注入至 bitmap 圖像空間
        for (x in 0 until width) {
            for (y in 0 until height) {
                // 有資料放黑色反之為白色
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        // 建立自製 ImageView
        val qrcodeImageView = ImageView(context)
        qrcodeImageView.setImageBitmap(bitmap)
        // 建立 AlterDialog 顯示 bitmap 圖像
        AlertDialog.Builder(context)
            .setView(qrcodeImageView)
            .create()
            .show()
    }
    // 使用票券
    private fun useTicket(order: Order) {
        runOnUiThread {
            val result_text = Gson().toJson(order, Order::class.java) // 解碼內容
            AlertDialog.Builder(context)
                .setTitle("QRCode 內容")
                .setMessage("${result_text}")
                .setPositiveButton(
                    "使用"
                ) { dialogInterface, i ->
                    // 取得該票路徑
                    val path = order.ticket.userName + "/" + order.key
                    val transPath = "transaction/$path"
                    // 刪除該票
                    myRef.child(transPath).removeValue()
                    // 建立 transaction_history
                    val ts = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS").format(Date()).toString()
                    myRef.child("transaction_history/$path/ts").setValue(ts)
                    myRef.child("transaction_history/$path/key").setValue(order.key)
                    myRef.child("transaction_history/$path/userName")
                        .setValue(order.ticket.userName)
                    myRef.child("transaction_history/$path/allTickets")
                        .setValue(order.ticket.allTickets)
                    myRef.child("transaction_history/$path/oneWay").setValue(order.ticket.oneWay)
                    myRef.child("transaction_history/$path/roundTrip")
                        .setValue(order.ticket.roundTrip)
                    myRef.child("transaction_history/$path/total").setValue(order.ticket.total)
                    myRef.child("transaction_history/$path/json").setValue(result_text)
                }
                .setNegativeButton("取消") { dialogInterface, i -> finish() }
                .create()
                .show()
        }
    }

    // 按一下可以產生 QR-Code 或 使用票券(後台使用)
    override fun OnItemClickListener(order: Order) {
        // 無userName 表示後台專用
        if (userName == null || userName.equals("") || userName.equals("null")) {
            useTicket(order)
        } else {
            genQRCode(order)
        }
    }

    // 長按一下可以退票
    override fun OnItemLongClickListener(order: Order) {
        // 組合訂單路徑
        val path = "transaction/" + order.ticket.userName + "/" + order.key
        // 刪除訂單路徑資料
        AlertDialog.Builder(context)
            .setTitle("票務處置")
            .setMessage("票券: ${order.ticket.userName} [${order.key}]")
            .setPositiveButton("退票") { dialog, which ->
                myRef.child(path).removeValue()
                // firebase's totalAmount 要加回所退回的數量(order.ticket.allTickets)
                myRef.child("totalAmount").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val totalAmount = snapshot.value.toString().toInt()
                        val newTotalAmount = totalAmount + order.ticket.allTickets
                        myRef.child("totalAmount").setValue(newTotalAmount)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
                // 寫入退票紀錄檔
                // firebase "transaction_refund"
                val sdf = SimpleDateFormat("yyyyMMddHHmmssSSS")
                val dateString = sdf.format(Date())
                val orderJsonString = Gson().toJson(order).toString()
                myRef.child("transaction_refund/" + dateString + "/json").setValue(orderJsonString)
                myRef.child("transaction_refund/" + dateString + "/order/key").setValue(order.key)
                myRef.child("transaction_refund/" + dateString + "/order/ticket/userName").setValue(order.ticket.userName)
                myRef.child("transaction_refund/" + dateString + "/order/ticket/allTickets").setValue(order.ticket.allTickets)
                myRef.child("transaction_refund/" + dateString + "/order/ticket/roundTrip").setValue(order.ticket.roundTrip)
                myRef.child("transaction_refund/" + dateString + "/order/ticket/oneWay").setValue(order.ticket.oneWay)
                myRef.child("transaction_refund/" + dateString + "/order/ticket/total").setValue(order.ticket.total)
            }
            .setNegativeButton("取消", null)
            .create()
            .show()
        Toast.makeText(context, "long click" + order.toString(), Toast.LENGTH_SHORT).show()
    }
    // 根據 ticket's total 排序
    fun ticketTotalSort(view: View) {
        // ▲▼
        Order.orderDelta *= -1
        if(Order.orderDelta == -1) {
            (view as TextView).text = resources.getString(R.string.total_txt) + "▼"
        } else {
            (view as TextView).text = resources.getString(R.string.total_txt) + "▲"
        }

        val orderList = recyclerViewAdapter.getOrderList()
        Collections.sort(orderList)
        recyclerViewAdapter.notifyDataSetChanged()
    }

}
