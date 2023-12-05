package com.example.didactic_app

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

/**
 * Ventana que representa el juego de verdadero a falso, pero en vez de esos nombres serian entre
 * 2 puertos.
 * @author Sergio Daniel Groppa
 */
class VerdaderoFalsoActivity : AppCompatActivity() {
    private lateinit var tvEnunciado: TextView
    private lateinit var tvNivel: TextView
    private lateinit var tvRespuesta: TextView
    private lateinit var ivBarco: ImageView
    private lateinit var btnSanturtzi: Button
    private lateinit var btnBilbao: Button

    private var nivel = 0
    private var preguntasAcertadas = 0

    private var lstPregunta: List<Pregunta> = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verdadero_falso)
        //Carga la lista de preguntas
        lstPregunta = listOf(
            Pregunta(getString(R.string.primera_pregunta), getString(R.string.button_santurtzi)),
            Pregunta(getString(R.string.segunda_pregunta), getString(R.string.button_bilbo)),
            Pregunta(getString(R.string.tercera_pregunta), getString(R.string.button_bilbo)),
            Pregunta(getString(R.string.cuarta_pregunta), getString(R.string.button_santurtzi)),
            Pregunta(getString(R.string.quinta_pregunta), getString(R.string.button_santurtzi)),
            Pregunta(getString(R.string.sexto_pregunta), getString(R.string.button_bilbo)),
        )
        //Consigue los elementos de la ventana
        tvEnunciado = findViewById(R.id.tvEnunciado)
        tvNivel = findViewById(R.id.tvNivel)
        tvRespuesta = findViewById(R.id.tvRespuesta)
        ivBarco = findViewById(R.id.ivBarco)
        btnSanturtzi = findViewById(R.id.btnSanturtzi)
        btnBilbao = findViewById(R.id.btnBilbao)

        //Inicializa el estado inicial de la ventana
        subirNivel()
        //Y asigna los eventos a los botones
        btnSanturtzi.setOnClickListener { evaluar(btnSanturtzi)  }
        btnBilbao.setOnClickListener { evaluar(btnBilbao) }
    }

    /**
     * Cuando se sube el nivel, se cambia la pregunta, la imagen del barcos y el nivel en el
     * que esta
     */
    private fun subirNivel(){
        tvEnunciado.text = lstPregunta[nivel].enunciado
        nivel += 1
        cambiarImagen()
        tvNivel.text = nivel.toString().plus("/6")
    }

    /**
     * Metodo que dependiendo del nivel cambia la imagen
     */
    private fun cambiarImagen(){
        when(nivel){
            1 -> ivBarco.setImageResource(R.drawable.itsasontzi_argazkia_1)
            2 -> ivBarco.setImageResource(R.drawable.itsasontzi_argazkia_2)
            3 -> ivBarco.setImageResource(R.drawable.itsasontzi_argazkia_3)
            4 -> ivBarco.setImageResource(R.drawable.itsasontzi_argazkia_4)
            5 -> ivBarco.setImageResource(R.drawable.itsasontzi_argazkia_5)
            6 -> ivBarco.setImageResource(R.drawable.itsasontzi_argazkia_6)
        }
    }

    /**
     * Evalua la respuesta mediante el boton que le a dado.
     * @param btn Boton que selecciona
     */
    private fun evaluar(btn : Button){
        if(lstPregunta[nivel].respuestaValida(btn.text.toString())){
            preguntasAcertadas += 1
            tvRespuesta.setTextColor(Color.GREEN)
            tvRespuesta.text = getString(R.string.respuesta_correcta)
        }else{
            tvRespuesta.setTextColor(Color.RED)
            tvRespuesta.text = getString(R.string.respuesta_incorrecta)
        }
        subirNivel()
    }

}