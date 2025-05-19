package com.example.afinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.afinal.ui.theme.FinalTheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material3.Button
import com.google.android.gms.maps.CameraUpdateFactory
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.MapProperties
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import com.google.maps.android.compose.Marker
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setContent {
            FinalTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MapScreen(
                        modifier = Modifier.padding(innerPadding),
                        fusedLocationClient = fusedLocationClient,
                        activity = this
                    )
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    fusedLocationClient: FusedLocationProviderClient,
    activity: ComponentActivity
) {
    val defaultLocation = LatLng(39.9087, 116.3975) // 北京天安门
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 12f)
    }
    val coroutineScope = rememberCoroutineScope()
    var hasLocationPermission by remember { mutableStateOf(false) }
    var places by remember { mutableStateOf<List<com.example.afinal.PlaceResult>>(emptyList()) }

    // Retrofit初始化
    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val placesApi = remember { retrofit.create(com.example.afinal.PlacesApiService::class.java) }

    // 检查并请求定位权限
    LaunchedEffect(Unit) {
        val fine = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarse = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
        hasLocationPermission = fine == PackageManager.PERMISSION_GRANTED || coarse == PackageManager.PERMISSION_GRANTED
        if (!hasLocationPermission) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                1001
            )
        }
    }

    // 获取用户当前位置
    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            fusedLocationClient.getCurrentLocation(
                com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            ).addOnSuccessListener { location ->
                location?.let {
                    val latLng = LatLng(it.latitude, it.longitude)
                    userLocation = latLng
                    coroutineScope.launch {
                        cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                    }
                    // 获取附近公交/地铁站点
                    val locStr = "${it.latitude},${it.longitude}"
                    placesApi.getNearbyTransitStations(locStr, 1000, "transit_station", "AIzaSyAFNT_hba3ms-n8sjwv2cEThHhO-z6Z_Cs")
                        .enqueue(object : Callback<com.example.afinal.PlacesResponse> {
                            override fun onResponse(
                                call: Call<com.example.afinal.PlacesResponse>,
                                response: Response<com.example.afinal.PlacesResponse>
                            ) {
                                if (response.isSuccessful) {
                                    places = response.body()?.results ?: emptyList()
                                }
                            }
                            override fun onFailure(call: Call<com.example.afinal.PlacesResponse>, t: Throwable) {
                                // 可选：处理错误
                            }
                        })
                }
            }
        }
    }

    // 计算距离
    fun calcDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Int {
        val r = 6371000.0 // 地球半径，米
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLng / 2) * Math.sin(dLng / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return (r * c).roundToInt()
    }

    // 地图
    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = hasLocationPermission,
            isTrafficEnabled = true
        )
    ) {
        // 标注公交/地铁站点
        places.forEach { place ->
            Marker(
                state = com.google.maps.android.compose.MarkerState(
                    position = LatLng(
                        place.geometry.location.lat,
                        place.geometry.location.lng
                    )
                ),
                title = place.name
            )
        }
    }
    // 下方列表
    if (places.isNotEmpty() && userLocation != null) {
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(places) { place ->
                val dist = calcDistance(
                    userLocation!!.latitude, userLocation!!.longitude,
                    place.geometry.location.lat, place.geometry.location.lng
                )
                Card(
                    modifier = Modifier.padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Text(text = "${place.name}  距离：${dist}米", modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
    // 可选：无权限时提示
    if (!hasLocationPermission) {
        Button(onClick = {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                1001
            )
        }, modifier = Modifier.padding(16.dp)) {
            Text("请求定位权限")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinalTheme {
        Greeting("Android")
    }
}