package com.example.didactic_app

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class AtraparSardinasActivity : AppCompatActivity() {
    private lateinit var tvCantidadSardinas: TextView
    private lateinit var tvTiempo: TextView
    private lateinit var tvTemporizadorInicio: TextView

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

        val layout = findViewById<LinearLayout>(R.id.lyPrincipal)

        var temporizadorInicio = TemporizadorComponent(tvTemporizadorInicio,
            5000)
        var temporizadorJuego = TemporizadorComponent(tvTiempo,30000)
        temporizadorJuego.onTick = {
            val imagePez = ImageView(this)
            imagePez.setImageResource(R.drawable.sardina_atrapar_sardinas)
            imagePez.setOnTouchListener { v, event -> clickSardina(v, event) }

            imagePez.layoutParams = LinearLayout.LayoutParams(250,250)
            layout.addView(imagePez)
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

    private fun clickSardina(v: View, event: MotionEvent):Boolean{
        // Obtiene las coordenadas del toque
        val x = event.x.toInt()
        val y = event.y.toInt()

        // Obtiene el ImageView y su bitmap
        val imageView = v as ImageView
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        // Asegúrate de que las coordenadas estén dentro de la imagen
        if (x < 0 || y < 0 || x >= bitmap.width || y >= bitmap.height) {
            return false
        }
        // Obtiene el color del píxel tocado
        val touchedRGB = bitmap.getPixel(x, y)

        // Comprueba si el píxel es transparente
        if (touchedRGB == Color.TRANSPARENT) {
            return false
        } else { // Aquí puedes manejar el evento de toque en una parte no transparente de la imagen
            imageView.visibility = View.GONE
            return true
        }
    }

}