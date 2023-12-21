package com.example.didactic_app

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.didactic_app.model.Pez
import kotlin.math.log
import kotlin.random.Random

class AtraparSardinasActivity : AppCompatActivity() {
    private lateinit var tvCantidadSardinas: TextView
    private lateinit var tvTiempo: TextView
    private lateinit var tvTemporizadorInicio: TextView
    private lateinit var lyPrincipal: ConstraintLayout
    private var cuentaAtrasInicio = 3


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atrapar_sardinas)

        tvTiempo = findViewById(R.id.tvTiempo)
        tvCantidadSardinas = findViewById(R.id.tvCantidadSardinas)
        tvTemporizadorInicio = findViewById(R.id.tvTemporizadorInicio)

        tvTiempo.text = getString(R.string.tiempo)
        tvCantidadSardinas.text = 0.toString()

        lyPrincipal = findViewById(R.id.lyPrincipal)

        val temporizadorInicio = TemporizadorComponent(tvTemporizadorInicio,
            5000)

        val temporizadorJuego = TemporizadorComponent(tvTiempo,30000)

        temporizadorJuego.onTick =  {
            try {
                val derecha = Random.nextBoolean()
                val pez = Pez(this, lyPrincipal, derecha)
                val direction = 1750
                val duracionMovimiento = (2500L..5000L).random()
                val duracionFinal = (250L..500L).random()
                pez.startAnimation(
                    direction,
                    duracionMovimiento,
                    duracionFinal
                ) { clickSardina() }
                lyPrincipal.addView(pez.imagen)
            } catch (_: Exception) { }
        }

        temporizadorInicio.onTick = { cuentaAtrasDelInicio(temporizadorJuego)}
        temporizadorInicio.startTimer()
    }

    private fun cuentaAtrasDelInicio(temporizadorJuego:TemporizadorComponent){
        if(cuentaAtrasInicio > 0) {
            tvTemporizadorInicio.text = cuentaAtrasInicio.toString()
        } else if(cuentaAtrasInicio == 0){
            tvTemporizadorInicio.text = getText(R.string.iniciar_juego)
        }else{
            tvTemporizadorInicio.visibility = View.GONE
            temporizadorJuego.startTimer()
        }
        cuentaAtrasInicio -= 1
    }

    private fun clickSardina(){
        // Aquí puedes manejar el evento de toque en una parte no transparente de la imagen
        val cant = tvCantidadSardinas.text.toString().toInt()
        tvCantidadSardinas.text = cant.plus(1).toString()
    }

}