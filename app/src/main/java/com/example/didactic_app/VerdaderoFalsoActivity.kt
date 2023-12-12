package com.example.didactic_app

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle

import android.view.View

import android.widget.Button
import android.widget.ImageButton
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
    private lateinit var tvNuevoIntento: TextView
    private lateinit var ivBarco: ImageView
    private lateinit var btnSanturtzi: Button
    private lateinit var btnBilbao: Button
    private lateinit var btnNuevoIntento: ImageButton
    private lateinit var btnSiguiente: ImageButton
    private lateinit var vBubble: View

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
        vBubble = findViewById(R.id.vBubble)
        btnSanturtzi = findViewById(R.id.btnSanturtzi)
        btnBilbao = findViewById(R.id.btnBilbao)
        tvNuevoIntento = findViewById(R.id.tvNuevoIntento)
        btnNuevoIntento = findViewById(R.id.btnNuevoIntento)
        btnSiguiente = findViewById(R.id.btnSiguiente)

        //Inicializa el estado inicial de la ventana
        subirNivel()
        //Y asigna los eventos a los botones
        btnSanturtzi.setOnClickListener { evaluar(btnSanturtzi)  }
        btnBilbao.setOnClickListener { evaluar(btnBilbao) }
        btnNuevoIntento.setOnClickListener { reiniciarJuego() }
        btnSiguiente.setOnClickListener { finish() }
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
        if(lstPregunta[nivel-1].respuestaValida(btn.text.toString()))
            preguntasAcertadas += 1
        if(nivel == 6){
            seTerminaJuego()
            return
        }
        subirNivel()
    }

    /**
     * Cuando se termina el juego oculta todos los elementos comunen del juego, se mostraran los
     * resultados y el boton para seguir, en caso de no se acertaran todas las pregutas tambien se
     * visualiza el boton para reiniciar el juego
     */
    private fun seTerminaJuego(){
        // Se ocultan los elementos
        btnSanturtzi.visibility = View.GONE
        btnBilbao.visibility = View.GONE
        ivBarco.visibility = View.GONE
        tvEnunciado.visibility = View.GONE
        vBubble.visibility = View.GONE
        //Se visualiza el boton de nuevo intento y le muestra la cantidad acertada
        if(preguntasAcertadas != 6) {
            tvRespuesta.text = preguntasAcertadas.toString().plus(" ")
                    .plus(getString(R.string.respuesta))
            tvNuevoIntento.visibility = View.VISIBLE
            btnNuevoIntento.visibility = View.VISIBLE
        }else //Se muestra que se han conseguido acertar todos las respuestas
            tvRespuesta.text = getString(R.string.todas).plus(" ")
                    .plus(getString(R.string.respuesta))
        btnSiguiente.visibility = View.VISIBLE
    }

    /**
     * Reinicia el juego al estado inicial de la actividad
     */
    private fun reiniciarJuego(){
        btnSanturtzi.visibility = View.VISIBLE
        btnBilbao.visibility = View.VISIBLE
        ivBarco.visibility = View.VISIBLE
        tvEnunciado.visibility = View.VISIBLE
        vBubble.visibility = View.VISIBLE
        tvNuevoIntento.visibility = View.GONE
        btnNuevoIntento.visibility = View.GONE
        btnSiguiente.visibility = View.GONE
        nivel = 0
        preguntasAcertadas = 0
        subirNivel()
    }

}