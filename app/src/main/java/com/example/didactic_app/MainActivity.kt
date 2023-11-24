package com.example.didactic_app

import android.content.Intent
import android.graphics.Rect
import android.location.GpsStatus
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.didactic_app.databinding.ActivityMainBinding
import com.example.didactic_app.databinding.ActivityMapBinding
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MainActivity : AppCompatActivity() {


    private lateinit var btnMapa: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnMapa = findViewById(R.id.btnMapa);
        btnMapa.setOnClickListener { v: View ->
            var intent: Intent = Intent(this@MainActivity, MapActivity::class.java);
            activityResultLauncher.launch(intent)
        }

    }

    private val activityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // NO SE HACE NADA
            // if (Activity.RESULT_OK == result.resultCode) {
            //     val intent = result.data
            // }
        }

}