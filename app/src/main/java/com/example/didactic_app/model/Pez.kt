package com.example.didactic_app.model

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator

import android.util.Log

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity

import androidx.constraintlayout.widget.ConstraintLayout

import com.example.didactic_app.R

/**
 * Animacion para la sardina en Atrapar Sardina
 */
class Pez
    (activity: AppCompatActivity, layout: ConstraintLayout, direccion: Boolean = false) {

    /**
     * Constantes de la sardina
     */
    companion object {
        private var cantPeces: Int = 0
        private const val maxPeces = 15
        private var separacion: Float = 250f
        private var screenHeight: Float = 0f
        private var screenWidth: Float = 0f
        private var widthPez:Int = 250
    }

    private var ivPez:ImageView
    val imagen: ImageView
        get() {
            return ivPez
        }

    private var rigthToLeft: Boolean = direccion

    init {
        //Si la cantidad de peces esta bien deja generar un pez en caso contrario no
        if(maxPeces > cantPeces)
            cantPeces += 1
        else
            throw Exception()

        //Creamos la imagen y añadimos sus caracteristicas
        ivPez = ImageView(activity)
        ivPez.isClickable = true
        ivPez.setImageResource(R.drawable.sardina_atrapar_sardinas)
        ivPez.translationY = (250..450).random() + separacion

        screenHeight = activity.resources.displayMetrics.heightPixels.toFloat()
        screenWidth = activity.resources.displayMetrics.widthPixels.toFloat()
        if((screenHeight - 350) <= ivPez.translationY)
            separacion = 250f
        else
            separacion += 250f

        // El layout para la sardina
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.width = widthPez
        layoutParams.height = 250

        //Mira si esta a la derecha
        if (rigthToLeft) {
            layoutParams.endToEnd = layout.id
            layoutParams.topToTop = layout.id
        }else  // Invertir la imagen horizontalmente
            ivPez.scaleX = -1f
        ivPez.visibility = View.INVISIBLE

        //Añadimos al layout principal
        layout.addView(ivPez, layoutParams)
    }

    /**
     * Animacion de la sardina
     */
    fun startAnimation(duracionMovimiento:Long, duracionFinal:Long, layout: ConstraintLayout
                       , event: () -> Unit){
        //Donde empieza y termina la sardina
        val startX = if (rigthToLeft) widthPez.toFloat() else -widthPez.toFloat()
        val endX = if (rigthToLeft) -screenWidth else screenWidth

        //Animacion del movimiento
        val animacionMover = ObjectAnimator.ofFloat(ivPez,"translationX", startX, endX)
        animacionMover.interpolator = AccelerateDecelerateInterpolator()
        animacionMover.duration = duracionMovimiento

        //Animacion al final
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
        animacionConjunta.playSequentially(animacionMover, animacionFinal)

        //Al darle click a la sardina
        imagen.setOnClickListener {
            event()
            animacionFinal.start()
        }

        //Inicia la animacion y visibiliza la sardina
        imagen.post {
            animacionConjunta.start()
            ivPez.visibility = View.VISIBLE
        }
    }

}