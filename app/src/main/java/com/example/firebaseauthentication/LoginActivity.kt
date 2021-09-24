package com.example.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sign_up_button.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }

        login_button.setOnClickListener {
            val email = editTextTextEmailAddress.text.toString().trim { it <= ' ' }
            val password = editTextTextPassword.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    val firebaseUser = it.result!!.user!!
                    val intent = Intent(
                        this@LoginActivity,
                        MainActivity::class.java
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("user_id", firebaseUser.uid)
                    intent.putExtra("email_id", email)
                    startActivity(intent)
                    finish()

                    Toast.makeText(this@LoginActivity,
                    "Success!",
                    Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginActivity,
                    "Invalid Credentials!",
                    Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}