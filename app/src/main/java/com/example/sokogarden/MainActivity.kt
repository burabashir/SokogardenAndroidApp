package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var signupBtn: Button
    lateinit var signinBtn: Button
    lateinit var welcomeText: TextView
    lateinit var logoutBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // =========================
        // INIT VIEWS
        // =========================
        signupBtn = findViewById(R.id.signupBtn)
        signinBtn = findViewById(R.id.signinBtn)
        welcomeText = findViewById(R.id.welcomeText)
        logoutBtn = findViewById(R.id.logoutBtn)

        // =========================
        // BUTTON ACTIONS
        // =========================
        signupBtn.setOnClickListener {
            startActivity(Intent(this, Signup::class.java))
        }

        signinBtn.setOnClickListener {
            startActivity(Intent(this, Signin::class.java))
        }

        // =========================
        // SESSION CHECK
        // =========================
        val prefs = getSharedPreferences("user_session", MODE_PRIVATE)
        val username = prefs.getString("username", null)

        if (username != null) {
            welcomeText.text = "Welcome $username"
            welcomeText.visibility = View.VISIBLE
            logoutBtn.visibility = View.VISIBLE

            signupBtn.visibility = View.GONE
            signinBtn.visibility = View.GONE
        } else {
            welcomeText.visibility = View.GONE
            logoutBtn.visibility = View.GONE

            signupBtn.visibility = View.VISIBLE
            signinBtn.visibility = View.VISIBLE
        }

        logoutBtn.setOnClickListener {
            prefs.edit().clear().apply()

            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // =========================
        // 🔥 IMPORTANT FIX: RecyclerView SETUP
        // =========================
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        // ✅ FIX 1: ALWAYS set LayoutManager (VERY IMPORTANT)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // =========================
        // API URL
        // =========================
        val url = "https://dumabashir.alwaysdata.net/api/get_products_details"

        // =========================
        // API HELPER
        // =========================
        val helper = ApiHelper(applicationContext)

        // =========================
        // CALL API TO LOAD PRODUCTS
        // =========================
        helper.loadProducts(url, recyclerView, progressBar)

        // =========================
        // DEBUG CHECK (REMOVE LATER IF WORKING)
        // =========================
        Toast.makeText(this, "Loading products...", Toast.LENGTH_SHORT).show()

//        Find the about btn by use of its ids and have the intent
        val aboutbtn = findViewById<Button>(R.id.aboutbtn)
//        below is the intent to about acrivity
        aboutbtn.setOnClickListener {
            val intent = Intent(applicationContext, About::class.java)
            startActivity(intent)
        }
    }
}