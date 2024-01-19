package com.example.didactic_app

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import java.util.Timer
import kotlin.concurrent.timerTask


class ExplicacionActivityYT : AppCompatActivity() {



    lateinit var ivFondo: WebView
    lateinit var tvExplicacion: TextView
    lateinit var btnAudio: ImageButton
    lateinit var btnAvanzar: ImageButton

    private var mp: MediaPlayer? = null

    private var audio: Int? = null
    private var texto: Array<String>? = null
    private var fondo: String? = null

    private var index = 0
    private val DELAY = 50


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explicacion_yt)

        ivFondo = findViewById(R.id.ivFondo)
        tvExplicacion = findViewById(R.id.tvExplicacion)
        btnAudio = findViewById(R.id.btnAudio)
        btnAvanzar = findViewById(R.id.btnAvanzar)

        val webSettings = ivFondo.settings
        webSettings.javaScriptEnabled = true

        ivFondo.webChromeClient = WebChromeClient() // Necesario para la reproducción de videos


        // URL del video de YouTube (reemplázalo con el enlace del video que desees mostrar)



        var variables = intent.extras
        if (variables != null) {
            fondo = variables.getString("fondo")
            if (fondo != null) {
                // Configura el WebView para cargar el video
                val html =
                    "<iframe width=\"100%\" height=\"100%\" src=\"$fondo\" frameborder=\"0\" allowfullscreen></iframe>"
                ivFondo.loadData(html, "text/html", "utf-8")
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

    fun reproducirAudio(recurso: Int?) {
        reproducirAudio(recurso) {}
    }
    fun reproducirAudio(recurso: Int?, alTerminar: () -> Unit): Unit {
        try {
            if (mp != null) { //Si estaba en reproducción, se para; y si estaba parado, se reproduce desde el principio.
                pararReproduccion()
            } else if (recurso != null) {
                mp = MediaPlayer.create(this, recurso)
                mp!!.setOnCompletionListener { alTerminar() }
                mp!!.start()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

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

    fun setRecursoAudio(audio: Int) {
        this.audio = audio
    }


}