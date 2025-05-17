package com.example.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.model.Weather.CurrentWeatherResponse
import com.google.gson.Gson
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    // API Key (Please replace with your own OpenWeatherMap API Key)
    private val API_KEY = "876a5f2eea68a896a599b01d69d1d59d"
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/weather?units=metric&lang=en&appid=$API_KEY&q="
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    // Widget declarations
    private lateinit var etCityName: EditText
    private lateinit var btnSearch: Button
    private lateinit var tvError: TextView
    private lateinit var weatherInfoLayout: View
    private lateinit var tvCity: TextView
    private lateinit var tvTemp: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvTempMin: TextView
    private lateinit var tvTempMax: TextView
    private lateinit var tvSunrise: TextView
    private lateinit var tvSunset: TextView
    private lateinit var tvWind: TextView
    private lateinit var tvPressure: TextView
    private lateinit var tvHumidity: TextView

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                            this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                            Note: Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 初始化控件
        etCityName = findViewById(R.id.etCityName)
        btnSearch = findViewById(R.id.btnSearch)
        tvError = findViewById(R.id.tvError)
        weatherInfoLayout = findViewById(R.id.weatherInfoLayout)
        tvCity = findViewById(R.id.tvCity)
        tvTemp = findViewById(R.id.tvTemp)
        tvCondition = findViewById(R.id.tvCondition)
        tvTempMin = findViewById(R.id.tvTempMin)
        tvTempMax = findViewById(R.id.tvTempMax)
        tvSunrise = findViewById(R.id.tvSunrise)
        tvSunset = findViewById(R.id.tvSunset)
        tvWind = findViewById(R.id.tvWind)
        tvPressure = findViewById(R.id.tvPressure)
        tvHumidity = findViewById(R.id.tvHumidity)

        // 初始隐藏错误信息
        tvError.visibility = View.GONE
        // 初始隐藏天气信息
        weatherInfoLayout.visibility = View.GONE

        // 启动时检测网络
        if (!checkNetworkAvailable()) {
            showError("Please connect to internet")
        }

        // 启动时尝试获取当前位置天气
        if (checkNetworkAvailable()) {
            getCurrentLocationWeather()
        }

        // 查询按钮监听
        btnSearch.setOnClickListener {
            if (!checkNetworkAvailable()) {
                showError("Please connect to internet")
                return@setOnClickListener
            }
            val city = etCityName.text.toString().trim()
            dismissKeyboard()
            if (city.isEmpty()) {
                showError("City name cannot be empty")
                return@setOnClickListener
            }
            tvError.visibility = View.GONE
            weatherInfoLayout.visibility = View.GONE
            val url = BASE_URL + city
            fetchWeatherData(url).start()
        }
    }


    private fun dismissKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        // Check if no view has focus:
        val currentFocusedView = currentFocus
        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(
                it.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    // TODO
    // Write a function to fetch weather Data
    // Make sure you use background Thread

    /**
     * Fetches weather data from a specified URL and processes the response.
     *
     * @param urlString The URL string from which the weather data is to be fetched.
     * @return A thread that, when started, performs the network request and data processing.
     */
    fun fetchWeatherData(urlString: String): Thread {
        return Thread {
            try {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 5000
                connection.readTimeout = 5000
                connection.requestMethod = "GET"
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.readText()
                    reader.close()
                    val gson = Gson()
                    val weatherResponse = gson.fromJson(response, CurrentWeatherResponse::class.java)
                    runOnUiThread {
                        showWeatherInfo(weatherResponse)
                    }
                } else {
                    runOnUiThread {
                        showError("City not found! (code: $responseCode)")
                    }
                }
                connection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    showError("Request failed: " + (e.message ?: "Unknown error"))
                }
            }
        }
    }

    // 显示错误信息
    private fun showError(msg: String) {
        tvError.text = msg
        tvError.visibility = View.VISIBLE
        weatherInfoLayout.visibility = View.GONE
    }

    // 显示天气信息
    private fun showWeatherInfo(data: CurrentWeatherResponse?) {
        if (data == null || data.cod != 200) {
            showError("Failed to get weather information")
            return
        }
        tvError.visibility = View.GONE
        weatherInfoLayout.visibility = View.VISIBLE
        tvCity.text = "${data.name ?: "-"}"
        tvTemp.text = "${data.main?.temp?.toInt() ?: "-"} ℃"
        val condition = data.weather?.getOrNull(0)?.description ?: "-"
        tvCondition.text = condition
        tvTempMin.text = "Min: ${data.main?.tempMin?.toInt() ?: "-"} ℃"
        tvTempMax.text = "Max: ${data.main?.tempMax?.toInt() ?: "-"} ℃"
        tvSunrise.text = formatTime(data.sys?.sunrise)
        tvSunset.text = formatTime(data.sys?.sunset)
        tvWind.text = "${data.wind?.speed ?: "-"} m/s"
        tvPressure.text = "${data.main?.pressure ?: "-"} hPa"
        tvHumidity.text = "${data.main?.humidity ?: "-"} %"
    }

    // 时间戳转本地时间字符串
    private fun formatTime(timestamp: Long?): String {
        if (timestamp == null || timestamp == 0L) return "-"
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp * 1000))
    }


    // 检查网络是否可用
    private fun checkNetworkAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    // 获取当前位置并查询天气
    private fun getCurrentLocationWeather() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location: Location? = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            ?: locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            val lat = location.latitude
            val lon = location.longitude
            val url = "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&units=metric&lang=en&appid=$API_KEY"
            fetchWeatherData(url).start()
        } else {
            showError("Unable to get current location, please enter city name manually")
        }
    }

    // 处理权限申请结果
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocationWeather()
            } else {
                showError("Location permission denied, cannot get current location weather")
            }
        }
    }

}
