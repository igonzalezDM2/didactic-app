package com.example.didactic_app

import android.content.Intent
import android.graphics.Rect
import android.location.GpsStatus
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {


    private lateinit var btnMapa: Button
    private lateinit var btnSopa: Button
    private lateinit var btnCocinar: Button
    private lateinit var btnLanzar: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnMapa = findViewById(R.id.btnMapa);
        btnMapa.setOnClickListener { v: View ->
            var intent: Intent = Intent(this@MainActivity, MapActivity::class.java);
            activityResultLauncher.launch(intent)
        }

        btnSopa = findViewById(R.id.btnSopa);
        btnSopa.setOnClickListener { v: View ->
            var intent: Intent = Intent(this@MainActivity, SopaActivity::class.java);
            activityResultLauncher.launch(intent)
        }

        btnCocinar = findViewById(R.id.btnCocinar);
        btnCocinar.setOnClickListener { v: View ->
            var intent: Intent = Intent(this@MainActivity, CocinarActivity::class.java);
            activityResultLauncher.launch(intent)
        }

        btnLanzar = findViewById(R.id.btnLanzar);
        btnLanzar.setOnClickListener { v: View ->
            var intent: Intent = Intent(this@MainActivity, LanzamientoActivity::class.java);
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