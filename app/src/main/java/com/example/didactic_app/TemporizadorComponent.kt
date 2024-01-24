package com.example.didactic_app

import android.os.CountDownTimer
import android.widget.TextView

/**
 * Componente para manejar un temporizador.
 *
 * @param timerTextView la vista del temporizador
 * @param initialTimeInMillis el tiempo inicial en milisegundos
 */
class TemporizadorComponent (
    private val timerTextView: TextView,
    private val initialTimeInMillis: Long,
) {

     /**
      * Función que se ejecuta al finalizar el temporizador.
      */
     var onFinish: () -> Unit = {}
     
     /**
      * Función que se ejecuta en cada tick del temporizador.
      *
      * @param millisLeftInMillis los milisegundos restantes
      */
     var onTick: (Long) -> Unit = {}

    /**
     * Temporizador para contar el tiempo restante.
     */
    private lateinit var countDownTimer: CountDownTimer
    /**
     * Tiempo restante en milisegundos.
     */
    private var timeLeftInMillis: Long = initialTimeInMillis

    /**
     * Inicia el temporizador.
     */
    fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimer()
                if (this@TemporizadorComponent.onTick != null) {
                    this@TemporizadorComponent.onTick(timeLeftInMillis)
                }
            }

            override fun onFinish() {
                timerTextView.text = "00:00"
                if (this@TemporizadorComponent.onFinish != null) {
                    this@TemporizadorComponent.onFinish()
                }
            }
        }.start()
    }

    /**
     * Detiene el temporizador.
     */
    fun stopTimer() {
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }
    }

    /**
     * Actualiza el temporizador con el tiempo restante en formato HH:MM.
     */
    private fun updateTimer() {
        val hours = (timeLeftInMillis / 3600000).toInt()
        val minutes = ((timeLeftInMillis % 3600000) / 60000).toInt()
        val seconds = ((timeLeftInMillis % 60000) / 1000).toInt()

        val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)
        timerTextView.text = timeLeftFormatted
    }
}