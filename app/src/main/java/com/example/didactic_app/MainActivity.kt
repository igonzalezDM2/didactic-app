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
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.didactic_app.enums.Lugar
import com.example.didactic_app.utilis.Utils
import kotlin.math.cos


/**
 * Clase MainActivity que representa la actividad principal de la aplicación.
 */
class MainActivity : Lanzador() {

    /**
     * Objeto compañero que contiene arreglos de sardinas y piezas.
     */
    companion object {
        /**
         * Arreglo de sardinas.
         */
        var arreglo_sardinas = arrayOf("", "", "", "", "", "")
        
        /**
         * Arreglo de piezas.
         */
        var arreglo_piezas = arrayOf("", "", "", "", "", "", "")
    }


    /**
     * Coordenadas del lugar Serantes.
     */
    private val COORDENADAS_SERANTES: DoubleArray = doubleArrayOf(43.3359377879001, -3.0619840315480364)
    /**
     * Coordenadas del lugar Sardinera.
     */
    private val COORDENADAS_SARDINERA: DoubleArray = doubleArrayOf(43.33437796738136, -3.039328003051773)
    /**
     * Coordenadas del lugar Sare Josle.
     */
    private val SARE_JOSLEEN_LEKUA: DoubleArray = doubleArrayOf(43.33005582802109, -3.0309998673703076)
    /**
     * Coordenadas del lugar Itsas Museoa.
     */
    private val ITSAS_MUSEOA: DoubleArray = doubleArrayOf(43.33077682113878, -3.030527877109482)
    /**
     * Coordenadas del lugar Santurtzi Parkea.
     */
    private val SANTURTZIKO_PARKEA: DoubleArray = doubleArrayOf(43.32879964909476, -3.031697605351168)
    /**
     * Coordenadas del lugar Arraun Udal Pabiloia.
     */
    private val ARRAUN_UDAL_PABILOIA: DoubleArray = doubleArrayOf(43.33072217111924, -3.03158330305195)
    /**
     * Coordenadas del lugar Udala.
     */
    private val UDALA: DoubleArray = doubleArrayOf(43.32883180778487, -3.033054932499828)

    /**
     * Código de solicitud de permiso de ubicación.
     */
    private val LOCATION_PERMISSION_REQUEST_CODE = 123;
    /**
     * Administrador de ubicación.
     */
    private lateinit var locationManager: LocationManager

    /**
     * Latitud actual.
     */
    private var latitud: Double = 0.0
    /**
     * Longitud actual.
     */
    private var longitud: Double = 0.0

    /**
     * Botón para acceder al mapa.
     */
    private lateinit var btMapa: Button
    /**
     * Botón para la prueba 1 - Barcos.
     */
    private lateinit var btPrueba1Barcos: Button
    /**
     * Botón para la prueba 2 - Pescar.
     */
    private lateinit var btPrueba2Pescar: Button
    /**
     * Botón para la prueba 3 - Sopa.
     */
    private lateinit var btPrueba3Sopa: Button
    /**
     * Botón para la prueba 5 - Cocinar.
     */
    private lateinit var btPrueba5Cocinar: Button
    /**
     * Botón para la prueba 6 - Canción.
     */
    private lateinit var btPrueba6Cancion: Button
    /**
     * Botón para la prueba 8 - Alimentar.
     */
    private lateinit var btPrueba8Alimentar: Button
    /**
     * Botón para salir.
     */
    private lateinit var btSalir: Button

    /**
     * Diseño lineal para el lugar Serantes.
     */
    private lateinit var llSerantes: LinearLayout
    /**
     * Diseño lineal para el lugar Sardinera.
     */
    private lateinit var llSardinera: LinearLayout
    /**
     * Diseño lineal para el lugar Sare Josle.
     */
    private lateinit var llSareJosle: LinearLayout
    /**
     * Diseño lineal para el lugar Itsas Museoa.
     */
    private lateinit var llMuseo: LinearLayout
    /**
     * Diseño lineal para el lugar Santurtzi Parkea.
     */
    private lateinit var llParque: LinearLayout
    /**
     * Diseño lineal para el lugar Udala.
     */
    private lateinit var llUdala: LinearLayout

    /**
     * Texto para mostrar la ubicación del lugar Serantes.
     */
    private lateinit var tvNoraSerantes: TextView
    /**
     * Texto para mostrar la ubicación del lugar Sardinera.
     */
    private lateinit var tvNoraSardinera: TextView
    /**
     * Texto para mostrar la ubicación del lugar Sare Josle.
     */
    private lateinit var tvNoraSarejosle: TextView
    /**
     * Texto para mostrar la ubicación del lugar Itsas Museoa.
     */
    private lateinit var tvNoraMuseo: TextView
    /**
     * Texto para mostrar la ubicación del lugar Santurtzi Parkea.
     */
    private lateinit var tvNoraParque: TextView
    /**
     * Texto para mostrar la ubicación del lugar Udala.
     */
    private lateinit var tvNoraUdala: TextView

    private lateinit var cvFinJuego: CardView

    private lateinit var btnDebug: ImageButton

    private var debug: Boolean = false

    private lateinit var mapPartida: Map<Lugar, Boolean>

    
    /**
     * Método llamado cuando la actividad es creada.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cargarMapa()
        initComponentes()
        initOyentes()
        checkSuperados()

        btnDebug.setOnClickListener{l ->
            debug = !debug
            if (debug) {
                findViewById<CardView>(R.id.cvNora).visibility = CardView.GONE
                cambioVisibilidadDemo()
            } else {
                findViewById<CardView>(R.id.cvNora).visibility = CardView.VISIBLE
                cambioVisibilidad()
            }
        }

//        if (!comprobarPermisos()) {
//            solicitarPermiso(android.Manifest.permission.ACCESS_FINE_LOCATION, "Localización", 0, this)
//            solicitarPermiso(android.Manifest.permission.ACCESS_COARSE_LOCATION, "Localización", 1, this)
//            solicitarPermiso(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION, "Localización en segundo plano", 2, this)
//        }



        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager;

        gestionLocalizacion()

        if (mapPartida.values.stream().allMatch{v -> v == false}) {
            Utils.anadirSuperado(this, Lugar.INICIO)
            lanzarJuego(arrayOf(
                resources.getText(R.string.explicacion_principio_1).toString(),
                resources.getText(R.string.explicacion_principio_2).toString(),
                resources.getText(R.string.explicacion_principio_3).toString(),
                resources.getText(R.string.explicacion_principio_4).toString(),
            ), intArrayOf(),
                R.raw.principio , Intent(this, MainActivity::class.java))
        }


    }

    /**
     * Carga el mapa de la partida.
     */
    private fun cargarMapa() {
        mapPartida = HashMap<Lugar, Boolean>(Lugar.values().size)

        Utils.operarBD(this) {db ->
            val campos = arrayOf("lugar", "superado")
            val cur = db!!.query("partida", campos, null, null, null, null, null)
            while (cur.moveToNext()) {
                val lugar = Lugar.valueOf(cur.getString(0))
                val superado = cur.getInt(1) > 0
                (mapPartida as HashMap<Lugar, Boolean>)?.put(lugar, superado)
            }
        }
    }

    /**
     * Comprueba si se tienen los permisos necesarios.
     * @return true si se tienen los permisos, false en caso contrario.
     */
    private fun comprobarPermisos(): Boolean {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }


    /**
     * Inicializa los componentes de la actividad principal.
     */
    private fun initComponentes() {
        btMapa = findViewById(R.id.bt_mapa)
        btPrueba1Barcos = findViewById(R.id.bt_prueba1_barcos)
        btPrueba2Pescar = findViewById(R.id.bt_prueba2_pescar)
        btPrueba3Sopa = findViewById(R.id.bt_prueba3_sopa)
//        btPrueba4Rederas = findViewById(R.id.bt_prueba4_rederas)
        btPrueba5Cocinar = findViewById(R.id.bt_prueba5_cocinar)
        btPrueba6Cancion = findViewById(R.id.bt_prueba6_cancion)
//        btPrueba7Trainera = findViewById(R.id.bt_prueba7_trainera)
        btPrueba8Alimentar = findViewById(R.id.bt_prueba8_alimentar)
//        btPrueba9Puerto = findViewById(R.id.bt_prueba9_puerto)
//        btPrueba10Tangram = findViewById(R.id.bt_prueba10_tangram)
        btSalir = findViewById(R.id.bt_salir)

        llSerantes = findViewById(R.id.llSerantes)
        llSardinera = findViewById(R.id.llSardinera)
        llSareJosle = findViewById(R.id.llSareJosleak)
        llMuseo = findViewById(R.id.llItsasMuseoa)
        llParque = findViewById(R.id.llParque)
//        llRemo = findViewById(R.id.llRemo)
        llUdala = findViewById(R.id.llUdala)

        tvNoraSerantes = findViewById(R.id.tvNoraSerantes)
        tvNoraSardinera = findViewById(R.id.tvNoraSardinera)
        tvNoraSarejosle = findViewById(R.id.tvNoraSarejosle)
        tvNoraMuseo = findViewById(R.id.tvNoraMuseo)
        tvNoraParque = findViewById(R.id.tvNoraParque)
        tvNoraUdala = findViewById(R.id.tvNoraUdala)
        cvFinJuego = findViewById(R.id.cvFinJuego)

        btnDebug = findViewById(R.id.bt_debug)

        ocultarLinears()
        ocultarNora()
    }

    /**
     * Inicializa los listeners de la actividad principal.
     */
    private fun initOyentes() {
        btMapa.setOnClickListener { goToActividades(0) }
        btPrueba1Barcos.setOnClickListener { goToActividades(1) }
        btPrueba2Pescar.setOnClickListener { goToActividades(2) }
        btPrueba3Sopa.setOnClickListener { goToActividades(3) }
//        btPrueba4Rederas.setOnClickListener { goToActividades(4) }
        btPrueba5Cocinar.setOnClickListener { goToActividades(5) }
        btPrueba6Cancion.setOnClickListener { goToActividades(6) }
//        btPrueba7Trainera.setOnClickListener { goToActividades(7) }
        btPrueba8Alimentar.setOnClickListener { goToActividades(8) }
//        btPrueba9Puerto.setOnClickListener { goToActividades(9) }
//        btPrueba10Tangram.setOnClickListener { goToActividades(10) }
        btSalir.setOnClickListener { goToActividades(11) }
    }

    /**
     * Navega a las actividades correspondientes según la opción seleccionada.
     *
     * @param opcion La opción seleccionada.
     */
    private fun goToActividades(opcion: Int) {

        if (opcion == 11) {
            finishAffinity();
        } else {

            when (opcion) {
                1 -> lanzarJuego(arrayOf(resources.getText(R.string.explicacion_serantes1).toString(),
                    resources.getText(R.string.explicacion_serantes2).toString(),
                    resources.getText(R.string.explicacion_serantes3).toString(),
                    resources.getText(R.string.explicacion_serantes4).toString()
                ), intArrayOf(R.drawable.foto_serantes),
                    R.raw.explicacion_serantes,
                    Intent(this, VerdaderoFalsoActivity::class.java))
                2 -> lanzarJuego(arrayOf(
                    resources.getText(R.string.explicacion_sardinera1).toString(),
                    resources.getText(R.string.explicacion_sardinera2).toString(),
                    resources.getText(R.string.explicacion_sardinera3).toString(),
                    resources.getText(R.string.explicacion_sardinera4).toString()
                    ),
                    intArrayOf(R.drawable.foto_sardinera1,
                        R.drawable.foto_sardinera2
                    ),
                    R.raw.explicacion_sardinera,
                    Intent(this, AtraparSardinasActivity::class.java))
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
                8 -> lanzarJuegoYT(
                    arrayOf(
                        resources.getText(R.string.explicacion_udala1).toString(),
                        resources.getText(R.string.explicacion_udala2).toString(),
                        resources.getText(R.string.explicacion_udala3).toString(),
                        resources.getText(R.string.explicacion_udala4).toString(),
                        resources.getText(R.string.explicacion_udala5).toString()
                    ),
                    "https://www.youtube.com/embed/9PLuQIg2n_Q?si=ftmUa28IQeh08DyA",
                    R.raw.audio_lanzar,
                    Intent(this, LanzamientoActivity::class.java))
                9 -> lanzarJuego(arrayOf(""), intArrayOf(0), 0, Intent(this, PuertoActivity::class.java))
                10 -> lanzarJuego(arrayOf(""), intArrayOf(0), 0, Intent(this, Puzzle4x2Activity::class.java))
                else -> lanzarJuego(Intent(this, MapActivity::class.java))
            }

        }

    }


    /**
     * Solicita permiso al usuario para acceder a recursos protegidos por el permiso especificado.
     *
     * @param permiso el permiso que se solicita
     * @param justificacion la justificación para la solicitud del permiso
     * @param requestCode el código de solicitud
     * @param actividad la actividad actual
     */
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

    /**
     * Comprueba si la ubicación se encuentra dentro de un radio específico.
     *
     * @param coordenadas las coordenadas de la ubicación
     * @param radio el radio de la ubicación
     * @return true si la ubicación se encuentra dentro del radio, de lo contrario false
     */
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

    /**
     * Objeto locationListener que escucha los cambios de ubicación.
     */
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            latitud = location.latitude
            longitud = location.longitude
            ocultarLinears()
            ocultarNora()
            if (debug) {
                cambioVisibilidadDemo()
            } else {
                cambioVisibilidad()
            }
            checkSuperados()

        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun cambioVisibilidadDemo() {
        cvFinJuego.visibility = CardView.GONE

        //            if (comprobarLocalizacion(COORDENADAS_SERANTES, 750)) {
        llSerantes.visibility = LinearLayout.VISIBLE
//            } else if (comprobarLocalizacion(COORDENADAS_SARDINERA, 75)) {
        llSardinera.visibility = LinearLayout.VISIBLE
//            } else if (comprobarLocalizacion(SARE_JOSLEEN_LEKUA, 15)) {
        llSareJosle.visibility = LinearLayout.VISIBLE
//            } else if (comprobarLocalizacion(ITSAS_MUSEOA, 15)) {
        llMuseo.visibility = LinearLayout.VISIBLE
//            } else if (comprobarLocalizacion(SANTURTZIKO_PARKEA, 50)) {
        llParque.visibility = LinearLayout.VISIBLE
        // } else if (comprobarLocalizacion(ARRAUN_UDAL_PABILOIA, 55)) {
//                llRemo.visibility = LinearLayout.VISIBLE
//            }*/ else if (ayuntamientoVisible() && comprobarLocalizacion(UDALA, 40)) {
        llUdala.visibility = LinearLayout.VISIBLE
//            }
    }

    private fun cambioVisibilidad() {
        if (!debug && juegoCompletado()) {
            cvFinJuego.visibility = CardView.VISIBLE
        }
        if (comprobarLocalizacion(COORDENADAS_SERANTES, 750)) {
            llSerantes.visibility = LinearLayout.VISIBLE
        } else if (comprobarLocalizacion(COORDENADAS_SARDINERA, 75)) {
            llSardinera.visibility = LinearLayout.VISIBLE
        } else if (comprobarLocalizacion(SARE_JOSLEEN_LEKUA, 15)) {
            llSareJosle.visibility = LinearLayout.VISIBLE
        } else if (comprobarLocalizacion(ITSAS_MUSEOA, 15)) {
            llMuseo.visibility = LinearLayout.VISIBLE
        } else if (comprobarLocalizacion(SANTURTZIKO_PARKEA, 50)) {
            llParque.visibility = LinearLayout.VISIBLE
        } /*else if (comprobarLocalizacion(ARRAUN_UDAL_PABILOIA, 55)) {
                llRemo.visibility = LinearLayout.VISIBLE
            }*/ else if (ayuntamientoVisible() && comprobarLocalizacion(UDALA, 40)) {
            llUdala.visibility = LinearLayout.VISIBLE
        }
    }

    /**
     * Oculta los diseños lineales.
     */
    private fun ocultarLinears() {
        llSerantes.visibility = LinearLayout.GONE
        llSardinera.visibility = LinearLayout.GONE
        llSareJosle.visibility = LinearLayout.GONE
        llMuseo.visibility = LinearLayout.GONE
        llParque.visibility = LinearLayout.GONE
//        llRemo.visibility = LinearLayout.GONE
        llUdala.visibility = LinearLayout.GONE
    }
    
    /**
     * Oculta la información de la ubicación.
     */
    private fun ocultarNora() {
        if (mapPartida[Lugar.SERANTES] == true) {
            tvNoraSerantes.visibility = LinearLayout.GONE
        } else {
            tvNoraSerantes.visibility = LinearLayout.VISIBLE
        }
        if (mapPartida[Lugar.SARDINERA] == true) {
            tvNoraSardinera.visibility = LinearLayout.GONE
        } else {
            tvNoraSardinera.visibility = LinearLayout.VISIBLE
        }
        if (mapPartida[Lugar.SARE_JOSLE] == true) {
            tvNoraSarejosle.visibility = LinearLayout.GONE
        } else {
            tvNoraSarejosle.visibility = LinearLayout.VISIBLE
        }
        if (mapPartida[Lugar.MUSEO] == true) {
            tvNoraMuseo.visibility = LinearLayout.GONE
        } else {
            tvNoraMuseo.visibility = LinearLayout.VISIBLE
        }
        if (mapPartida[Lugar.PARQUE] == true) {
            tvNoraParque.visibility = LinearLayout.GONE
        } else {
            tvNoraParque.visibility = LinearLayout.VISIBLE
        }
        if (mapPartida[Lugar.AYUNTAMIENTO] == true || !ayuntamientoVisible()) {
            tvNoraUdala.visibility = LinearLayout.GONE
        } else {
            tvNoraUdala.visibility = LinearLayout.VISIBLE
        }
    }

    /**
     * Comprueba si el juego está completado.
     * @return true si todos los lugares están completados, false de lo contrario
     */
    fun juegoCompletado(): Boolean {
        return mapPartida[Lugar.SERANTES] == true &&
                mapPartida[Lugar.SARDINERA] == true &&
                mapPartida[Lugar.SARE_JOSLE] == true &&
                mapPartida[Lugar.MUSEO] == true &&
                mapPartida[Lugar.PARQUE] == true &&
                mapPartida[Lugar.AYUNTAMIENTO] == true
    }

    /**
     * Verifica si el ayuntamiento es visible en el juego.
     * @return true si todos los lugares excepto el ayuntamiento están completados, false de lo contrario
     */
    fun ayuntamientoVisible(): Boolean {
        return mapPartida[Lugar.SERANTES] == true &&
                mapPartida[Lugar.SARDINERA] == true &&
                mapPartida[Lugar.SARE_JOSLE] == true &&
                mapPartida[Lugar.MUSEO] == true &&
                mapPartida[Lugar.PARQUE] == true
    }


    /**
     * Gestiona la localización del usuario.
     */
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

    /**
     * Comprueba si los lugares han sido superados y muestra la información correspondiente.
     */
    fun checkSuperados() {
        mapPartida.entries.forEach {e ->
            if (e.value) {
                when(e.key) {
                    Lugar.SERANTES -> {llSerantes.findViewWithTag<TextView>("superado").visibility = TextView.VISIBLE}
                    Lugar.SARDINERA -> {llSardinera.findViewWithTag<TextView>("superado").visibility = TextView.VISIBLE }
                    Lugar.SARE_JOSLE -> {llSareJosle.findViewWithTag<TextView>("superado").visibility = TextView.VISIBLE}
                    Lugar.MUSEO -> {llMuseo.findViewWithTag<TextView>("superado").visibility = TextView.VISIBLE}
                    Lugar.PARQUE -> {llParque.findViewWithTag<TextView>("superado").visibility = TextView.VISIBLE}
//                    Lugar.REMO -> {llRemo.findViewWithTag<TextView>("superado").visibility = TextView.VISIBLE}
                    Lugar.AYUNTAMIENTO -> {llUdala.findViewWithTag<TextView>("superado").visibility = TextView.VISIBLE}
                    else -> {/*NADA*/}
                }

            }
        }
        if (juegoCompletado()) {
            findViewById<CardView>(R.id.cvNora).visibility = CardView.GONE
            if (!debug) {
                cvFinJuego.visibility = CardView.VISIBLE
            } else {
                cvFinJuego.visibility = CardView.GONE
            }
        }
    }

}

