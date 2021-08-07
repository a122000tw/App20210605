package com.study.app_login_firebase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var context: Context
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        auth = Firebase.auth
    }

    fun changePassword(view: View) {
        val oobcode = ""
        val newPassword = "000000"
        auth.confirmPasswordReset(oobcode, newPassword)
    }
}