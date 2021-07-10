package com.study.app_tictactoe_firebase

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val database = Firebase.database
    var notificationManager: NotificationManagerCompat? = null
    val myTTTRef = database.getReference("ttt/game")
    val myTTTLastMarkRef = database.getReference("ttt/last_mark")
    val myWinnerRef = database.getReference("ttt/winner")
    lateinit var context: Context
    lateinit var lastMark: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this

        // 建構Manager
        notificationManager = NotificationManagerCompat.from(this)

        bt_mark.setOnClickListener {
            val mark = bt_mark.tag.toString()
            if(mark.equals("O")) {
                bt_mark.text = "X"
                bt_mark.tag = "X"
            } else {
                bt_mark.text = "O"
                bt_mark.tag = "O"
            }
        }

        myTTTRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val game = snapshot.children
                Log.d("MainActivity", game.toString())
                game.forEach {
                    Log.d("MainActivity", it.key + ":" + it.value)
                    val id = resources.getIdentifier(it.key,"id", context.packageName)
                    findViewById<Button>(id).text = it.value.toString()
                }
                // 判斷贏家
                winner()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        myTTTLastMarkRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                lastMark = snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        myWinnerRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val winner = snapshot.value.toString()
                if (winner.equals("")) {
                    sendByChannel1(winner)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

//        b1.setOnClickListener {
//            if (bt_mark.text.toString().equals("O")) {
//                b1.text = bt_mark.text.toString()
//            } else if (bt_mark.text.toString().equals("X")) {
//                b1.text = bt_mark.text.toString()
//            }
//            myTTTRef.child("b1").setValue(b1.text.toString())
//        }
//
//        b2.setOnClickListener {
//            if (bt_mark.text.toString().equals("O")) {
//                b2.text = bt_mark.text.toString()
//            } else if (bt_mark.text.toString().equals("X")) {
//                b2.text = bt_mark.text.toString()
//            }
//            myTTTRef.child("b2").setValue(b2.text.toString())
//        }
//
//        b3.setOnClickListener {
//            if (bt_mark.text.toString().equals("O")) {
//                b3.text = bt_mark.text.toString()
//            } else if (bt_mark.text.toString().equals("X")) {
//                b3.text = bt_mark.text.toString()
//            }
//            myTTTRef.child("b3").setValue(b3.text.toString())
//        }
//
//        b4.setOnClickListener {
//            if (bt_mark.text.toString().equals("O")) {
//                b4.text = bt_mark.text.toString()
//            } else if (bt_mark.text.toString().equals("X")) {
//                b4.text = bt_mark.text.toString()
//            }
//            myTTTRef.child("b4").setValue(b4.text.toString())
//        }
//
//        b5.setOnClickListener {
//            if (bt_mark.text.toString().equals("O")) {
//                b5.text = bt_mark.text.toString()
//            } else if (bt_mark.text.toString().equals("X")) {
//                b5.text = bt_mark.text.toString()
//            }
//            myTTTRef.child("b5").setValue(b5.text.toString())
//        }
//
//        b6.setOnClickListener {
//            if (bt_mark.text.toString().equals("O")) {
//                b6.text = bt_mark.text.toString()
//            } else if (bt_mark.text.toString().equals("X")) {
//                b6.text = bt_mark.text.toString()
//            }
//            myTTTRef.child("b6").setValue(b6.text.toString())
//        }
//
//        b7.setOnClickListener {
//            if (bt_mark.text.toString().equals("O")) {
//                b7.text = bt_mark.text.toString()
//            } else if (bt_mark.text.toString().equals("X")) {
//                b7.text = bt_mark.text.toString()
//            }
//            myTTTRef.child("b7").setValue(b7.text.toString())
//        }
//
//        b8.setOnClickListener {
//            if (bt_mark.text.toString().equals("O")) {
//                b8.text = bt_mark.text.toString()
//            } else if (bt_mark.text.toString().equals("X")) {
//                b8.text = bt_mark.text.toString()
//            }
//            myTTTRef.child("b8").setValue(b8.text.toString())
//        }
//
//        b9.setOnClickListener {
//            if (bt_mark.text.toString().equals("O")) {
//                b9.text = bt_mark.text.toString()
//            } else if (bt_mark.text.toString().equals("X")) {
//                b9.text = bt_mark.text.toString()
//            }
//            myTTTRef.child("b9").setValue(b9.text.toString())
//        }

    }

    fun tttOnClick(view: View) {

        val tag = view.getTag().toString()
        val path = "b" + tag
        val mark = bt_mark.tag.toString()
        if (mark.equals(lastMark)) {
            return
        }

        myTTTRef.child(path).setValue(mark)
        myTTTLastMarkRef.setValue(mark)
    }

    fun tttResetOnClick(view: View) {

        for (i in 1..9) {
            val path = "b" + i
            myTTTRef.child(path).setValue("")
        }
        myTTTLastMarkRef.setValue("")
        myWinnerRef.setValue("")
    }

    fun winner() {
        var winner: String? = null
        val mark = bt_mark.tag.toString()
        if (b1.text.toString().equals(mark) &&
            b2.text.toString().equals(mark) &&
            b3.text.toString().equals(mark)    ) {
            winner = mark
        }
        if (b1.text.toString().equals(mark) &&
            b4.text.toString().equals(mark) &&
            b7.text.toString().equals(mark)    ) {
            winner = mark
        }
        if (b1.text.toString().equals(mark) &&
            b5.text.toString().equals(mark) &&
            b9.text.toString().equals(mark)    ) {
            winner = mark
        }
        if (b2.text.toString().equals(mark) &&
            b5.text.toString().equals(mark) &&
            b8.text.toString().equals(mark)    ) {
            winner = mark
        }
        if (b9.text.toString().equals(mark) &&
            b6.text.toString().equals(mark) &&
            b3.text.toString().equals(mark)    ) {
            winner = mark
        }
        if (b4.text.toString().equals(mark) &&
            b5.text.toString().equals(mark) &&
            b6.text.toString().equals(mark)    ) {
            winner = mark
        }
        if (b7.text.toString().equals(mark) &&
            b8.text.toString().equals(mark) &&
            b9.text.toString().equals(mark)    ) {
            winner = mark
        }
        if (b7.text.toString().equals(mark) &&
            b5.text.toString().equals(mark) &&
            b3.text.toString().equals(mark)    ) {
            winner = mark
        }

        if (winner != null) {
            myWinnerRef.setValue(mark)
        }


    }

    fun sendByChannel1 (message: String) {
        val intent = Intent(this, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val title: String = "Firebase"
        val notification: Notification = NotificationCompat.Builder(this, App.CHANNEL_1_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setOngoing(true)
            .setContentTitle("Firebase 井字遊戲")
            .setContentText("Winner: " + message + " 贏了")
            .setSubText("2021-7-3")
            .setContentIntent(pIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()
        notificationManager!!.notify(1001, notification)
    }
}