package com.example.didactic_app

import android.os.CountDownTimer
import android.widget.TextView
class TemporizadorComponent (
    private val timerTextView: TextView,
    private val initialTimeInMillis: Long,
) {

     var onFinish: () -> Unit = {}
     var onTick: (Long) -> Unit = {}

    private lateinit var countDownTimer: CountDownTimer
    private var timeLeftInMillis: Long = initialTimeInMillis

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

    fun stopTimer() {
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }
    }

    private fun updateTimer() {
        val hours = (timeLeftInMillis / 3600000).toInt()
        val minutes = ((timeLeftInMillis % 3600000) / 60000).toInt()
        val seconds = ((timeLeftInMillis % 60000) / 1000).toInt()

        val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)
        timerTextView.text = timeLeftFormatted
    }
}