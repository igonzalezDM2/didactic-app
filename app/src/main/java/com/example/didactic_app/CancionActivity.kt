package com.example.didactic_app

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.didactic_app.model.RespuestasCancion

class CancionActivity : AppCompatActivity() {

    private val RESPUESTAS: Array<RespuestasCancion> = arrayOf(
        RespuestasCancion(R.string.bilbainada1, R.string.bilbainada_resp1_1, R.string.bilbainada_resp1_2, R.string.bilbainada_resp1_3, R.string.bilbainada_resp1_2),
        RespuestasCancion(R.string.bilbainada2, R.string.bilbainada_resp2_1, R.string.bilbainada_resp2_2, R.string.bilbainada_resp2_3, R.string.bilbainada_resp2_1),
        RespuestasCancion(R.string.bilbainada3, R.string.bilbainada_resp3_1, R.string.bilbainada_resp3_2, R.string.bilbainada_resp3_3, R.string.bilbainada_resp3_3),
        RespuestasCancion(R.string.bilbainada4, R.string.bilbainada_resp4_1, R.string.bilbainada_resp4_2, R.string.bilbainada_resp4_3, R.string.bilbainada_resp4_2),
        RespuestasCancion(R.string.bilbainada5, R.string.bilbainada_resp5_1, R.string.bilbainada_resp5_2, R.string.bilbainada_resp5_3, R.string.bilbainada_resp5_1),
        RespuestasCancion(R.string.bilbainada3, R.string.bilbainada_resp3_1, R.string.bilbainada_resp3_2, R.string.bilbainada_resp3_3, R.string.bilbainada_resp3_3),
        RespuestasCancion(R.string.bilbainada5, R.string.bilbainada_resp5_1, R.string.bilbainada_resp5_2, R.string.bilbainada_resp5_3, R.string.bilbainada_resp5_1),
    )

    private val AUDIOS: IntArray = intArrayOf(
        R.raw.bilbainada1, R.raw.bilbainada1_1,
        R.raw.bilbainada2, R.raw.bilbainada2_1,
        R.raw.bilbainada3, R.raw.bilbainada3_1,
        R.raw.bilbainada4, R.raw.bilbainada4_1,
        R.raw.bilbainada5, R.raw.bilbainada5_1,
        R.raw.bilbainada3, R.raw.bilbainada3_1,
        R.raw.bilbainada5, R.raw.bilbainada5_1)

    private val LETRAS: IntArray = intArrayOf(
        R.string.bilbainada1,
        R.string.bilbainada2,
        R.string.bilbainada3,
        R.string.bilbainada4,
        R.string.bilbainada5,
        R.string.bilbainada3,
        R.string.bilbainada5,
    )

    private var mp: MediaPlayer? = null

    private  var contador: Int = 0
    private var aciertos: Int = 0

    private lateinit var btnOpcion1: Button
    private lateinit var btnOpcion2: Button
    private lateinit var btnOpcion3: Button

    private lateinit var botonesSeleccion: Array<Button>

    private lateinit var btnPlay: ImageButton

    private lateinit var tvCancion: TextView

    private lateinit var tvContador: TextView
    private lateinit var tvBienMal: TextView



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

    fun reproducirAudio(recurso: Int) {
        reproducirAudio(recurso) {}
    }
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
        val finDelJuego: Boolean = contador == 6
        var resp: RespuestasCancion = RESPUESTAS[contador]
        forzarReproduccionDeAudio(AUDIOS[contador++ * 2 + 1]) {
            if (!finDelJuego) {
                mostrarLetra(LETRAS[contador])
                preprararRespuestas()
            }
        }
        if (!finDelJuego) {
            comprobarRespuesta(btn, resp)
        } else {
            seAcabo()
        }
    }

    private fun seAcabo() {
        botonesSeleccion.forEach { btn ->
            var view: View = btn.parent as View
            view.visibility = View.INVISIBLE
        }

        //TODO: CAMBIAR ESTE TOAST POR ALGO COMO DIOS MANDA
        val toast = Toast.makeText(this, "$aciertos / $contador", Toast.LENGTH_SHORT)
        toast.show()

    }

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

    private fun preprararRespuestas() {
        btnOpcion1.setText(RESPUESTAS[contador].opcion1)
        btnOpcion1.setBackgroundResource(R.color.green)
        btnOpcion2.setText(RESPUESTAS[contador].opcion2)
        btnOpcion2.setBackgroundResource(R.color.violet)
        btnOpcion3.setText(RESPUESTAS[contador].opcion3)
        btnOpcion3.setBackgroundResource(R.color.yellow)

        tvContador.setText(String.format("%d/7", contador + 1))
        tvBienMal.visibility = View.INVISIBLE
    }

    private fun getIndiceRespuesta(btn: Button): Int{
        when (btn.id) {
            R.id.btnOpcion1 -> return 0
            R.id.btnOpcion2 -> return 1
            R.id.btnOpcion3 -> return 2
            else -> return -1
        }
    }

}