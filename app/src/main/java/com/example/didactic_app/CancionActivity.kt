package com.example.didactic_app

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.example.didactic_app.dialogs.DialogoFinJuego
import com.example.didactic_app.dialogs.OnDialogoConfirmacionListener
import com.example.didactic_app.enums.Lugar
import com.example.didactic_app.model.RespuestasCancion
import com.example.didactic_app.utilis.Utils

/**
 * Clase CancionActivity
 * Esta clase representa la actividad que gestiona el juego de adivinar canciones.
 */
class CancionActivity : Lanzador(), OnDialogoConfirmacionListener {

    /**
     * Array de respuestas de canciones.
     */
    private val RESPUESTAS: Array<RespuestasCancion> = arrayOf(
        RespuestasCancion(R.string.bilbainada1, R.string.bilbainada_resp1_1, R.string.bilbainada_resp1_2, R.string.bilbainada_resp1_3, R.string.bilbainada_resp1_2),
        RespuestasCancion(R.string.bilbainada2, R.string.bilbainada_resp2_1, R.string.bilbainada_resp2_2, R.string.bilbainada_resp2_3, R.string.bilbainada_resp2_1),
        RespuestasCancion(R.string.bilbainada3, R.string.bilbainada_resp3_1, R.string.bilbainada_resp3_2, R.string.bilbainada_resp3_3, R.string.bilbainada_resp3_3),
        RespuestasCancion(R.string.bilbainada4, R.string.bilbainada_resp4_1, R.string.bilbainada_resp4_2, R.string.bilbainada_resp4_3, R.string.bilbainada_resp4_2),
        RespuestasCancion(R.string.bilbainada5, R.string.bilbainada_resp5_1, R.string.bilbainada_resp5_2, R.string.bilbainada_resp5_3, R.string.bilbainada_resp5_1),
        RespuestasCancion(R.string.bilbainada3, R.string.bilbainada_resp3_1, R.string.bilbainada_resp3_2, R.string.bilbainada_resp3_3, R.string.bilbainada_resp3_3),
        RespuestasCancion(R.string.bilbainada5, R.string.bilbainada_resp5_1, R.string.bilbainada_resp5_2, R.string.bilbainada_resp5_3, R.string.bilbainada_resp5_1),
    )

    /**
     * Array de IDs de audios.
     */
    private val AUDIOS: IntArray = intArrayOf(
        R.raw.bilbainada1, R.raw.bilbainada1_1,
        R.raw.bilbainada2, R.raw.bilbainada2_1,
        R.raw.bilbainada3, R.raw.bilbainada3_1,
        R.raw.bilbainada4, R.raw.bilbainada4_1,
        R.raw.bilbainada5, R.raw.bilbainada5_1,
        R.raw.bilbainada3, R.raw.bilbainada3_1,
        R.raw.bilbainada5, R.raw.bilbainada5_1)

    /**
     * Array de IDs de letras.
     */
    private val LETRAS: IntArray = intArrayOf(
        R.string.bilbainada1,
        R.string.bilbainada2,
        R.string.bilbainada3,
        R.string.bilbainada4,
        R.string.bilbainada5,
        R.string.bilbainada3,
        R.string.bilbainada5,
    )

    /**
     * Reproductor de audio.
     */
    private var mp: MediaPlayer? = null

    /**
     * Contador de la canción actual.
     */
    private  var contador: Int = 0
    /**
     * Número de aciertos.
     */
    private var aciertos: Int = 0

    /**
     * Botón de la opción 1.
     */
    private lateinit var btnOpcion1: Button
    /**
     * Botón de la opción 2.
     */
    private lateinit var btnOpcion2: Button
    /**
     * Botón de la opción 3.
     */
    private lateinit var btnOpcion3: Button

    /**
     * Array de botones de selección.
     */
    private lateinit var botonesSeleccion: Array<Button>

    /**
     * Botón de reproducción.
     */
    private lateinit var btnPlay: ImageButton

    /**
     * TextView de la canción.
     */
    private lateinit var tvCancion: TextView

    /**
     * TextView del contador.
     */
    private lateinit var tvContador: TextView
    /**
     * TextView de aciertos y fallos.
     */
    private lateinit var tvBienMal: TextView



    /**
     * Método llamado cuando la actividad es creada.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancion)

        aciertos = 0
        contador = 0

        btnOpcion1 = findViewById(R.id.btnOpcion1)
        btnOpcion2 = findViewById(R.id.btnOpcion2)
        btnOpcion3 = findViewById(R.id.btnOpcion3)
        botonesSeleccion = arrayOf(btnOpcion1, btnOpcion2, btnOpcion3)
        btnPlay = findViewById(R.id.btnPlay)
        tvCancion = findViewById(R.id.tvCancion)
        tvContador = findViewById(R.id.tvContador)
        tvBienMal = findViewById(R.id.tvBienMal)

        btnPlay.setOnClickListener{
            reproducirAudio(AUDIOS[contador * 2])
        }

        botonesSeleccion.forEach { btn ->
            btn.setOnClickListener{
                alSeleccionar(btn)
            }
        }

        mostrarLetra(LETRAS[contador])
        preprararRespuestas()

    }

    /**
     * Reproduce el audio correspondiente al recurso proporcionado.
     * @param recurso El recurso de audio a reproducir.
     */
    fun reproducirAudio(recurso: Int) {
        reproducirAudio(recurso) {}
    }
    /**
     * Reproduce el audio correspondiente al recurso proporcionado y ejecuta una acción al terminar la reproducción.
     * @param recurso El recurso de audio a reproducir.
     * @param alTerminar La acción a ejecutar al terminar la reproducción.
     */
    fun reproducirAudio(recurso: Int, alTerminar: () -> Unit): Unit {
        try {
            if (mp != null) { //Si estaba en reproducción, se para; y si estaba parado, se reproduce desde el principio.
                pararReproduccion()
            } else {
                mp = MediaPlayer.create(this, recurso)
                mp!!.setOnCompletionListener { alTerminar() }
                mp!!.start()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Reproduce forzadamente el audio correspondiente al recurso proporcionado y ejecuta una acción al terminar la reproducción.
     * @param recurso El recurso de audio a reproducir.
     * @param alTerminar La acción a ejecutar al terminar la reproducción.
     */
    fun forzarReproduccionDeAudio(recurso: Int, alTerminar: () -> Unit): Unit {
        try {
            if (mp != null) {
                pararReproduccion()
            }
            mp = MediaPlayer.create(this, recurso)
            mp!!.setOnCompletionListener { alTerminar() }
            mp!!.start()


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun pararReproduccion() {
        if (mp != null) {
            mp!!.stop()
            mp!!.release()
            mp = null
        }
    }

    private fun mostrarLetra(letra: Int): Unit {
        tvCancion.text = String.format(getText(letra).toString(), "___________________")
    }

    private fun alSeleccionar(btn: Button): Unit {
        btnPlay.isEnabled = false
        botonesSeleccion.forEach {btn -> btn.isEnabled = false}

        val finDelJuego: Boolean = contador == 6
        var resp: RespuestasCancion = RESPUESTAS[contador]
        forzarReproduccionDeAudio(AUDIOS[contador++ * 2 + 1]) {
            if (!finDelJuego) {
                mostrarLetra(LETRAS[contador])
                preprararRespuestas()
            }
        }
        comprobarRespuesta(btn, resp)

        if (finDelJuego) {
            seAcabo()
        }
    }

    /**
     * Método llamado cuando el juego ha terminado.
     * Oculta los botones de selección y muestra un diálogo con el resultado del juego.
     */
    private fun seAcabo() {
        botonesSeleccion.forEach { btn ->
            var view: View = btn.parent as View
            view.visibility = View.INVISIBLE
        }

        var fragmentManager: FragmentManager = supportFragmentManager
        if (aciertos == 7) {
            Utils.anadirSuperado(this, Lugar.PARQUE)
        }
        var dialogo = DialogoFinJuego(aciertos, 7)
        dialogo.show(fragmentManager, "GAME OVER")

    }

    /**
     * Comprueba si la respuesta seleccionada es correcta y actualiza la interfaz en consecuencia.
     *
     * @param btn El botón de la respuesta seleccionada.
     * @param resp El objeto que contiene la información de la respuesta correcta.
     */
    private fun comprobarRespuesta(btn: Button, resp: RespuestasCancion) {

        var indResp: Int = getIndiceRespuesta(btn)
        var indCorrecto = resp.getIndiceCorrecta()

        tvCancion.text = String.format(getText(resp.pregunta).toString(), getText(resp.correcta).toString().uppercase())

        botonesSeleccion.forEach { btn ->  btn.setBackgroundResource(R.color.gray)}

        if (indResp == indCorrecto) {
            tvBienMal.text = "OSO ONDO!"
            tvBienMal.setTextColor(resources.getColor(R.color.green))
            aciertos++
            botonesSeleccion[indCorrecto].setBackgroundResource(R.color.green)
        } else {
            tvBienMal.text = "GAIZKI!"
            tvBienMal.setTextColor(resources.getColor(R.color.red))
            botonesSeleccion[indCorrecto].setBackgroundResource(R.color.green)
            botonesSeleccion[indResp].setBackgroundResource(R.color.red)
        }
        tvBienMal.visibility = View.VISIBLE

    }

    /**
     * Prepara las respuestas para ser mostradas en la interfaz.
     */
    private fun preprararRespuestas() {
        btnOpcion1.setText(RESPUESTAS[contador].opcion1)
        btnOpcion1.setBackgroundResource(R.color.green)
        btnOpcion2.setText(RESPUESTAS[contador].opcion2)
        btnOpcion2.setBackgroundResource(R.color.violet)
        btnOpcion3.setText(RESPUESTAS[contador].opcion3)
        btnOpcion3.setBackgroundResource(R.color.yellow)

        tvContador.setText(String.format("%d/7", contador + 1))
        tvBienMal.visibility = View.INVISIBLE

        forzarReproduccionDeAudio(AUDIOS[contador * 2]) {}
        botonesSeleccion.forEach {btn -> btn.isEnabled = true}
        btnPlay.isEnabled = true
    }

    /**
     * Obtiene el índice de la respuesta seleccionada.
     *
     * @param btn El botón de la respuesta seleccionada.
     * @return El índice de la respuesta seleccionada.
     */
    private fun getIndiceRespuesta(btn: Button): Int{
        when (btn.id) {
            R.id.btnOpcion1 -> return 0
            R.id.btnOpcion2 -> return 1
            R.id.btnOpcion3 -> return 2
            else -> return -1
        }
    }

    override fun onPossitiveButtonClick() {
        pararReproduccion()
        if (aciertos == 7) {
            lanzarJuego(arrayOf(
                resources.getText(R.string.sardina_y_puzzle).toString(),
            ), intArrayOf(
                R.drawable.sardinaypuzzle
            ), R.raw.sardina_y_puzzle_sarejosle, Intent(applicationContext, MainActivity::class.java)
            )
        } else {
            resetearJuego()
        }
    }

    override fun onNegativeButtonClick() {
        pararReproduccion()
        finish()
    }

    /**
     * Reinicia el juego.
     */
    private fun resetearJuego() {
        contador = 0
        aciertos = 0

        botonesSeleccion.forEach { btn ->
            var view: View = btn.parent as View
            view.visibility = View.VISIBLE
        }

        mostrarLetra(LETRAS[contador])
        preprararRespuestas()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        pararReproduccion()
    }

}
