package com.example.sokogarden

import android.R.attr.data
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

//        find the two edit text, textview and the button by use of their ids
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val signinButton = findViewById<Button>(R.id.signinBtn)
        val signupTextView = findViewById<TextView>(R.id.signuptxt)

//        on the textview set on click listener such that when clicked it navigate to the signup page
        signupTextView.setOnClickListener {
            val intent = Intent(applicationContext, Signup::class.java)
            startActivity(intent)
        }

//        on click of button signin, we need to interact with api end point as we pass two data info, i.e email and password
        signinButton.setOnClickListener {
//            specify the api end point
            val api = "https://kbenkamotho.alwaysdata.net/api/signin"

//            create a RequstParans that will enable u to hold data in form of bundle/package
            val data = RequestParams()

//            Add/append/attach the email and password
            data.put("email", email.text.toString())
            data.put("password", password.text.toString())

//            Import the Api helper
            val helper = ApiHelper(applicationContext)

//            By use of the function post_login inside of the helper class we pass the api and data
            helper.post_login(api, data)


        }
    }
}