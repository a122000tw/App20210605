package com.study.app_ticket_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class OrderListActivity : AppCompatActivity() {
    lateinit var userName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
        // 設定 app title
        var title_text = resources.getString(R.string.activity_order_list_title_txt)
        // 取得使用者名稱 (上一頁傳來的 userName 參數資料)
        userName = intent.getStringExtra("userName").toString()
        // 判斷是否有 userName 的資料
        if (!(userName == null || userName.equals("") || userName.equals("null"))) {
            title_text = title_text + " - " + userName
        }
        // 設定 app title
        title = title_text
    }
}