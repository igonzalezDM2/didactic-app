package com.example.didactic_app

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class CocinarActivity : AppCompatActivity() {

    private var xDelta: Int = 0
    private var yDelta: Int = 0
    private var cocinada: Boolean = false
    private lateinit var mainLayout: ViewGroup
    private lateinit var sardina: ImageView
    private lateinit var parrilla: ImageView
    private lateinit var vReferenciaParrilla: View
    private lateinit var vReferenciaPlato: View
    private lateinit var tvTemporizador: TextView
    private lateinit var tvInstrucciones: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocinar)

        mainLayout = findViewById(R.id.main)

        sardina = findViewById(R.id.sardina1)
        parrilla = findViewById(R.id.parrilla)
        vReferenciaParrilla = findViewById(R.id.tvReferencia)
        vReferenciaPlato = findViewById(R.id.vReferenciaPlato)
        tvTemporizador = findViewById(R.id.tvTemporizador)
        tvInstrucciones = findViewById(R.id.tvInstrucciones)

        tvTemporizador.visibility = View.INVISIBLE
        var temporizador = TemporizadorComponent(tvTemporizador, 10000)

        temporizador.onFinish = {
            sardina.setImageResource(R.drawable.sardina_cocinada)
            parrilla.setImageResource(R.drawable.parrilla_vacia)
            cocinada = true
            tvTemporizador.visibility = View.INVISIBLE
            tvInstrucciones.setText("Orain platerrara!")
//            animacionFlama()
        }


        sardina.setOnTouchListener { v, event ->
            val x = event.rawX.toInt()
            val y = event.rawY.toInt()

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    temporizador.stopTimer()
                    parrilla.setImageResource(R.drawable.parrilla_vacia)
                    val lParams = v.layoutParams as RelativeLayout.LayoutParams
                    xDelta = x - sardina.x.toInt()
                    yDelta = y - sardina.y.toInt()
                }

                MotionEvent.ACTION_UP -> {


                    if (!cocinada and estaSobreLaParrilla()) {
                        parrilla.setImageResource(R.drawable.parrilla)
                        tvTemporizador.visibility = View.VISIBLE
                        tvInstrucciones.setText("Itxaron, mesedez...")
                        temporizador.startTimer()

                    } else if (cocinada and estaSobreElPlato()) {
                        tvInstrucciones.setText("Bikain!")
                    }

                }

                MotionEvent.ACTION_MOVE -> {
                    val layoutParams = v.layoutParams as RelativeLayout.LayoutParams

                    // Evitar que la ImageView se haga más pequeña al arrastrar hacia los bordes
                    if (x - xDelta >= 0 && x - xDelta + layoutParams.width <= mainLayout.width) {
                        sardina.x = x - xDelta.toFloat()
                    }

                    if (y - yDelta >= 0 && y - yDelta + layoutParams.height <= mainLayout.height) {
                        sardina.y = y - yDelta.toFloat()
                    }


//                    layoutParams.rightMargin = 0
//                    layoutParams.bottomMargin = 0

                    v.layoutParams = layoutParams
                }

                else -> {}
            }
            mainLayout.invalidate()
            parrilla.invalidate()
            true
        }

    }

    fun estaSobreLaParrilla(): Boolean {
        return intersecta(vReferenciaParrilla)
    }

    fun estaSobreElPlato(): Boolean {
        return intersecta(vReferenciaPlato)
    }
    fun intersecta(referencia: View): Boolean {
        val firstPosition = IntArray(2)
        val secondPosition = IntArray(2)

        referencia.getLocationOnScreen((firstPosition))
        sardina.getLocationOnScreen((secondPosition))

        val rectFirstView = Rect(
            firstPosition[0],
            firstPosition[1],
            firstPosition[0] + referencia.getMeasuredWidth(),
            firstPosition[1] + referencia.getMeasuredHeight()
        )
        val rectSecondView = Rect(
            secondPosition[0],
            secondPosition[1],
            secondPosition[0] + sardina.getMeasuredWidth(),
            secondPosition[1] + sardina.getMeasuredHeight()
        )
        return rectFirstView.intersect(rectSecondView)
    }

    fun animacionFlama(): Unit {


        val rotate = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotate.duration = 500
        rotate.interpolator = LinearInterpolator()

        val image = findViewById<View>(R.id.sardina1) as ImageView

        image.startAnimation(rotate)

    }


}