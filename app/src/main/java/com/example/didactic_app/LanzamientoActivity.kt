package com.example.didactic_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import kotlin.math.abs

class LanzamientoActivity : AppCompatActivity() {
    private lateinit var bolaImageView: ImageView
    private var offsetX = 0f
    private var offsetY = 0f
    private var velocidadX = 0f
    private var velocidadY = 0f

    private  var xInicial = 0f
    private  var yInicial = 0f

    private  var xFinal = 0f
    private  var yFinal = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lanzamiento)

        bolaImageView = findViewById(R.id.bolaImageView)

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
                    bolaImageView.x = event.x - offsetX
                    bolaImageView.y = event.y - offsetY
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
                        val factorDesaceleracion = 0.18f
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
}