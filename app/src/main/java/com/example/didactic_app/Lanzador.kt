package com.example.didactic_app

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

abstract class Lanzador: AppCompatActivity() {

    /**
     * Parámetro para el fondo de la explicación.
     */
    public val FONDO_PARAM: String = "fondo";
    /**
     * Parámetro para el audio de la explicación.
     */
    public val AUDIO_PARAM: String = "audio";
    /**
     * Parámetro para el texto de la explicación.
     */
    public val TEXTO_PARAM: String = "texto";

    /**
     * Intent para lanzar el juego.
     */
    var intencion: Intent? = null

    /**
     * Lanza el juego directamente
     *
     * @param intencion La intención de lanzar el juego
     */
    fun lanzarJuego(intencion: Intent) {
        lanzarJuego(null, null, null, intencion)
    }

    /**
     * Lanza el juego con explicación, fondo de YouTube, audio y una intención.
     *
     * @param explicacion El texto de la explicación
     * @param fondo El array de recursos de fondo
     * @param audio El recurso de audio
     * @param intencion La intención de lanzar el juego
     */
    fun lanzarJuego(explicacion: Array<String>?, fondo: IntArray?, audio: Int?, intencion: Intent) {
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

    /**
     * Lanza el juego con explicación, fondo de YouTube, audio y una intención.
     *
     * @param explicacion El texto de la explicación
     * @param fondo El enlace del video de YouTube
     * @param audio El recurso de audio
     * @param intencion La intención de lanzar el juego
     */
    fun lanzarJuegoYT(explicacion: Array<String>?, fondo: String?, audio: Int?, intencion: Intent) {
        this.intencion = intencion
        if (!explicacion.isNullOrEmpty()) {
            val intentExplicacion: Intent = Intent(this, ExplicacionActivityYT::class.java)
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

    /**
     * Resultado de la actividad lanzada para obtener un resultado.
     */
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            startActivity(intencion)
        }
    }

}