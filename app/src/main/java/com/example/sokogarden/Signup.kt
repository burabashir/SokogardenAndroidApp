package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loopj.android.http.RequestParams

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        find all views by use of their ids
        val username = findViewById<EditText>(R.id.username)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val phone = findViewById<EditText>(R.id.phone)
        val signupButton = findViewById<Button>(R.id.signupBtn)
        val signupTextView = findViewById<TextView>(R.id.signuptxt)

//        below when a person clicks on the textview it navigate to the signin page
        signupTextView.setOnClickListener {
            val intent = Intent(applicationContext, Signin::class.java)
            startActivity(intent)
        }

//        on click of signup button we want to regester a person
        signupButton.setOnClickListener {
//            Specify the api endpoint
            val api = "https://dumabashir.alwaysdata.net/api/signup"

//            Create a requestParam ~it is where we will hold the data
            val data = RequestParams()

//            Add/append/attach the username, email, password and phone on data
            data.put("username", username.text.toString().trim())
            data.put("email", email.text.toString().trim())
            data.put("password", password.text.toString().trim())
            data.put("phone", phone.text.toString().trim())

//            Import the Api Helper
            val helper = ApiHelper(applicationContext)

//            Inside of helper class access the function post
            helper.post(api, data)

//            clearing previous record
            username.text.clear()
            email.text.clear()
            password.text.clear()
            phone.text.clear()

//            Intent to navigate to main Activity
            Handler(Looper.getMainLooper()).postDelayed({

                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)

            }, 5000) // 5000 milliseconds = 5 seconds
        }
    }
}
