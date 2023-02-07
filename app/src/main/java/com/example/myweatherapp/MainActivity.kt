package com.example.myweatherapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myweatherapp.databinding.ActivityMainBinding
import org.json.JSONObject

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
                parseData(response)
            },
            {
                Log.d("MyLog", "Error: $it")
            }
        )
        queue.add(stringRequest)

    }

    private fun parseData(result: String?) {
        val responseMainObject = result?.let { JSONObject(it) }
        val itemDayWeather = OneDayData(
            responseMainObject?.optJSONObject("current")?.getString("temp_c"),
            responseMainObject?.optJSONObject("current")?.getJSONObject("condition")?.getString("text"),
            responseMainObject?.optJSONObject("current")?.getString("humidity")
        )
        Log.d("MyLog", "$itemDayWeather")
    }
}