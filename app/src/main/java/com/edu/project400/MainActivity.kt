package com.edu.project400

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import com.amplifyframework.core.Amplify
import com.google.firebase.messaging.FirebaseMessaging
import com.google.android.gms.tasks.OnCompleteListener
import android.content.ContentValues.TAG
import java.io.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")
        //needed for sending the firebase notifications from the python code.
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log
            tv_firebase_token.text = "Token: $token"

        })

        tv_user_id.text = "User ID :: $userId"
        tv_email_id.text = "Email ID :: $emailId"

        btn_logout.setOnClickListener {
            // Logout from app.
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
        btn_images.setOnClickListener{
            val intent = Intent(this@MainActivity, ImageActivity::class.java)
            intent.putExtra("email_id",emailId)
            intent.putExtra("user_id",userId)
            startActivity(intent)
            finish()
        }

    }

}