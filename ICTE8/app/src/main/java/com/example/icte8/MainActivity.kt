package com.example.icte8

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 初始化地图
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        setUpMap(googleMap)
    }

    private fun setUpMap(map: GoogleMap) {
        // 这里可以自定义地图设置和添加标记
        val seattle = LatLng(47.6062, -122.3321)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(seattle, 12f))

        // 添加"隐藏宝藏"标记
        val hiddenGems = listOf(
            LatLng(47.6101, -122.3421), // Sculpture Park
            LatLng(47.6289, -122.3426), // Gas Works Park
            LatLng(47.6205, -122.3493), // Space Needle 附近
            LatLng(47.5952, -122.3316)  // Pioneer Square 附近
        )
        hiddenGems.forEach { location ->
            map.addMarker(
                com.google.android.gms.maps.model.MarkerOptions()
                    .position(location)
                    .title("Hidden Gem")
            )
        }
    }
}