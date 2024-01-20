package com.example.didactic_app

import android.content.Intent
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
import com.example.didactic_app.enums.Lugar
import com.example.didactic_app.utilis.Utils


/**
 * Clase que representa la actividad de cocinar.
 */
class CocinarActivity : Lanzador() {

    /**
     * Variable que representa el desplazamiento en el eje x.
     */
    private var xDelta: Int = 0

    /**
     * Variable que representa el desplazamiento en el eje y.
     */
    private var yDelta: Int = 0

    /**
     * Variable que indica si la sardina ha sido cocinada.
     */
    private var cocinada: Boolean = false

    /**
     * Layout principal de la actividad.
     */
    private lateinit var mainLayout: ViewGroup

    /**
     * ImageView que representa la sardina.
     */
    private lateinit var sardina: ImageView

    /**
     * ImageView que representa la parrilla.
     */
    private lateinit var parrilla: ImageView

    /**
     * Vista de referencia para la parrilla.
     */
    private lateinit var vReferenciaParrilla: View

    /**
     * Vista de referencia para el plato.
     */
    private lateinit var vReferenciaPlato: View

    /**
     * TextView que muestra el temporizador.
     */
    private lateinit var tvTemporizador: TextView

    /**
     * TextView que muestra las instrucciones.
     */
    private lateinit var tvInstrucciones: TextView

    /**
     * Método que se llama al crear la actividad.
     */
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
                        Utils.anadirSuperado(applicationContext, Lugar.MUSEO)
                        tvInstrucciones.setText("Bikain!")
                        lanzarJuego(arrayOf(
                            resources.getText(R.string.sardina_y_puzzle).toString(),
                        ), intArrayOf(
                            R.drawable.sardinaypuzzle
                        ), R.raw.sardina_y_puzzle_sarejosle, Intent(applicationContext, MainActivity::class.java)
                        )
                    }
                }

                MotionEvent.ACTION_MOVE -> {
                    val layoutParams = v.layoutParams as RelativeLayout.LayoutParams
                    if (x - xDelta >= 0 && x - xDelta + layoutParams.width <= mainLayout.width) {
                        sardina.x = x - xDelta.toFloat()
                    }
                    if (y - yDelta >= 0 && y - yDelta + layoutParams.height <= mainLayout.height) {
                        sardina.y = y - yDelta.toFloat()
                    }
                    v.layoutParams = layoutParams
                }

                else -> {}
            }
            mainLayout.invalidate()
            parrilla.invalidate()
            true
        }
    }

    /**
     * Método que verifica si la sardina está sobre la parrilla.
     */
    fun estaSobreLaParrilla(): Boolean {
        return intersecta(vReferenciaParrilla)
    }

    /**
     * Método que verifica si la sardina está sobre el plato.
     */
    fun estaSobreElPlato(): Boolean {
        return intersecta(vReferenciaPlato)
    }

    /**
     * Método que verifica si dos vistas se intersectan.
     */
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

    /**
     * Método que realiza la animación de la flama.
     */
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