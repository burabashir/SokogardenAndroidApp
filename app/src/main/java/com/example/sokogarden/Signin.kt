package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loopj.android.http.RequestParams

class Signin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signin)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Views
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val signinButton = findViewById<Button>(R.id.signinBtn)
        val signupTextView = findViewById<TextView>(R.id.signuptxt)

        // Navigate to Signup
        signupTextView.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

        // Sign in button click
        signinButton.setOnClickListener {

            val api = "https://dumabashir.alwaysdata.net/api/signin"

            val data = RequestParams()
            data.put("email", email.text.toString().trim())
            data.put("password", password.text.toString().trim())

            val helper = ApiHelper(this)

            // ✅ UPDATED: handle response
            helper.postLogin(api, data) { success, message ->

                runOnUiThread {
                    if (success) {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                        // ✅ OPEN MAIN ACTIVITY
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show()

//            Intent to navigate to main Activity
            Handler(Looper.getMainLooper()).postDelayed({

                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)

            }, 5000) // 5000 milliseconds = 5 seconds
        }
    }
}