package com.example.bhawook54545434.weatherforecast

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")

        window.statusBarColor = Color.parseColor("#D7DBDD")

        getJsonData(lat, long)

    }

    private fun getJsonData(lat: String?, long: String?) {
        // Instantiate the RequestQueue.
        val API_key = ""   // put you API Key here
        val queue = Volley.newRequestQueue(this)
        val url =
            "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${API_key}"

        // Request a string response from the provided URL.
        // val can be change to string or any other variable
        val JsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                setValues(response)
            },
            Response.ErrorListener {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            })

        // Add the request to the RequestQueue.
        queue.add(JsonRequest)
    }

    private fun setValues(response: JSONObject) {
        city_name.text = response.getString("name")
        var lat = response.getJSONObject("coord").getString("lat")
        var long = response.getJSONObject("coord").getString("lon")
        coordinates.text = "${lat},${long}"
        weather.text = response.getJSONArray("weather").getJSONObject(0).getString("main")
        var tempr = response.getJSONObject("main").getString("temp")
        tempr = ((((tempr).toFloat() - 273.15)).toInt()).toString()
        temp.text = "${tempr}°C"
        var mintemp = response.getJSONObject("main").getString("temp_min")
        mintemp = ((((mintemp).toFloat() - 273.15)).toInt()).toString()
        min_temp.text = mintemp + "°C"
        var maxtemp = response.getJSONObject("main").getString("temp_min")
        maxtemp = ((ceil((maxtemp).toFloat() - 273.15)).toInt()).toString()
        max_temp.text = maxtemp + "°C"
        pressure.text = response.getJSONObject("main").getString("pressure")
        humidity.text = response.getJSONObject("main").getString("humidity") + "%"
        wind.text = response.getJSONObject("wind").getString("speed")
        degree.text = "Degree : " + response.getJSONObject("wind").getString("deg") + "°"
        /*  gust.text = "Gust : " + response.getJSONObject("wind").getString("gust") + "°"*/

    }

}
