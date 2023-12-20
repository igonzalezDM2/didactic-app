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

class TangramActivity : AppCompatActivity() {
    private lateinit var puzzleZone: ViewGroup
    private lateinit var marcoZone: ViewGroup

    private lateinit var tangram1: ImageView
    private lateinit var tangram2: ImageView
    private lateinit var tangram3: ImageView
    private lateinit var tangram4: ImageView
    private lateinit var tangram5: ImageView
    private lateinit var tangram6: ImageView
    private lateinit var tangram7: ImageView

    private lateinit var marco1: ImageView
    private lateinit var marco2: ImageView
    private lateinit var marco3: ImageView
    private lateinit var marco4: ImageView
    private lateinit var marco5: ImageView
    private lateinit var marco6: ImageView
    private lateinit var marco7: ImageView

    private var draggedImage: ImageView? = null

    private var bien1: Boolean = false
    private var bien2: Boolean = false
    private var bien3: Boolean = false
    private var bien4: Boolean = false
    private var bien5: Boolean = false
    private var bien6: Boolean = false
    private var bien7: Boolean = false

    private lateinit var tvRespuesta: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tangram)

        puzzleZone = findViewById(R.id.puzzleZone)
        marcoZone = findViewById(R.id.marcoZone)

        tangram1 = findViewById(R.id.tangram1)
        tangram2 = findViewById(R.id.tangram2)
        tangram3 = findViewById(R.id.tangram3)
        tangram4 = findViewById(R.id.tangram4)
        tangram5 = findViewById(R.id.tangram5)
        tangram6 = findViewById(R.id.tangram6)
        tangram7 = findViewById(R.id.tangram7)

        marco1 = findViewById(R.id.marco1)
        marco2 = findViewById(R.id.marco2)
        marco3 = findViewById(R.id.marco3)
        marco4 = findViewById(R.id.marco4)
        marco5 = findViewById(R.id.marco5)
        marco6 = findViewById(R.id.marco6)
        marco7 = findViewById(R.id.marco7)

        tvRespuesta = findViewById(R.id.tvRespuesta)

        // Configurar listeners de arrastrar y soltar para las imágenes del puzzle
        tangram1.setOnTouchListener(TouchListener())
        tangram2.setOnTouchListener(TouchListener())
        tangram3.setOnTouchListener(TouchListener())
        tangram4.setOnTouchListener(TouchListener())
        tangram5.setOnTouchListener(TouchListener())
        tangram6.setOnTouchListener(TouchListener())
        tangram7.setOnTouchListener(TouchListener())

        marco1.setOnDragListener(DragListener())
        marco2.setOnDragListener(DragListener())
        marco3.setOnDragListener(DragListener())
        marco4.setOnDragListener(DragListener())
        marco5.setOnDragListener(DragListener())
        marco6.setOnDragListener(DragListener())
        marco7.setOnDragListener(DragListener())
    }

    private inner class TouchListener : View.OnTouchListener {
        override fun onTouch(view: View, event: MotionEvent): Boolean {
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Guardar la información de la tangram arrastrada y su posición inicial
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
                    // Verificar si la tangram se soltó en un marco correcto
                    val droppedImage = event.localState as ImageView
                    if (view == marco1 && droppedImage == tangram1) {
                        marco1.setImageDrawable(droppedImage.drawable)
                        tangram1.visibility = View.INVISIBLE
                        bien1 = true
                    } else if (view == marco2 && droppedImage == tangram2) {
                        marco2.setImageDrawable(droppedImage.drawable)
                        tangram2.visibility = View.INVISIBLE
                        bien2 = true
                    } else if (view == marco3 && droppedImage == tangram3) {
                        marco3.setImageDrawable(droppedImage.drawable)
                        tangram3.visibility = View.INVISIBLE
                        bien3 = true
                    } else if (view == marco4 && droppedImage == tangram4) {
                        marco4.setImageDrawable(droppedImage.drawable)
                        tangram4.visibility = View.INVISIBLE
                        bien4 = true
                    } else if (view == marco5 && droppedImage == tangram5) {
                        marco5.setImageDrawable(droppedImage.drawable)
                        tangram5.visibility = View.INVISIBLE
                        bien5 = true
                    } else if (view == marco6 && droppedImage == tangram6) {
                        marco6.setImageDrawable(droppedImage.drawable)
                        tangram6.visibility = View.INVISIBLE
                        bien6 = true
                    } else if (view == marco7 && droppedImage == tangram7) {
                        marco7.setImageDrawable(droppedImage.drawable)
                        tangram7.visibility = View.INVISIBLE
                        bien7 = true
                    } else {
                        // Volver a colocar la imagen en su posición inicial
                        val layoutParams = droppedImage.layoutParams as ViewGroup.MarginLayoutParams
                        layoutParams.leftMargin = 0
                        layoutParams.topMargin = 0
                        droppedImage.layoutParams = layoutParams
                    }
                    if (bien1 && bien2 && bien3 && bien4 && bien5 && bien6 && bien7){
                        tvRespuesta.setText("Zorionak puzzlea egin duzu!")
                    }
                }
            }
            return true
        }
    }
}