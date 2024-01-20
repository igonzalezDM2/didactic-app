package com.example.didactic_app

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.example.didactic_app.dialogs.DialogoFinJuego
import com.example.didactic_app.dialogs.DialogoFinJuegoLanzamiento
import com.example.didactic_app.dialogs.OnDialogoConfirmacionListener
import kotlin.math.abs
import kotlin.math.sin

/**
 * Clase que representa la actividad de lanzamiento con funcionalidades de juego.
 */
class LanzamientoActivity : Lanzador(), OnDialogoConfirmacionListener {

    private var contador = 0
    private var restantes = 6

    private lateinit var sardina: ImageView
    private lateinit var diana: View
    private lateinit var tvRestantes: TextView
    private lateinit var fumanchuRelativo: RelativeLayout

    private  var xPorDefecto = -1f
    private  var yPorDefecto = -1f

    private var offsetX = 0f
    private var offsetY = 0f
    private var velocidadX = 0f
    private var velocidadY = 0f

    private  var xInicial = 0f
    private  var yInicial = 0f

    private  var xFinal = 0f
    private  var yFinal = 0f

    private var amplitud = 100f // Ajusta la amplitud según tus necesidades
    private var frecuencia = 2 * Math.PI / 5000 // Ajusta la frecuencia según tus necesidades

    /**
     * Método que se llama al crear la actividad.
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lanzamiento)

        sardina = findViewById(R.id.bolaImageView)
        diana = findViewById(R.id.diana)
        tvRestantes = findViewById(R.id.tvRestantes)
        fumanchuRelativo = findViewById(R.id.fumanchu_relativo)
        tvRestantes.text = restantes.toString()

        sardina.post {
            xPorDefecto = sardina.x
            yPorDefecto = sardina.y
        }

        amplitud = Resources.getSystem().displayMetrics.widthPixels / 2f

        iniciarAnimacionMAS()

        sardina.setOnTouchListener { _, event ->

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    offsetX = event.x - sardina.x
                    offsetY = event.y - sardina.y
                    velocidadX = 0f
                    velocidadY = 0f

                    xInicial = event.x - offsetX
                    yInicial = event.y - offsetY
                }
                MotionEvent.ACTION_MOVE -> {

                    val x = event.x - offsetX
                    val y = event.y - offsetY

                    val altura = Resources.getSystem().displayMetrics.heightPixels
                    val limite = altura * 2 / 3
                    if (y > limite) {
                        sardina.x = x
                        sardina.y = y
                    }

                }
                MotionEvent.ACTION_UP -> {
                    // Calcular la velocidad basada en la diferencia de posición entre DOWN y UP
                    xFinal = event.x - offsetX
                    yFinal = event.y - offsetY
//                    velocidadX = event.x - offsetX - bolaImageView.x
//                    velocidadY = event.y - offsetY - bolaImageView.y
                    velocidadX = xFinal - xInicial
                    velocidadY = yFinal - yInicial


                    // Limitar el lanzamiento solo hacia arriba
                    if (velocidadY < 0) {
                        // Simular desaceleración reduciendo la velocidad gradualmente
                        val factorDesaceleracion = 0.25f
                        velocidadX *= factorDesaceleracion
                        velocidadY *= factorDesaceleracion

                        // Aplicar la velocidad al movimiento de la bola en un hilo separado
                        Thread {
                            moverBolaConVelocidad()
                        }.start()
                    }
                }
            }
            true
        }
    }



    /**
     * Método que mueve la bola con una velocidad determinada.
     */
    private fun moverBolaConVelocidad() {
        var parar = false
        val intervalo = 25
        val limiteTiempo = 1500
        var transcurrido = 0

        while (!parar && (abs(velocidadX) > 1 || abs(velocidadY) > 1)) {
            runOnUiThread {
                velocidadX -= abs(velocidadX) * 0.05f * (velocidadX / abs(velocidadX))
                velocidadY -= velocidadY * 0.05f
                sardina.x += velocidadX
                sardina.y += velocidadY

                val intersecta = intersecta(diana)

                if (transcurrido > limiteTiempo || intersecta) {
                    parar = true
                    sardina.x = xPorDefecto
                    sardina.y = yPorDefecto

                    restantes--
                    tvRestantes.text = restantes.toString()

                    if (intersecta) {
                        contador++
                    }

                    if (restantes == 0) {
                        var fragmentManager: FragmentManager = supportFragmentManager
                        var dialogo = DialogoFinJuego(contador, 6)
                        dialogo.show(fragmentManager, "GAME OVER")


//                        var fragmentManager: FragmentManager = supportFragmentManager
//                        var dialogo = DialogoFinJuegoLanzamiento(contador)
//                        dialogo.show(fragmentManager, "GAME OVER")
                    }
                }
            }
            try {
                // Simular intervalo de tiempo (puedes ajustar según tus necesidades)
                Thread.sleep(intervalo.toLong())
                transcurrido += intervalo
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        sardina.x = xPorDefecto
        sardina.y = yPorDefecto

    }

    /**
     * Método que inicia la animación de la bola.
     */
    private fun iniciarAnimacionMAS() {
//        val periodo = 2000L // Ajusta el periodo según tus necesidades
        val startTime = System.currentTimeMillis()

        val runnable = object : Runnable {
            override fun run() {
                val currentTime = System.currentTimeMillis()
                val deltaTime = currentTime - startTime

                // Calcular la posición horizontal usando la función sin
                val newX = amplitud * sin(frecuencia * deltaTime.toFloat())

                // Establecer la posición en el eje X de la vista "diana"
//                diana.translationX = newX.toFloat()
                fumanchuRelativo.translationX = newX.toFloat()

                // Programar la próxima ejecución del runnable
//                diana.postDelayed(this, 16) // Aproximadamente 60 cuadros por segundo
                fumanchuRelativo.postDelayed(this, 16) // Aproximadamente 60 cuadros por segundo

                // Detener la animación después de un periodo específico (opcional)
//                if (deltaTime >= periodo) {
//                    diana.removeCallbacks(this)
//                }
            }
        }

        // Programar la primera ejecución del runnable
        diana.post(runnable)
    }

    /**
     * Verifica si hay intersección entre la vista de referencia y la vista "sardina".
     *
     * @param referencia La vista de referencia con la que se verifica la intersección
     * @return true si hay intersección, de lo contrario false
     */
    private fun intersecta(referencia: View): Boolean {
        val firstPosition = IntArray(2)
        val secondPosition = IntArray(2)

        referencia.getLocationOnScreen((firstPosition))
        sardina.getLocationOnScreen((secondPosition))

        val rectFirstView = Rect(
            firstPosition[0],
            firstPosition[1],
            firstPosition[0] + referencia.measuredWidth,
            firstPosition[1] + referencia.measuredHeight
        )
        val rectSecondView = Rect(
            secondPosition[0],
            secondPosition[1],
            secondPosition[0] + sardina.measuredWidth,
            secondPosition[1] + sardina.measuredHeight
        )
        return rectFirstView.intersect(rectSecondView)
    }

    /**
     * Método que maneja la acción al hacer clic en el botón positivo.
     */
    override fun onPossitiveButtonClick() {
        if (contador == 6) {
            lanzarJuego(arrayOf(
                resources.getText(R.string.explicacion_azkena1).toString(),
            ), intArrayOf(
                R.drawable.azkena
            ), R.raw.azkena_1, Intent(this, Puzzle4x2Activity::class.java))
        } else {
            resetearJuego()
        }
    }

    /**
     * Método que maneja la acción al hacer clic en el botón negativo.
     */
    override fun onNegativeButtonClick() {
        finish()
    }

    /**
     * Reinicia el juego.
     */
    private fun resetearJuego() {
        contador = 0
        restantes = 6
        tvRestantes.text = 6.toString()
        sardina.x = xPorDefecto
        sardina.y = yPorDefecto
    }
}