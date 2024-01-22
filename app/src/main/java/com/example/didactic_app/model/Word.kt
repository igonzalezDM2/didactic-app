package com.example.didactic_app.model

/**
 * Clase para representar una palabra en el juego.
 *
 * @param word la palabra a representar
 */
class Word(word: String) {
    var isFound = false
    val wordVal = word
    var _start = 0
    var _end = 0

    /**
     * Establece la ubicación de la palabra en el tablero.
     *
     * @param tag la posición inicial de la palabra
     * @param isHorizontal indica si la palabra está en posición horizontal
     */
    fun setLoc(tag: Int, isHorizontal: Boolean){
        _start = tag
        _end = if(isHorizontal) tag + wordVal.length - 1 else tag + (wordVal.length -1)*10
    }

    /**
     * Verifica si la ubicación seleccionada coincide con la ubicación de la palabra.
     *
     * @param start la posición inicial seleccionada
     * @param end la posición final seleccionada
     * @param isHorizontal indica si la palabra está en posición horizontal
     * @return true si la ubicación seleccionada coincide con la ubicación de la palabra, de lo contrario false
     */
    fun checkLoc(start: Int, end: Int, isHorizontal: Boolean): Boolean{

        if(isHorizontal){
            // horizontal case: check if word length and selected equal
            if(end - start != wordVal.length - 1){
                return false
            }
        } else {
            // vertical case: check if word length and selected equal
            if((end - start)/10 != wordVal.length - 1){
                return false
            }
        }
        if(_start == start && _end == end){
            return true
        }
        return false
    }
}