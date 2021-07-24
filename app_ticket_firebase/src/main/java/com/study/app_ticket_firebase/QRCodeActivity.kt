package com.study.app_ticket_firebase

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class QRCodeActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CODE = 200
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)
        context = this

        // check permission
        if (checkPermission()) {
            // 執行QRCode程式
        } else {
            // 啟動動態核准對話框
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CODE)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            Toast.makeText(context, grantResults[0].toString(), Toast.LENGTH_SHORT).show()
            // 執行ORCode程式
            // grantResults[0] == 0 -> Accept
            // grantResults[0] == -1 -> Deny
            if (grantResults[0] == 0) {
                runProgram()
            } else if (grantResults[0] == -1){
                finish()
            }
        }
    }


    // 持行QRCode程式
    fun runProgram() {
        title = "執行QRCode程式..."
    }

    // 使用者是否同意使用權限(Ex: Camera)
    private fun checkPermission(): Boolean {
        val check = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        val result = (check == PackageManager.PERMISSION_GRANTED)
        if (result) {
            Toast.makeText(context, "Permission is OK", Toast.LENGTH_SHORT).show()
            return true
        } else {
            Toast.makeText(context, "Permission is not granted", Toast.LENGTH_SHORT).show()
            return false
        }


    }

}