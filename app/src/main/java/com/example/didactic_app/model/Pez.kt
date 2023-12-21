package com.example.didactic_app.model

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.didactic_app.R
import com.google.android.material.internal.BaselineLayout

class Pez {

    companion object {
        private var cantPeces: Int = 0
        private const val maxPeces = 15
        private var separacion: Float = 250f
        private var screenHeight: Float = 0f
        private var screenWidth: Float = 0f
        private var ultimoPezPantalla: Float = 0f
    }

    private var ivPez:ImageView
    val imagen: ImageView
        get() {
            return ivPez
        }

    private var rigthToLeft: Boolean = false

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
            ivPez.x = screenWidth + ivPez.width.toFloat()
        }else { // Invertir la imagen horizontalmente
            ivPez.scaleX = -1f
            ivPez.x = -ivPez.width.toFloat()
        }
        layoutParams.width = 250
        layoutParams.height = 250
        layout.addView(ivPez, layoutParams)
    }

    fun startAnimation(desplazamiento:Int, duracionMovimiento:Long, duracionFinal:Long
                       , event: () -> Unit){

        val animacionMover = ValueAnimator.ofFloat(0f, 1f)
        animacionMover.addUpdateListener { animation ->
            val factor = animation.animatedValue as Float
            val translationX: Float = if (rigthToLeft) {
                factor * -desplazamiento
            } else {
                factor * desplazamiento
            }
            ivPez.translationX = translationX
        }
        animacionMover.interpolator = AccelerateDecelerateInterpolator()
        animacionMover.duration = duracionMovimiento

        val animacionFinal = ValueAnimator.ofFloat(1f, 0f)
        animacionFinal.addUpdateListener { animation ->
            val factor = animation.animatedValue as Float
            ivPez.alpha = factor
            cantPeces -= 1
            ivPez.visibility = View.GONE
        }
        animacionFinal.duration = duracionFinal

        val animacionConjunta = AnimatorSet()
        animacionConjunta.playSequentially(animacionMover, animacionFinal)

        imagen.setOnClickListener { view ->
            event()
            view.visibility = View.GONE
            animacionConjunta.start()
            view.isClickable = false
            cantPeces -= 1
        }

        imagen.post {
            animacionConjunta.start()
        }
    }

}