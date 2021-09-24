package com.example.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        signup_button.setOnClickListener {
            val email = signup_editTextTextEmailAddress.text.toString().trim { it <= ' ' }
            val password = signup_editTextTextPassword.text.toString().trim { it <= ' ' }

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val firebaseUser = it.result!!.user!!
                    Toast.makeText(this@RegisterActivity, "Success", Toast.LENGTH_SHORT).show()

                    val intent = Intent(
                        this@RegisterActivity,
                        MainActivity::class.java
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("user_id", firebaseUser.uid)
                    intent.putExtra("email_id", email)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        it.exception!!.message, Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }
}