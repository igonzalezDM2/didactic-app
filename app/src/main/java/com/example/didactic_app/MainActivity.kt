package com.example.didactic_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    companion object {
        var arreglo_sardinas = arrayOf("", "", "", "", "", "")
        var arreglo_piezas = arrayOf("", "", "", "", "", "", "")
    }

    private lateinit var btMapa: Button
    private lateinit var btPrueba1Barcos: Button
    private lateinit var btPrueba2Pescar: Button
    private lateinit var btPrueba3Sopa: Button
    private lateinit var btPrueba4Rederas: Button
    private lateinit var btPrueba5Cocinar: Button
    private lateinit var btPrueba6Cancion: Button
    private lateinit var btPrueba7Trainera: Button
    private lateinit var btPrueba8Alimentar: Button
    private lateinit var btPrueba9Puerto: Button
    private lateinit var btPrueba10Tangram: Button
    private lateinit var btSalir: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponentes()
        initOyentes()
    }

    private fun initComponentes() {
        btMapa = findViewById(R.id.bt_mapa)
        btPrueba1Barcos = findViewById(R.id.bt_prueba1_barcos)
        btPrueba2Pescar = findViewById(R.id.bt_prueba2_pescar)
        btPrueba3Sopa = findViewById(R.id.bt_prueba3_sopa)
        btPrueba4Rederas = findViewById(R.id.bt_prueba4_rederas)
        btPrueba5Cocinar = findViewById(R.id.bt_prueba5_cocinar)
        btPrueba6Cancion = findViewById(R.id.bt_prueba6_cancion)
        btPrueba7Trainera = findViewById(R.id.bt_prueba7_trainera)
        btPrueba8Alimentar = findViewById(R.id.bt_prueba8_alimentar)
        btPrueba9Puerto = findViewById(R.id.bt_prueba9_puerto)
        btPrueba10Tangram = findViewById(R.id.bt_prueba10_tangram)
        btSalir = findViewById(R.id.bt_salir)
    }

    private fun initOyentes() {
        btMapa.setOnClickListener { goToActividades(0) }
        btPrueba1Barcos.setOnClickListener { goToActividades(1) }
        btPrueba2Pescar.setOnClickListener { goToActividades(2) }
        btPrueba3Sopa.setOnClickListener { goToActividades(3) }
        btPrueba4Rederas.setOnClickListener { goToActividades(4) }
        btPrueba5Cocinar.setOnClickListener { goToActividades(5) }
        btPrueba6Cancion.setOnClickListener { goToActividades(6) }
        btPrueba7Trainera.setOnClickListener { goToActividades(7) }
        btPrueba8Alimentar.setOnClickListener { goToActividades(8) }
        btPrueba9Puerto.setOnClickListener { goToActividades(9) }
        btPrueba10Tangram.setOnClickListener { goToActividades(10) }
        btSalir.setOnClickListener { goToActividades(11) }
    }

    private fun goToActividades(opcion: Int) {

        if (opcion == 11) {
            finish()
        } else {

            val intento = when (opcion) {
                1 -> Intent(this, VerdaderoFalsoActivity::class.java)
                2 -> Intent(this, AtraparSardinasActivity::class.java)
                3 -> Intent(this, SopaActivity::class.java)
                4 -> Intent(this, Puzzle3x2Activity::class.java)
                5 -> Intent(this, CocinarActivity::class.java)
                6 -> Intent(this, CancionActivity::class.java)
                7 -> Intent(this, TraineraActivity::class.java)
                8 -> Intent(this, LanzamientoActivity::class.java)
                9 -> Intent(this, PuertoActivity::class.java)
                10 -> Intent(this, TangramActivity::class.java)
                else -> Intent(this, MapActivity::class.java)
            }

            startActivity(intento)
        }

    }

}
