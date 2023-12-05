package com.example.didactic_app

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import kotlin.math.abs
import kotlin.math.sin

class LanzamientoActivity : AppCompatActivity() {
    private lateinit var bolaImageView: ImageView
    private lateinit var diana: View

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lanzamiento)

        bolaImageView = findViewById(R.id.bolaImageView)
        diana = findViewById(R.id.diana)

        amplitud = Resources.getSystem().displayMetrics.widthPixels / 2f

        iniciarAnimacionMAS()

        bolaImageView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    offsetX = event.x - bolaImageView.x
                    offsetY = event.y - bolaImageView.y
                    velocidadX = 0f
                    velocidadY = 0f

                    xInicial = event.x - offsetX
                    yInicial = event.y - offsetY
                }
                MotionEvent.ACTION_MOVE -> {

                    var x = event.x - offsetX
                    var y = event.y - offsetY

                    var altura = Resources.getSystem().displayMetrics.heightPixels
                    var limite = altura * 2 / 3
                    if (y > limite) {
                        bolaImageView.x = x
                        bolaImageView.y = y
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

    private fun moverBolaConVelocidad() {
        while (abs(velocidadX) > 1 || abs(velocidadY) > 1) {
            runOnUiThread {
                velocidadX -= abs(velocidadX) * 0.05f * (velocidadX / abs(velocidadX))
                velocidadY -= velocidadY * 0.05f
                bolaImageView.x += velocidadX
                bolaImageView.y += velocidadY
            }
            try {
                // Simular intervalo de tiempo (puedes ajustar según tus necesidades)
                Thread.sleep(25)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    private fun iniciarAnimacionMAS() {
        val periodo = 2000L // Ajusta el periodo según tus necesidades
        val startTime = System.currentTimeMillis()

        val runnable = object : Runnable {
            override fun run() {
                val currentTime = System.currentTimeMillis()
                val deltaTime = currentTime - startTime

                // Calcular la posición horizontal usando la función sin
                val newX = amplitud * sin(frecuencia * deltaTime.toFloat())

                // Establecer la posición en el eje X de la vista "diana"
                diana.translationX = newX.toFloat()

                // Programar la próxima ejecución del runnable
                diana.postDelayed(this, 16) // Aproximadamente 60 cuadros por segundo

                // Detener la animación después de un periodo específico (opcional)
//                if (deltaTime >= periodo) {
//                    diana.removeCallbacks(this)
//                }
            }
        }

        // Programar la primera ejecución del runnable
        diana.post(runnable)
    }
}