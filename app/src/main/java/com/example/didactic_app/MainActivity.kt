package com.example.didactic_app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    companion object {
        var arreglo_sardinas = arrayOf("", "", "", "", "", "")
        var arreglo_piezas = arrayOf("", "", "", "", "", "", "")
    }

    private val FONDO_PARAM: String = "fondo";
    private val AUDIO_PARAM: String = "audio";
    private val TEXTO_PARAM: String = "texto";

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

    private var intencion: Intent? = null
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

            when (opcion) {
                1 -> lanzarJuego(arrayOf(""), intArrayOf(0), 0, Intent(this, VerdaderoFalsoActivity::class.java))
                2 -> lanzarJuego(arrayOf(""), intArrayOf(0), 0, Intent(this, AtraparSardinasActivity::class.java))
                3 -> lanzarJuego(arrayOf(""), intArrayOf(0), 0, Intent(this, SopaActivity::class.java))
                4 -> lanzarJuego(arrayOf(""), intArrayOf(0), 0, Intent(this, Puzzle3x2Activity::class.java))
                5 -> lanzarJuego(arrayOf(""), intArrayOf(0), 0, Intent(this, CocinarActivity::class.java))
                6 -> lanzarJuego(arrayOf("Canción xD", "JEJE"), intArrayOf(R.drawable.fondo_atrapar_sardinas, R.drawable.fumanchu), 0, Intent(this, CancionActivity::class.java))
                7 -> lanzarJuego(arrayOf(""), intArrayOf(0), 0, Intent(this, TraineraActivity::class.java))
                8 -> lanzarJuego(arrayOf(""), intArrayOf(0), 0, Intent(this, LanzamientoActivity::class.java))
                9 -> lanzarJuego(arrayOf(""), intArrayOf(0), 0, Intent(this, PuertoActivity::class.java))
                10 -> lanzarJuego(arrayOf(""), intArrayOf(0), 0, Intent(this, TangramActivity::class.java))
                else -> lanzarJuego(Intent(this, MapActivity::class.java))
            }

        }

    }

    private fun lanzarJuego(intencion: Intent) {
        lanzarJuego(null, null, null, intencion)
    }
    private fun lanzarJuego(explicacion: Array<String>?, fondo: IntArray?, audio: Int?, intencion: Intent) {
        this.intencion = intencion
        if (!explicacion.isNullOrEmpty()) {
            val intentExplicacion: Intent = Intent(this, ExplicacionActivity::class.java)
            if (audio != null) {
                intentExplicacion.putExtra(AUDIO_PARAM, audio)
            }
            if (fondo != null) {
                intentExplicacion.putExtra(FONDO_PARAM, fondo)
            }
            intentExplicacion.putExtra(TEXTO_PARAM, explicacion)
            startForResult.launch(intentExplicacion)
        } else {
            startActivity(intencion)
        }
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            startActivity(intencion)
        }
    }

}
