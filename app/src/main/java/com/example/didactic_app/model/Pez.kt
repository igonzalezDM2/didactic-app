package com.example.didactic_app.model

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.didactic_app.R
import com.google.android.material.internal.BaselineLayout

/**
 * Clase para representar un pez en la aplicación.
 */
class Pez {

    companion object {
        /**
         * Cantidad de peces actualmente en la pantalla.
         */
        private var cantPeces: Int = 0
        /**
         * Máximo de peces permitidos en la pantalla.
         */
        private const val maxPeces = 15
        /**
         * Separación entre los peces en la pantalla.
         */
        private var separacion: Float = 250f
        /**
         * Altura de la pantalla.
         */
        private var screenHeight: Float = 0f
        /**
         * Ancho de la pantalla.
         */
        private var screenWidth: Float = 0f
        /**
         * Ancho del pez.
         */
        private var widthPez:Int = 250
    }

    private var ivPez:ImageView
    val imagen: ImageView
        get() {
            return ivPez
        }

    private var rigthToLeft: Boolean = false
    private var puntoInicio: Float = 0f

    /**
     * Constructor de la clase Pez.
     *
     * @param activity la actividad actual
     * @param layout el layout donde se mostrará el pez
     * @param direccion la dirección del movimiento del pez
     */
    constructor(activity: AppCompatActivity, layout: ConstraintLayout, direccion:Boolean = false) {
        this.rigthToLeft = direccion
        if(maxPeces > cantPeces)
            cantPeces += 1
        else
            throw Exception()
        ivPez = ImageView(activity)
        ivPez.isClickable = true
        ivPez.setImageResource(R.drawable.sardina_atrapar_sardinas)
        ivPez.translationY = (250..450).random() + separacion
        if((screenHeight - 350) <= ivPez.translationY) {
            Log.d("HEIGHT", screenHeight.toString())
            separacion = 250f
        }else
            separacion += 250

        screenHeight = activity.resources.displayMetrics.heightPixels.toFloat()
        screenWidth = activity.resources.displayMetrics.widthPixels.toFloat()

        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        if (rigthToLeft) {
            layoutParams.endToEnd = layout.id
            layoutParams.topToTop = layout.id
        }else { // Invertir la imagen horizontalmente
            ivPez.scaleX = -1f
        }
        layoutParams.width = widthPez
        layoutParams.height = 250
        ivPez.visibility = View.INVISIBLE
        layout.addView(ivPez, layoutParams)
    }

    /**
     * Inicia la animación del pez.
     *
     * @param desplazamiento el desplazamiento del pez
     * @param duracionMovimiento la duración del movimiento del pez
     * @param duracionFinal la duración de la animación final del pez
     * @param layout el layout donde se mostrará el pez
     * @param event la función a ejecutar al hacer clic en el pez
     */
    fun startAnimation(desplazamiento:Int, duracionMovimiento:Long, duracionFinal:Long, layout: ConstraintLayout
                       , event: () -> Unit){

        val startX = if (rigthToLeft) widthPez.toFloat() else -widthPez.toFloat()
        val endX = if (rigthToLeft) -screenWidth else screenWidth

        val animacionMover = ObjectAnimator.ofFloat(ivPez,"translationX", startX, endX)

        animacionMover.interpolator = AccelerateDecelerateInterpolator()
        animacionMover.duration = duracionMovimiento

        val animacionFinal = ValueAnimator.ofFloat(1f, 0f)
        animacionFinal.addUpdateListener { animation ->
            val factor = animation.animatedValue as Float
            ivPez.alpha = factor
            cantPeces -= 1
            ivPez.isClickable = false
            ivPez.visibility = View.GONE
            layout.removeView(ivPez)
        }
        animacionFinal.duration = duracionFinal
        animacionFinal.startDelay = 150

        val animacionConjunta = AnimatorSet()
        animacionConjunta.playSequentially(animacionMover)

        imagen.setOnClickListener {
            event()
            animacionFinal.start()
        }

        imagen.post {
            animacionConjunta.start()
            ivPez.visibility = View.VISIBLE
        }
    }

}