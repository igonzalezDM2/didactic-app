package com.example.didactic_app

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
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
import androidx.fragment.app.FragmentManager
import com.example.didactic_app.dialogs.DialogoFinJuego
import com.example.didactic_app.dialogs.OnDialogoConfirmacionListener
import com.example.didactic_app.enums.Lugar
import com.example.didactic_app.model.Pez
import com.example.didactic_app.utilis.Utils
import kotlin.math.log
import kotlin.random.Random

class AtraparSardinasActivity : Lanzador() {
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

        tvTiempo = findViewById(R.id.tvTiempo)
        tvCantidadSardinas = findViewById(R.id.tvCantidadSardinas)
        tvTemporizadorInicio = findViewById(R.id.tvTemporizadorInicio)

        tvTiempo.text = getString(R.string.tiempo)
        tvCantidadSardinas.text = 0.toString()

        lyPrincipal = findViewById(R.id.lyPrincipal)

        temporizadorInicio = TemporizadorComponent(tvTemporizadorInicio,
            5000)

        temporizadorJuego = TemporizadorComponent(tvTiempo,30999)

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
                    duracionFinal,
                    lyPrincipal
                ) { clickSardina() }
                lyPrincipal.addView(pez.imagen)
            } catch (_: Exception) { }
        }

        temporizadorJuego.onFinish = {
            Utils.anadirSuperado(this, Lugar.SARDINERA)
            lanzarJuego(arrayOf(
                resources.getText(R.string.sardina_y_puzzle).toString(),
            ), intArrayOf(
                R.drawable.sardinaypuzzle
            ), R.raw.sardina_y_puzzle_sarejosle, Intent(applicationContext, MainActivity::class.java)
            )
        }

        temporizadorInicio.onTick = { cuentaAtrasDelInicio()}
        temporizadorInicio.startTimer()
    }

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

    private fun clickSardina(){
        // Aquí puedes manejar el evento de toque en una parte no transparente de la imagen
        val cant = tvCantidadSardinas.text.toString().toInt()
        tvCantidadSardinas.text = cant.plus(1).toString()
    }

}