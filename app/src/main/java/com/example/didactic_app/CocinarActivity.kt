package com.example.didactic_app

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class CocinarActivity : AppCompatActivity() {

    private var xDelta: Int = 0
    private var yDelta: Int = 0
    private lateinit var mainLayout: ViewGroup
    private lateinit var sardina: ImageView
    private lateinit var parrilla: ImageView
    private lateinit var tvReferencia: View



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocinar)

        mainLayout = findViewById(R.id.main)

        sardina = findViewById(R.id.sardina1)
        parrilla = findViewById(R.id.parrilla)
        tvReferencia = findViewById(R.id.tvReferencia)

        sardina.setOnTouchListener { v, event ->
            val x = event.rawX.toInt()
            val y = event.rawY.toInt()

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val lParams = v.layoutParams as RelativeLayout.LayoutParams
                    xDelta = x - lParams.leftMargin
                    yDelta = y - lParams.topMargin
                }

                MotionEvent.ACTION_UP -> {


                    var sobre = estaSobreLaParrilla()
                    Toast.makeText(this, if (sobre)  "Intersecta" else "Pendejo", Toast.LENGTH_SHORT).show()




                }

                MotionEvent.ACTION_MOVE -> {

                    val layoutParams = v.layoutParams as RelativeLayout.LayoutParams

                    // Evitar que la ImageView se haga más pequeña al arrastrar hacia los bordes
                    if (x - xDelta >= 0 && x - xDelta + layoutParams.width <= mainLayout.width) {
                        layoutParams.leftMargin = x - xDelta
                    }

                    if (y - yDelta >= 0 && y - yDelta + layoutParams.height <= mainLayout.height) {
                        layoutParams.topMargin = y - yDelta
                    }

                    layoutParams.rightMargin = 0
                    layoutParams.bottomMargin = 0

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
        val firstPosition = IntArray(2)
        val secondPosition = IntArray(2)

        tvReferencia.getLocationOnScreen((firstPosition))
        sardina.getLocationOnScreen((secondPosition))

        val rectFirstView = Rect(
            firstPosition[0],
            firstPosition[1],
            firstPosition[0] + tvReferencia.getMeasuredWidth(),
            firstPosition[1] + tvReferencia.getMeasuredHeight()
        )
        val rectSecondView = Rect(
            secondPosition[0],
            secondPosition[1],
            secondPosition[0] + sardina.getMeasuredWidth(),
            secondPosition[1] + sardina.getMeasuredHeight()
        )
        return rectFirstView.intersect(rectSecondView)
    }

}