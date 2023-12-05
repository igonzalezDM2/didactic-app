package com.example.didactic_app

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class Puzzle3x2Activity : AppCompatActivity() {
    private lateinit var puzzleZone: ViewGroup
    private lateinit var marcoZone: ViewGroup

    private lateinit var imagen1: ImageView
    private lateinit var imagen2: ImageView
    private lateinit var imagen3: ImageView
    private lateinit var imagen4: ImageView
    private lateinit var imagen5: ImageView
    private lateinit var imagen6: ImageView

    private lateinit var marco1: ImageView
    private lateinit var marco2: ImageView
    private lateinit var marco3: ImageView
    private lateinit var marco4: ImageView
    private lateinit var marco5: ImageView
    private lateinit var marco6: ImageView

    private var draggedImage: ImageView? = null

    private var bien1: Boolean = false
    private var bien2: Boolean = false
    private var bien3: Boolean = false
    private var bien4: Boolean = false
    private var bien5: Boolean = false
    private var bien6: Boolean = false

    private lateinit var tvRespuesta: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puzzle3x2)

        puzzleZone = findViewById(R.id.puzzleZone)
        marcoZone = findViewById(R.id.marcoZone)

        imagen1 = findViewById(R.id.imagen1)
        imagen2 = findViewById(R.id.imagen2)
        imagen3 = findViewById(R.id.imagen3)
        imagen4 = findViewById(R.id.imagen4)
        imagen5 = findViewById(R.id.imagen5)
        imagen6 = findViewById(R.id.imagen6)

        marco1 = findViewById(R.id.marco1)
        marco2 = findViewById(R.id.marco2)
        marco3 = findViewById(R.id.marco3)
        marco4 = findViewById(R.id.marco4)
        marco5 = findViewById(R.id.marco5)
        marco6 = findViewById(R.id.marco6)

        tvRespuesta = findViewById(R.id.tvRespuesta)

        // Configurar listeners de arrastrar y soltar para las imágenes del puzzle
        imagen1.setOnTouchListener(TouchListener())
        imagen2.setOnTouchListener(TouchListener())
        imagen3.setOnTouchListener(TouchListener())
        imagen4.setOnTouchListener(TouchListener())
        imagen5.setOnTouchListener(TouchListener())
        imagen6.setOnTouchListener(TouchListener())

        marco1.setOnDragListener(DragListener())
        marco2.setOnDragListener(DragListener())
        marco3.setOnDragListener(DragListener())
        marco4.setOnDragListener(DragListener())
        marco5.setOnDragListener(DragListener())
        marco6.setOnDragListener(DragListener())
    }

    private inner class TouchListener : View.OnTouchListener {
        override fun onTouch(view: View, event: MotionEvent): Boolean {
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Guardar la información de la imagen arrastrada y su posición inicial
                draggedImage = view as ImageView
                val clipData = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(view)
                view.startDrag(clipData, shadowBuilder, view, 0)
                return true
            }
            return false
        }
    }

    private inner class DragListener : View.OnDragListener {
        override fun onDrag(view: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DROP -> {
                    // Verificar si la imagen se soltó en un marco correcto
                    val droppedImage = event.localState as ImageView
                    if (view == marco1 && droppedImage == imagen1) {
                        marco1.setImageDrawable(droppedImage.drawable)
                        imagen1.visibility = View.INVISIBLE
                        bien1 = true
                    } else if (view == marco2 && droppedImage == imagen2) {
                        marco2.setImageDrawable(droppedImage.drawable)
                        imagen2.visibility = View.INVISIBLE
                        bien2 = true
                    } else if (view == marco3 && droppedImage == imagen3) {
                        marco3.setImageDrawable(droppedImage.drawable)
                        imagen3.visibility = View.INVISIBLE
                        bien3 = true
                    } else if (view == marco4 && droppedImage == imagen4) {
                        marco4.setImageDrawable(droppedImage.drawable)
                        imagen4.visibility = View.INVISIBLE
                        bien4 = true
                    } else if (view == marco5 && droppedImage == imagen5) {
                        marco5.setImageDrawable(droppedImage.drawable)
                        imagen5.visibility = View.INVISIBLE
                        bien5 = true
                    } else if (view == marco6 && droppedImage == imagen6) {
                        marco6.setImageDrawable(droppedImage.drawable)
                        imagen6.visibility = View.INVISIBLE
                        bien6 = true
                    } else {
                        // Volver a colocar la imagen en su posición inicial
                        val layoutParams = droppedImage.layoutParams as ViewGroup.MarginLayoutParams
                        layoutParams.leftMargin = 0
                        layoutParams.topMargin = 0
                        droppedImage.layoutParams = layoutParams
                    }
                    if (bien1 && bien2 && bien3 && bien4 && bien5 && bien6){
                        tvRespuesta.setText("Zorionak puzzlea egin duzu!")
                    }
                }
            }
            return true
        }
    }
}