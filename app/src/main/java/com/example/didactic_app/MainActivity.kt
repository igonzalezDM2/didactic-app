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
    private lateinit var btnVerdaderoFalso: Button
    private lateinit var btnAtrapar: Button
    private lateinit var btnPuzzle: Button
    private lateinit var btnCancion: Button
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

        btnVerdaderoFalso = findViewById(R.id.btnVerdaderoFalso);
        btnVerdaderoFalso.setOnClickListener { v: View ->
            var intent: Intent = Intent(this@MainActivity, VerdaderoFalsoActivity::class.java);
            activityResultLauncher.launch(intent)
        }

        btnAtrapar = findViewById(R.id.btnAtrapar);
        btnAtrapar.setOnClickListener { v: View ->
            var intent: Intent = Intent(this@MainActivity, AtraparSardinasActivity::class.java);
            activityResultLauncher.launch(intent)
        }

        btnPuzzle = findViewById(R.id.btnPuzzle);
        btnPuzzle.setOnClickListener {
            var intent: Intent = Intent(this@MainActivity, Puzzle3x2Activity::class.java);
            activityResultLauncher.launch(intent)
        }

        btnCancion = findViewById(R.id.btnCancion);
        btnCancion.setOnClickListener { v: View ->
            var intent: Intent = Intent(this@MainActivity, CancionActivity::class.java);
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