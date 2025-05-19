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
import androidx.compose.material3.TabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.foundation.clickable
import androidx.compose.runtime.saveable.rememberSaveable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import kotlinx.coroutines.delay
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import android.content.Context

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
    var history by rememberSaveable { mutableStateOf<List<List<com.example.afinal.PlaceResult>>>(emptyList()) }
    var selectedTab by rememberSaveable { mutableStateOf(0) }
    var selectedPlace by remember { mutableStateOf<com.example.afinal.PlaceResult?>(null) }
    // 加速度计相关
    val sensorManager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    var lastRefreshTime by remember { mutableStateOf(0L) }
    var lastAccel by remember { mutableStateOf(0f) }
    var lastAccelFiltered by remember { mutableStateOf(0f) }
    val REFRESH_INTERVAL = 10_000L // 10秒
    val ACCEL_THRESHOLD = 1.5f // 运动判定阈值
    val snackbarHostState = remember { SnackbarHostState() }
    var isLoading by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }

    // 加速度计监听
    DisposableEffect(Unit) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                val accel = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
                val delta = Math.abs(accel - lastAccel)
                lastAccel = accel
                lastAccelFiltered = lastAccelFiltered * 0.9f + delta * 0.1f
                val now = System.currentTimeMillis()
                if (lastAccelFiltered > ACCEL_THRESHOLD && now - lastRefreshTime > REFRESH_INTERVAL) {
                    lastRefreshTime = now
                    // 触发刷新
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
                                val retrofit = Retrofit.Builder()
                                    .baseUrl("https://maps.googleapis.com/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()
                                val placesApi = retrofit.create(com.example.afinal.PlacesApiService::class.java)
                                placesApi.getNearbyTransitStations(locStr, 1000, "transit_station", "AIzaSyAFNT_hba3ms-n8sjwv2cEThHhO-z6Z_Cs")
                                    .enqueue(object : Callback<com.example.afinal.PlacesResponse> {
                                        override fun onResponse(
                                            call: Call<com.example.afinal.PlacesResponse>,
                                            response: Response<com.example.afinal.PlacesResponse>
                                        ) {
                                            if (response.isSuccessful) {
                                                places = response.body()?.results ?: emptyList()
                                                if (places.isNotEmpty()) {
                                                    history = (listOf(places) + history).take(3)
                                                }
                                            }
                                        }
                                        override fun onFailure(call: Call<com.example.afinal.PlacesResponse>, t: Throwable) {}
                                    })
                            }
                        }
                    }
                }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

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

    // 封装刷新逻辑
    fun refreshNearbyStations() {
        if (hasLocationPermission) {
            isLoading = true
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
                    val retrofit = Retrofit.Builder()
                        .baseUrl("https://maps.googleapis.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val placesApi = retrofit.create(com.example.afinal.PlacesApiService::class.java)
                    placesApi.getNearbyTransitStations(locStr, 1000, "transit_station", "AIzaSyAFNT_hba3ms-n8sjwv2cEThHhO-z6Z_Cs")
                        .enqueue(object : Callback<com.example.afinal.PlacesResponse> {
                            override fun onResponse(
                                call: Call<com.example.afinal.PlacesResponse>,
                                response: Response<com.example.afinal.PlacesResponse>
                            ) {
                                isLoading = false
                                if (response.isSuccessful) {
                                    places = response.body()?.results ?: emptyList()
                                    if (places.isNotEmpty()) {
                                        history = (listOf(places) + history).take(3)
                                    }
                                } else {
                                    errorMsg = "获取公交信息失败"
                                    coroutineScope.launch { snackbarHostState.showSnackbar(errorMsg) }
                                }
                            }
                            override fun onFailure(call: Call<com.example.afinal.PlacesResponse>, t: Throwable) {
                                isLoading = false
                                errorMsg = "网络请求失败"
                                coroutineScope.launch { snackbarHostState.showSnackbar(errorMsg) }
                            }
                        })
                } ?: run {
                    isLoading = false
                    errorMsg = "无法获取当前位置"
                    coroutineScope.launch { snackbarHostState.showSnackbar(errorMsg) }
                }
            }.addOnFailureListener {
                isLoading = false
                errorMsg = "定位失败"
                coroutineScope.launch { snackbarHostState.showSnackbar(errorMsg) }
            }
        } else {
            errorMsg = "请先授予定位权限"
            coroutineScope.launch { snackbarHostState.showSnackbar(errorMsg) }
        }
    }

    // 首次进入自动刷新
    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            refreshNearbyStations()
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

    // Tab切换栏
    TabRow(selectedTabIndex = selectedTab) {
        Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("实时") })
        Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("历史") })
    }
    if (selectedTab == 0) {
        // 实时视图
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
                val dist = if (userLocation != null) calcDistance(
                    userLocation!!.latitude, userLocation!!.longitude,
                    place.geometry.location.lat, place.geometry.location.lng
                ) else null
                Marker(
                    state = com.google.maps.android.compose.MarkerState(
                        position = LatLng(
                            place.geometry.location.lat,
                            place.geometry.location.lng
                        )
                    ),
                    title = place.name + (if (dist != null) "  距离：${dist}米" else ""),
                    onClick = {
                        selectedPlace = place
                        false // 让InfoWindow弹出
                    }
                )
            }
        }
        // 不再显示下方列表
        // 点击Marker时可选弹窗（如需更丰富内容可加）
    } else {
        // 历史视图
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(history) { record ->
                val first = record.firstOrNull()
                Card(
                    modifier = Modifier.padding(vertical = 4.dp).clickable {
                        // 点击定位到该历史记录第一个站点
                        first?.let {
                            coroutineScope.launch {
                                cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(
                                    LatLng(it.geometry.location.lat, it.geometry.location.lng), 15f
                                ))
                            }
                        }
                    },
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Text(text = "历史记录：${first?.name ?: "无"}", modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
    // 手动刷新按钮
    Box(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { refreshNearbyStations() }, modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)) {
            Text("手动刷新")
        }
        // 加载中指示器
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        // 错误提示
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter))
    }
    // 权限处理优化
    if (!hasLocationPermission) {
        Box(modifier = Modifier.fillMaxSize()) {
            Button(onClick = {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    1001
                )
            }, modifier = Modifier.align(Alignment.Center)) {
                Text("请求定位权限")
            }
            Text(
                text = "未授予定位权限，部分功能不可用。请在设置中允许定位权限。",
                modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
            )
        }
        return
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