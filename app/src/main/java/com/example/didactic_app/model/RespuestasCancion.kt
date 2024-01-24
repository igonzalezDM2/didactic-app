package com.example.didactic_app.model

import com.example.didactic_app.R

/**
 * Clase para representar las respuestas de una canción.
 */
class RespuestasCancion {
    var pregunta: Int = 0
    var opcion1: Int = 0
    var opcion2: Int = 0
    var opcion3: Int = 0
    var correcta: Int = 0

    /**
     * Constructor de la clase RespuestasCancion.
     *
     * @param pregunta el número de la pregunta
     * @param op1 la opción 1
     * @param op2 la opción 2
     * @param op3 la opción 3
     * @param correcta la respuesta correcta
     */
    constructor(pregunta: Int, op1: Int, op2: Int, op3: Int, correcta: Int) {
        this.pregunta = pregunta
        opcion1 = op1
        opcion2 = op2
        opcion3 = op3
        this.correcta = correcta
    }

    /**
     * Obtiene el índice de la respuesta correcta.
     *
     * @return el índice de la respuesta correcta
     */
    fun getIndiceCorrecta(): Int {
        when (correcta) {
            opcion1 -> return 0
            opcion2 -> return 1
            opcion3 -> return 2
            else -> return -1
        }
    }
}