package com.example.didactic_app

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

abstract class Lanzador: AppCompatActivity() {

    public val FONDO_PARAM: String = "fondo";
    public val AUDIO_PARAM: String = "audio";
    public val TEXTO_PARAM: String = "texto";

    var intencion: Intent? = null

    fun lanzarJuego(intencion: Intent) {
        lanzarJuego(null, null, null, intencion)
    }
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

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            startActivity(intencion)
        }
    }

}