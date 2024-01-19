package com.example.didactic_app

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.didactic_app.enums.Lugar
import com.example.didactic_app.utilis.Utils

class Puzzle4x2Activity : Lanzador() {
    private lateinit var puzzleZone: ViewGroup
    private lateinit var marcoZone: ViewGroup

    private lateinit var puzzle4x2p1: ImageView
    private lateinit var puzzle4x2p2: ImageView
    private lateinit var puzzle4x2p3: ImageView
    private lateinit var puzzle4x2p4: ImageView
    private lateinit var puzzle4x2p5: ImageView
    private lateinit var puzzle4x2p6: ImageView
    private lateinit var puzzle4x2p7: ImageView
    private lateinit var puzzle4x2p8: ImageView

    private lateinit var marco1: ImageView
    private lateinit var marco2: ImageView
    private lateinit var marco3: ImageView
    private lateinit var marco4: ImageView
    private lateinit var marco5: ImageView
    private lateinit var marco6: ImageView
    private lateinit var marco7: ImageView
    private lateinit var marco8: ImageView

    private var draggedImage: ImageView? = null

    private var bien1: Boolean = false
    private var bien2: Boolean = false
    private var bien3: Boolean = false
    private var bien4: Boolean = false
    private var bien5: Boolean = false
    private var bien6: Boolean = false
    private var bien7: Boolean = false
    private var bien8: Boolean = false

    private lateinit var tvRespuesta: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puzzle4x2)

        puzzleZone = findViewById(R.id.puzzleZone)
        marcoZone = findViewById(R.id.marcoZone)

        puzzle4x2p1 = findViewById(R.id.puzzle4x2p1)
        puzzle4x2p2 = findViewById(R.id.puzzle4x2p2)
        puzzle4x2p3 = findViewById(R.id.puzzle4x2p3)
        puzzle4x2p4 = findViewById(R.id.puzzle4x2p4)
        puzzle4x2p5 = findViewById(R.id.puzzle4x2p5)
        puzzle4x2p6 = findViewById(R.id.puzzle4x2p6)
        puzzle4x2p7 = findViewById(R.id.puzzle4x2p7)
        puzzle4x2p8 = findViewById(R.id.puzzle4x2p8)

        marco1 = findViewById(R.id.marco1)
        marco2 = findViewById(R.id.marco2)
        marco3 = findViewById(R.id.marco3)
        marco4 = findViewById(R.id.marco4)
        marco5 = findViewById(R.id.marco5)
        marco6 = findViewById(R.id.marco6)
        marco7 = findViewById(R.id.marco7)
        marco8 = findViewById(R.id.marco8)

        tvRespuesta = findViewById(R.id.tvRespuesta)

        // Configurar listeners de arrastrar y soltar para las imágenes del puzzle
        puzzle4x2p1.setOnTouchListener(TouchListener())
        puzzle4x2p2.setOnTouchListener(TouchListener())
        puzzle4x2p3.setOnTouchListener(TouchListener())
        puzzle4x2p4.setOnTouchListener(TouchListener())
        puzzle4x2p5.setOnTouchListener(TouchListener())
        puzzle4x2p6.setOnTouchListener(TouchListener())
        puzzle4x2p7.setOnTouchListener(TouchListener())
        puzzle4x2p8.setOnTouchListener(TouchListener())

        marco1.setOnDragListener(DragListener())
        marco2.setOnDragListener(DragListener())
        marco3.setOnDragListener(DragListener())
        marco4.setOnDragListener(DragListener())
        marco5.setOnDragListener(DragListener())
        marco6.setOnDragListener(DragListener())
        marco7.setOnDragListener(DragListener())
        marco8.setOnDragListener(DragListener())
    }

    private inner class TouchListener : View.OnTouchListener {
        override fun onTouch(view: View, event: MotionEvent): Boolean {
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Guardar la información de la puzzle3x2p arrastrada y su posición inicial
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
                    // Verificar si la puzzle3x2p se soltó en un marco correcto
                    val droppedImage = event.localState as ImageView
                    if (view == marco1 && droppedImage == puzzle4x2p1) {
                        marco1.setImageDrawable(droppedImage.drawable)
                        puzzle4x2p1.visibility = View.INVISIBLE
                        bien1 = true
                    } else if (view == marco2 && droppedImage == puzzle4x2p2) {
                        marco2.setImageDrawable(droppedImage.drawable)
                        puzzle4x2p2.visibility = View.INVISIBLE
                        bien2 = true
                    } else if (view == marco3 && droppedImage == puzzle4x2p3) {
                        marco3.setImageDrawable(droppedImage.drawable)
                        puzzle4x2p3.visibility = View.INVISIBLE
                        bien3 = true
                    } else if (view == marco4 && droppedImage == puzzle4x2p4) {
                        marco4.setImageDrawable(droppedImage.drawable)
                        puzzle4x2p4.visibility = View.INVISIBLE
                        bien4 = true
                    } else if (view == marco5 && droppedImage == puzzle4x2p5) {
                        marco5.setImageDrawable(droppedImage.drawable)
                        puzzle4x2p5.visibility = View.INVISIBLE
                        bien5 = true
                    } else if (view == marco6 && droppedImage == puzzle4x2p6) {
                        marco6.setImageDrawable(droppedImage.drawable)
                        puzzle4x2p6.visibility = View.INVISIBLE
                        bien6 = true
                    } else if (view == marco7 && droppedImage == puzzle4x2p7) {
                        marco7.setImageDrawable(droppedImage.drawable)
                        puzzle4x2p7.visibility = View.INVISIBLE
                        bien7 = true
                    } else if (view == marco8 && droppedImage == puzzle4x2p8) {
                        marco8.setImageDrawable(droppedImage.drawable)
                        puzzle4x2p8.visibility = View.INVISIBLE
                        bien8 = true
                    } else {
                        // Volver a colocar la puzzle3x2p en su posición inicial
                        val layoutParams = droppedImage.layoutParams as ViewGroup.MarginLayoutParams
                        layoutParams.leftMargin = 0
                        layoutParams.topMargin = 0
                        droppedImage.layoutParams = layoutParams
                    }
                    if (bien1 && bien2 && bien3 && bien4 && bien5 && bien6 && bien7 && bien8){
                        tvRespuesta.setText("Zorionak puzzlea egin duzu!")
                        Utils.anadirSuperado(applicationContext, Lugar.AYUNTAMIENTO)
                        lanzarJuego(arrayOf(
                            resources.getText(R.string.explicacion_azkena_del_todo).toString(),
                        ), intArrayOf(
                        ), R.raw.amaiera_del_todo, Intent(applicationContext, MainActivity::class.java)
                        )

                    }
                }
            }
            return true
        }
    }
}