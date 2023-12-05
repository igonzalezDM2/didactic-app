package com.example.didactic_app

/**
 * Clase que representa una pregunta
 * @property enunciado de la pregunta
 * @property respuesta de la pregunta
 * @constructor Se pasa el enunciado de la pregunta y su respuesta
 * @author Sergio Daniel Groppa
 */
class Pregunta() {

    private var _enunciado: String = ""

    val enunciado: String
        get(){
            return _enunciado
        }

    private var respuesta:String = ""

    constructor(enunciado:String, respuesta:String) : this() {
        this._enunciado = enunciado
        this.respuesta = respuesta
    }

    /**
     * Metodo que compara la respuesta con la respuesta pasada por parametro
     * @param posibleResuelta una respuesta
     * @return devuelve `true` si es la misma respuesta con la que se pasa con la del parametro si
     * no devuelve `false`
     */
    fun respuestaValida(posibleResuelta:String): Boolean{
        if(this.respuesta.lowercase() == posibleResuelta.lowercase())
            return true
        return false
    }



}