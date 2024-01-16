package com.example.didactic_app

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlin.math.cos


class MainActivity : AppCompatActivity() {

    companion object {
        var arreglo_sardinas = arrayOf("", "", "", "", "", "")
        var arreglo_piezas = arrayOf("", "", "", "", "", "", "")
    }


    private val COORDENADAS_SERANTES: DoubleArray = doubleArrayOf(43.3359377879001, -3.0619840315480364)
    private val COORDENADAS_SARDINERA: DoubleArray = doubleArrayOf(43.33437796738136, -3.039328003051773)
    private val SARE_JOSLEEN_LEKUA: DoubleArray = doubleArrayOf(43.33005582802109, -3.0309998673703076)
    private val ITSAS_MUSEOA: DoubleArray = doubleArrayOf(43.33077682113878, -3.030527877109482)
    private val SANTURTZIKO_PARKEA: DoubleArray = doubleArrayOf(43.32879964909476, -3.031697605351168)
    private val ARRAUN_UDAL_PABILOIA: DoubleArray = doubleArrayOf(43.33072217111924, -3.03158330305195)
    private val UDALA: DoubleArray = doubleArrayOf(43.32883180778487, -3.033054932499828)

    private val LOCATION_PERMISSION_REQUEST_CODE = 123;
    private lateinit var locationManager: LocationManager

    private var latitud: Double = 0.0
    private var longitud: Double = 0.0

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

    private lateinit var llSerantes: LinearLayout
    private lateinit var llSardinera: LinearLayout
    private lateinit var llSareJosle: LinearLayout
    private lateinit var llMuseo: LinearLayout
    private lateinit var llParque: LinearLayout
    private lateinit var llRemo: LinearLayout
    private lateinit var llUdala: LinearLayout

    private var intencion: Intent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponentes()
        initOyentes()

//        if (!comprobarPermisos()) {
//            solicitarPermiso(android.Manifest.permission.ACCESS_FINE_LOCATION, "Localización", 0, this)
//            solicitarPermiso(android.Manifest.permission.ACCESS_COARSE_LOCATION, "Localización", 1, this)
//            solicitarPermiso(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION, "Localización en segundo plano", 2, this)
//        }



        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager;

        gestionLocalizacion()


    }

    private fun comprobarPermisos(): Boolean {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
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

        llSerantes = findViewById(R.id.llSerantes)
        llSardinera = findViewById(R.id.llSardinera)
        llSareJosle = findViewById(R.id.llSareJosleak)
        llMuseo = findViewById(R.id.llItsasMuseoa)
        llParque = findViewById(R.id.llParque)
        llRemo = findViewById(R.id.llRemo)
        llUdala = findViewById(R.id.llUdala)

        ocultarLinears()
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
                3 -> lanzarJuego(arrayOf(
                    resources.getText(R.string.explicacion_sopa1).toString(),
                    resources.getText(R.string.explicacion_sopa2).toString(),
                    resources.getText(R.string.explicacion_sopa3).toString(),
                    resources.getText(R.string.explicacion_sopa4).toString(),
                ), intArrayOf(R.drawable.foto_sopa1, R.drawable.foto_sopa2),
                    R.raw.audio_sopa , Intent(this, SopaActivity::class.java))
                4 -> lanzarJuego(arrayOf(""), intArrayOf(0), 0, Intent(this, Puzzle3x2Activity::class.java))
                5 -> lanzarJuego(arrayOf(
                    resources.getText(R.string.explicacion_sardinas1).toString(),
                    resources.getText(R.string.explicacion_sardinas2).toString(),
                    resources.getText(R.string.explicacion_sardinas3).toString(),
                    resources.getText(R.string.explicacion_sardinas4).toString(),
                ), intArrayOf(
                    R.drawable.foto_cocinar1,
                    R.drawable.foto_cocinar2
                ), R.raw.audio_cocinar, Intent(this, CocinarActivity::class.java))
                6 -> lanzarJuego(arrayOf(resources.getText(R.string.explicacion_cancion1).toString(),
                    resources.getText(R.string.explicacion_cancion2).toString(),
                    resources.getText(R.string.explicacion_cancion3).toString()),
                    intArrayOf(R.drawable.foto_cancion1, R.drawable.foto_cancion2, R.drawable.foto_cancion3),
                    R.raw.audio_cancion, Intent(this, CancionActivity::class.java))
                7 -> lanzarJuego(arrayOf(""), intArrayOf(0), 0, Intent(this, TraineraActivity::class.java))
                8 -> lanzarJuego(arrayOf(resources.getText(R.string.explicacion_lanzar).toString()), intArrayOf(0), R.raw.audio_lanzar, Intent(this, LanzamientoActivity::class.java))
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

    private fun solicitarPermiso(
        permiso: String,
        justificacion: String,
        requestCode: Int,
        actividad: Activity
    ) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                actividad, permiso
            )
        ) {
//Informamos al usuario para qué y por qué
//se necesitan los permisos
            AlertDialog.Builder(actividad)
                .setTitle("Solicitud de permiso")
                .setMessage(justificacion)
                .setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, which ->
                        ActivityCompat.requestPermissions(
                            actividad,
                            arrayOf(permiso),
                            requestCode
                        )
                    }).show()
        } else {
//Muestra el cuadro de dialogo para la solicitud de permisos y
//registra el permiso según respuesta del usuario
            ActivityCompat.requestPermissions(actividad, arrayOf(permiso), requestCode)
        }
    }

    private fun comprobarLocalizacion(coordenadas: DoubleArray, radio: Int): Boolean {
        //Earth’s radius, sphere
        val RADIO_TIERRA =6378137.toDouble()



        //Coordinate offsets in radians
        val dLat = radio/RADIO_TIERRA
        val dLon = radio/(RADIO_TIERRA* cos(Math.PI*coordenadas[0]/180))

        //OffsetPosition, decimal degrees
        val latO = coordenadas[0] - dLat * 180/Math.PI
        val lat1 = coordenadas[0] + dLat * 180/Math.PI
        val lonO = coordenadas[1] - dLon * 180/Math.PI
        val lon1 = coordenadas[1] + dLon * 180/Math.PI


        return ((latitud in latO..lat1) &&
                (longitud in lonO..lon1))
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            latitud = location.latitude
            longitud = location.longitude
            ocultarLinears()
            if (comprobarLocalizacion(COORDENADAS_SERANTES, 200)) {
                llSerantes.visibility = LinearLayout.VISIBLE
            } else if (comprobarLocalizacion(COORDENADAS_SARDINERA, 75)) {
                llSardinera.visibility = LinearLayout.VISIBLE
            } else if (comprobarLocalizacion(SARE_JOSLEEN_LEKUA, 75)) {
                llSareJosle.visibility = LinearLayout.VISIBLE
            } else if (comprobarLocalizacion(ITSAS_MUSEOA, 75)) {
                llMuseo.visibility = LinearLayout.VISIBLE
            } else if (comprobarLocalizacion(SANTURTZIKO_PARKEA, 75)) {
                llParque.visibility = LinearLayout.VISIBLE
            } else if (comprobarLocalizacion(ARRAUN_UDAL_PABILOIA, 75)) {
                llRemo.visibility = LinearLayout.VISIBLE
            } else if (comprobarLocalizacion(UDALA, 75)) {
                llUdala.visibility = LinearLayout.VISIBLE
            }

        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun ocultarLinears() {
        llSerantes.visibility = LinearLayout.INVISIBLE
        llSardinera.visibility = LinearLayout.INVISIBLE
        llSareJosle.visibility = LinearLayout.INVISIBLE
        llMuseo.visibility = LinearLayout.INVISIBLE
        llParque.visibility = LinearLayout.INVISIBLE
        llRemo.visibility = LinearLayout.INVISIBLE
        llUdala.visibility = LinearLayout.INVISIBLE
    }


    fun gestionLocalizacion() {
        // Verifica si tienes permisos de ubicación
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Si no tienes permisos, solicítalos
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Si ya tienes permisos, ejecuta la lógica para obtener la ubicación
            locationManager.requestLocationUpdates("gps", 5000, 0f, locationListener)
        }

    }
    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, ejecuta la lógica para obtener la ubicación
                locationManager.requestLocationUpdates("gps", 5000, 0f, locationListener)
            } else {
                // Permiso denegado, maneja la situación en consecuencia
                // Por ejemplo, muestra un mensaje al usuario
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
