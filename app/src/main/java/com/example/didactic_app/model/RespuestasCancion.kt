package com.example.didactic_app.model

class RespuestasCancion {
    var opcion1: Int = 0
    var opcion2: Int = 0
    var opcion3: Int = 0
    var correcta: Int = 0

    constructor(op1: Int, op2: Int, op3: Int, correcta: Int) {
        opcion1 = op1
        opcion2 = op2
        opcion3 = op3
        this.correcta = correcta
    }
}