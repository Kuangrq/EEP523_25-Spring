package com.example.weatherapp

import android.annotation.SuppressLint
import android.content.Context
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


class MainActivity : AppCompatActivity() {

    // API Key（请替换为你自己的 OpenWeatherMap API Key）
    private val API_KEY = "876a5f2eea68a896a599b01d69d1d59d"
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/weather?units=metric&lang=zh_cn&appid=$API_KEY&q="

    // 控件声明
    private lateinit var etCityName: EditText
    private lateinit var btnSearch: Button
    private lateinit var tvError: TextView
    private lateinit var weatherInfoLayout: View
    private lateinit var tvCity: TextView
    private lateinit var tvTemp: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvTempRange: TextView
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
        tvTempRange = findViewById(R.id.tvTempRange)
        tvSunrise = findViewById(R.id.tvSunrise)
        tvSunset = findViewById(R.id.tvSunset)
        tvWind = findViewById(R.id.tvWind)
        tvPressure = findViewById(R.id.tvPressure)
        tvHumidity = findViewById(R.id.tvHumidity)

        // 初始隐藏错误信息
        tvError.visibility = View.GONE
        // 初始隐藏天气信息
        weatherInfoLayout.visibility = View.GONE

        // 查询按钮监听
        btnSearch.setOnClickListener {
            val city = etCityName.text.toString().trim()
            dismissKeyboard()
            if (city.isEmpty()) {
                showError("城市名不能为空")
                return@setOnClickListener
            }
            // 隐藏错误信息，显示加载中（可选）
            tvError.visibility = View.GONE
            weatherInfoLayout.visibility = View.GONE
            // 发起天气查询
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
                        showError("城市未找到或网络错误 (code: $responseCode)")
                    }
                }
                connection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    showError("请求失败：" + (e.message ?: "未知错误"))
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
            showError("未能获取天气信息")
            return
        }
        tvError.visibility = View.GONE
        weatherInfoLayout.visibility = View.VISIBLE
        tvCity.text = "城市：${data.name ?: "-"}"
        tvTemp.text = "温度：${data.main?.temp?.toInt() ?: "-"} ℃"
        val condition = data.weather?.getOrNull(0)?.description ?: "-"
        tvCondition.text = "天气状况：$condition"
        tvTempRange.text = "最高/最低温：${data.main?.tempMax?.toInt() ?: "-"} / ${data.main?.tempMin?.toInt() ?: "-"} ℃"
        tvSunrise.text = "日出时间：${formatTime(data.sys?.sunrise)}"
        tvSunset.text = "日落时间：${formatTime(data.sys?.sunset)}"
        tvWind.text = "风速：${data.wind?.speed ?: "-"} m/s"
        tvPressure.text = "气压：${data.main?.pressure ?: "-"} hPa"
        tvHumidity.text = "湿度：${data.main?.humidity ?: "-"} %"
    }

    // 时间戳转本地时间字符串
    private fun formatTime(timestamp: Long?): String {
        if (timestamp == null || timestamp == 0L) return "-"
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp * 1000))
    }

    // TODO
    // Write a function to update Error Screen
    // Make sure you update UI on main Thread
    // Hint:  runOnUiThread {
    //            kotlin.run {
    //              Write update code
    //          }
    //        }


    // TODO
    // Write a function to update Error Screen
    // Make sure you update UI on main Thread
    // Hint:  runOnUiThread {
    //            kotlin.run {
    //              Write update code
    //          }
    //        }

}
