package com.example.alarmmanager

 import android.app.AlarmManager
 import android.app.Notification
 import android.app.PendingIntent
 import android.app.TimePickerDialog
 import android.content.BroadcastReceiver
 import android.content.Context
 import android.content.Intent
 import android.content.IntentFilter
 import android.net.wifi.WifiManager
 import android.os.Build
 import android.os.Bundle
 import android.os.SystemClock
 import android.util.Log
 import android.widget.*
 import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
 import androidx.core.content.ContextCompat.getSystemService
 import kotlinx.android.synthetic.main.activity_main.*
 import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var wifiSwitch: Switch
    lateinit var wifiManager: WifiManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        title = "KotlinApp"
        wifiSwitch = findViewById(R.id.wifiSwitch)
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiSwitch.setOnCheckedChangeListener { _, isCheckedo ->
            if (isCheckedo) {
                wifiManager.isWifiEnabled = true
                wifiSwitch.text = "WiFi Encendido"
            } else {
                wifiManager.isWifiEnabled = false
                wifiSwitch.text = "WiFi Apagado"
            }
        }
    }
    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(wifiStateReceiver, intentFilter)
    }
    override fun onStop() {
        super.onStop()
        unregisterReceiver(wifiStateReceiver)
    }
    private val wifiStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_UNKNOWN)) {
                WifiManager.WIFI_STATE_ENABLED -> {
                    wifiSwitch.isChecked = true
                    wifiSwitch.text = "WiFi Encendido"
                    Toast.makeText(this@MainActivity, "Wifi is On", Toast.LENGTH_SHORT).show()
                }
                WifiManager.WIFI_STATE_DISABLED -> {
                    wifiSwitch.isChecked = false
                    wifiSwitch.text = "WiFi Apagado"
                    Toast.makeText(this@MainActivity, "Wifi is Off", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}