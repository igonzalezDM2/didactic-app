package com.example.didactic_app

import android.annotation.SuppressLint
import android.os.Bundle

import android.view.View

import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

import androidx.constraintlayout.widget.ConstraintLayout

import com.example.didactic_app.model.Pez

import kotlin.random.Random

/**
 * Actividad que representa el juego para Atrapar Sardinas
 */
class AtraparSardinasActivity : AppCompatActivity() {
    private lateinit var tvCantidadSardinas: TextView
    private lateinit var tvTiempo: TextView
    private lateinit var tvTemporizadorInicio: TextView
    private lateinit var lyPrincipal: ConstraintLayout
    private lateinit var temporizadorInicio: TemporizadorComponent
    private lateinit var temporizadorJuego: TemporizadorComponent
    private var cuentaAtrasInicio = 3

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atrapar_sardinas)

        //Conseguiendo a los elementos de la actividad
        tvTiempo = findViewById(R.id.tvTiempo)
        tvCantidadSardinas = findViewById(R.id.tvCantidadSardinas)
        tvTemporizadorInicio = findViewById(R.id.tvTemporizadorInicio)
        lyPrincipal = findViewById(R.id.lyPrincipal)

        //Inicializar cantidad de peces a 0
        tvCantidadSardinas.text = 0.toString()

        //Crear los temporizadores
        temporizadorInicio = TemporizadorComponent(tvTemporizadorInicio, 5000)
        temporizadorJuego = TemporizadorComponent(tvTiempo,30999)

        // Funcionalidad del juego en cada segundo
        temporizadorJuego.onTick =  {
            try {
                val derecha = Random.nextBoolean()
                val pez = Pez(this, lyPrincipal, derecha)
                val duracionMovimiento = (2500L..5000L).random()
                val duracionFinal = (250L..500L).random()
                pez.startAnimation(
                    duracionMovimiento,
                    duracionFinal,
                    lyPrincipal
                ) { clickSardina() }
                lyPrincipal.addView(pez.imagen)
            } catch (_: Exception) { }
        }
        // Funcionalidad de cada segundo del primer temporizador
        temporizadorInicio.onTick = { cuentaAtrasDelInicio() }
        // Al terminar el temporizador del juego
        temporizadorJuego.onFinish = { finish() }
        //Poner en marcha el temporizador
        temporizadorInicio.startTimer()
    }

    /**
     * Cuenta atras para iniciar el juego
     */
    private fun cuentaAtrasDelInicio(){
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

    /**
     * Evento de darle click a la sardina
     */
    private fun clickSardina(){
        val cant = tvCantidadSardinas.text.toString().toInt()
        tvCantidadSardinas.text = cant.plus(1).toString()
    }

}