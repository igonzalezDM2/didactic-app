package com.example.didactic_app

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Handler.Callback
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import java.lang.Thread.sleep
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timerTask

/**
 * Clase ExplicacionActivity que extiende de AppCompatActivity.
 */
class ExplicacionActivity : AppCompatActivity() {



    /**
     * Imagen de fondo de la explicación.
     */
    lateinit var ivFondo: ImageView
    /**
     * Texto de la explicación.
     */
    lateinit var tvExplicacion: TextView
    /**
     * Botón para reproducir audio.
     */
    lateinit var btnAudio: ImageButton
    /**
     * Botón para avanzar en la explicación.
     */
    lateinit var btnAvanzar: ImageButton

    /**
     * Reproductor de audio.
     */
    private var mp: MediaPlayer? = null

    /**
     * ID del recurso de audio.
     */
    private var audio: Int? = null
    /**
     * Array de texto de la explicación.
     */
    private var texto: Array<String>? = null
    /**
     * Array de IDs de recursos de imagen de fondo.
     */
    private var fondo: IntArray? = null

    /**
     * Índice actual.
     */
    private var index = 0
    /**
     * Retraso para la transición de imágenes.
     */
    private val DELAY = 50


    /**
     * Método llamado cuando la actividad es creada.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explicacion)

        ivFondo = findViewById(R.id.ivFondo)
        tvExplicacion = findViewById(R.id.tvExplicacion)
        btnAudio = findViewById(R.id.btnAudio)
        btnAvanzar = findViewById(R.id.btnAvanzar)

        var variables = intent.extras
        if (variables != null) {
            fondo = variables.getIntArray("fondo")
            if (fondo != null) {

                if (fondo!!.size > 0) {

                    var ind:Int = 0
                    Timer().scheduleAtFixedRate(timerTask {
                        runOnUiThread() {
                            setRecursoImagen(fondo!![ind])
                            ind++
                            if (ind == fondo!!.size) {
                                ind = 0
                            }
                        }
                    }, 0, 5000)

                } else if (fondo != null && fondo!!.isNotEmpty()){
                    setRecursoImagen(fondo!![0])
                }
            }
            val recursoAudio: Int = variables.getInt("audio")
            if (recursoAudio > 0) {
                setRecursoAudio(recursoAudio)
            }
            texto = variables.getStringArray("texto")
            if (texto != null) {
                tvExplicacion.text = texto!![0]
            }
        }

        (btnAudio.parent as View).isVisible = false
        if (audio != null && audio!! > 0) {
            (btnAudio.parent as View).isVisible = true
            btnAudio.setOnClickListener() {
                reproducirAudio(audio)
            }
        }

        btnAvanzar.setOnClickListener() {
            var indice: Int = texto!!.indexOf(tvExplicacion.text.toString())
            if (texto != null && indice < texto!!.size - 1) {
                tvExplicacion.text = texto!![indice + 1]
            } else {
                pararReproduccion()
                var intencion = Intent()
                setResult(RESULT_OK, intencion)
                finish()
            }
        }

    }

    /**
     * Reproduce el audio correspondiente al recurso proporcionado.
     *
     * @param recurso El recurso de audio a reproducir
     */
    fun reproducirAudio(recurso: Int?) {
        reproducirAudio(recurso) {}

    }
    /**
     * Reproduce el audio correspondiente al recurso proporcionado y ejecuta la acción al terminar la reproducción.
     *
     * @param recurso El recurso de audio a reproducir
     * @param alTerminar La acción a ejecutar al terminar la reproducción
     */
    fun reproducirAudio(recurso: Int?, alTerminar: () -> Unit): Unit {
        try {
            if (mp != null) { //Si estaba en reproducción, se para; y si estaba parado, se reproduce desde el principio.
                pararReproduccion()
                btnAudio.setImageResource(R.drawable.audio_muted)
            } else if (recurso != null) {
                btnAudio.setImageResource(R.drawable.audio_vector)
                mp = MediaPlayer.create(this, recurso)
                mp!!.setOnCompletionListener { alTerminar() }
                mp!!.start()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Reproduce forzosamente el audio correspondiente al recurso proporcionado y ejecuta la acción al terminar la reproducción.
     *
     * @param recurso El recurso de audio a reproducir
     * @param alTerminar La acción a ejecutar al terminar la reproducción
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

    /**
     * Detiene la reproducción de audio si se está reproduciendo.
     */
    private fun pararReproduccion() {
        if (mp != null) {
            mp!!.stop()
            mp!!.release()
            mp = null
        }
    }

    /**
     * Establece la imagen correspondiente al recurso proporcionado.
     *
     * @param imagen El recurso de imagen a establecer
     */
    fun setRecursoImagen(imagen: Int) {
        ivFondo.setImageResource(imagen)
    }

    /**
     * Establece el recurso de audio a reproducir.
     *
     * @param audio El recurso de audio a establecer
     */
    fun setRecursoAudio(audio: Int) {
        this.audio = audio
    }

    override fun onBackPressed() {
        super.onBackPressed()
        pararReproduccion()
    }

}
