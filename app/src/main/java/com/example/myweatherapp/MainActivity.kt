package com.example.myweatherapp

import android.app.DownloadManager.Request
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myweatherapp.databinding.ActivityMainBinding

const val API_KEY = "29dd79570f01448b8b670632230702"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData("London")


    }
    private fun getData(cityName: String){
        val url = "http://api.weatherapi.com/v1/current.json?" +
                "key=$API_KEY" + "&q=$cityName" + "&aqi=no"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(com.android.volley.Request.Method.GET,
            url,
            {response ->
                Log.d("MyLog", "Response: $response")
            },
            {
                Log.d("MyLog", "Error: $it")
            }
        )
        queue.add(stringRequest)

    }
}