package com.example.myweatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

        binding.apply {
            btnSearch.setOnClickListener {

                fun isEmpty(): Boolean {
                    if (userInput.text.isNullOrEmpty())
                        userInput.error = getString(R.string.emptyField)
                    return userInput.text.isNullOrEmpty()
                }

                if(!isEmpty()){
                getData(userInput.text.toString())
                }
            }
        }
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
                Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_LONG).show()
                Log.d("MyLog", "Error: $it")
            }
        )
        queue.add(stringRequest)

    }

    @SuppressLint("SetTextI18n")
    private fun parseData(result: String?) {
        val responseMainObject = result?.let { JSONObject(it) }
        val itemDayWeather = OneDayData(
            responseMainObject?.optJSONObject("location")?.getString("name"),
            responseMainObject?.optJSONObject("current")?.getDouble("temp_c"),
            responseMainObject?.optJSONObject("current")?.getJSONObject("condition")
                ?.getString("text"),
            responseMainObject?.optJSONObject("current")?.getInt("humidity")
        )
        Log.d("MyLog", "$itemDayWeather")
        binding.apply {
            finalCity.text = itemDayWeather.fullName_city
            temp.text = itemDayWeather.current_temp?.toInt().toString() + "\u00B0" + "C"
            condition.text = itemDayWeather.condition_text
            humidity.text = "Humidity " + itemDayWeather.current_humidity.toString() + "%"


            when(condition.text){
                "Sunny" -> dayWeatherLayout.setBackgroundResource(R.drawable.sunny)
                "Partly cloudy" -> dayWeatherLayout.setBackgroundResource(R.drawable.clouds)
                "Mist" -> dayWeatherLayout.setBackgroundResource(R.drawable.mist)
                "Fog" -> dayWeatherLayout.setBackgroundResource(R.drawable.fog)
                else -> dayWeatherLayout.setBackgroundResource(R.drawable.mainback)
            }
        }
    }
}