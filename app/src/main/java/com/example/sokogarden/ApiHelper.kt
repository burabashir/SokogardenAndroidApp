package com.example.sokogarden

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class ApiHelper(private val context: Context) {

    private val client = AsyncHttpClient()

    // =========================
    // GENERAL POST (LOGIN/SIGNUP)
    // =========================
    fun post(url: String, params: RequestParams) {

        client.post(url, params, object : JsonHttpResponseHandler() {

            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>?,
                response: JSONObject
            ) {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>?,
                throwable: Throwable,
                errorResponse: JSONObject?
            ) {
                Toast.makeText(context, "Request failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // =========================
    // LOGIN (✅ FIXED)
    // =========================
    fun postLogin(
        url: String,
        params: RequestParams,
        callback: (Boolean, String) -> Unit   // ✅ ADDED
    ) {

        client.post(url, params, object : JsonHttpResponseHandler() {

            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>?,
                response: JSONObject
            ) {
                try {
                    val success = response.getBoolean("success")
                    val message = response.getString("message")

                    callback(success, message) // ✅ RETURN RESULT

                } catch (e: Exception) {
                    callback(false, "Parsing error")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>?,
                throwable: Throwable,
                errorResponse: JSONObject?
            ) {
                callback(false, "Login failed")
            }
        })
    }

    // =========================
    // LOAD PRODUCTS (UNCHANGED)
    // =========================
    fun loadProducts(
        url: String,
        recyclerView: RecyclerView,
        progressBar: ProgressBar
    ) {

        progressBar.visibility = View.VISIBLE

        client.get(url, object : JsonHttpResponseHandler() {

            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>?,
                response: JSONArray
            ) {

                progressBar.visibility = View.GONE

                try {

                    val productList = ProductAdapter.fromJsonArray(response)

                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.adapter = ProductAdapter(productList)

                    Toast.makeText(context, "Products loaded", Toast.LENGTH_SHORT).show()

                } catch (e: Exception) {
                    Toast.makeText(context, "Parsing error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>?,
                throwable: Throwable,
                errorResponse: JSONArray?
            ) {

                progressBar.visibility = View.GONE
                Toast.makeText(context, "Failed to load products", Toast.LENGTH_SHORT).show()
            }
        })
    }
}