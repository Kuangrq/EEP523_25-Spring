package com.example.smartnewsfetcher

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var switchPreference: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO: Initialize shared preferences
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        // TODO: Find views by ID
        switchPreference = findViewById(R.id.switch_network_pref)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // TODO: Initialize adapter with empty list
        adapter = NewsAdapter(emptyList())
        recyclerView.adapter = adapter

        // TODO: Load user preference and update switch state
        val allowCellular = sharedPreferences.getBoolean("allow_cellular", false)
        switchPreference.isChecked = allowCellular

        // TODO: Set listener to save user preference when switch changes
        switchPreference.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("allow_cellular", isChecked).apply()
        }

        findViewById<Button>(R.id.btn_refresh).setOnClickListener {
            if (isNetworkAllowed()) {
                fetchNews()
            } else {
                Toast.makeText(this, "Data fetch restricted by user settings", Toast.LENGTH_SHORT).show()
            }
        }

        // Initial fetch if network allowed
        if (isNetworkAllowed()) {
            fetchNews()
        }
    }

    private fun isNetworkAllowed(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork
        if (network == null) return false

        val capabilities = cm.getNetworkCapabilities(network)
        if (capabilities == null) return false

        val allowCellular = sharedPreferences.getBoolean("allow_cellular", false)

        // TODO: Check if connection type is allowed based on user preference
        val hasWifi = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        val hasCellular = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)

        return hasWifi || (hasCellular && allowCellular)
    }

    private fun fetchNews() {
        // TODO: Fetch news data from API (hint: use background thread or coroutine)
        Toast.makeText(this, "Fetching news...", Toast.LENGTH_SHORT).show()

        thread {
            try {
                // Mock API data for the assignment
                val headlines = listOf(
                    "Google Announces New AI Features for Android",
                    "Apple's Latest MacBook Pro Sets New Performance Records",
                    "Microsoft Introduces Revolutionary Cloud Computing Service",
                    "Samsung Unveils Next-Generation Foldable Phones",
                    "Tech Companies Adapt to New Privacy Regulations",
                    "Breakthrough in Quantum Computing Announced",
                    "New Cybersecurity Threats Emerge in Mobile Space",
                    "Tesla Reveals Plans for Autonomous Vehicle Network",
                    "SpaceX Successfully Launches Internet Satellites",
                    "Amazon Expands AI-Powered Shopping Experience"
                )

                // TODO: Parse JSON and update adapter with list of headlines
                runOnUiThread {
                    adapter.updateData(headlines)
                    Toast.makeText(this, "News updated!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}