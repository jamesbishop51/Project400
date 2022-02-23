package com.edu.project400


import android.app.PendingIntent.getActivity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edu.project400.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.messaging.FirebaseMessaging

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding

    //will replace view binding with better system soon
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
       /* FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast

            Log.d(TAG, "Token::$token")
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })*/

        binding.tvSignUp.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        binding.BtnLogin.setOnClickListener{

            when {
                TextUtils.isEmpty(binding.etLoginEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                            this@LoginActivity,
                            "Please enter email.",
                            Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(binding.etLoginPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                            this@LoginActivity,
                            "Please enter password.",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = binding.etLoginEmail.text.toString().trim { it <= ' ' }
                    val password: String = binding.etLoginPassword.text.toString().trim { it <= ' ' }
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                        this@LoginActivity,
                                        "You are logged in successfully.",
                                        Toast.LENGTH_SHORT
                                ).show()
                                val intent =
                                    Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra(
                                        "user_id",
                                        FirebaseAuth.getInstance().currentUser!!.uid
                                )
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            } else {

                                // If the login is not successful then show error message.
                                Toast.makeText(
                                        this@LoginActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }

        }

    }
}