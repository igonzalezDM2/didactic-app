package com.example.didactic_app.enums

enum class Lugar {
    SERANTES("Serantes Mendia"),
    SARDINERA("La Sardinera eskultura"),
    SARE_JOSLE("Sare josleen lan lekua"),
    MUSEO("Santurtziko itsas museoa"),
    PARQUE("Santurtziko parkea"),
//    REMO("Santurtziko arraun udal pabiloia"),
    AYUNTAMIENTO("Santurtziko udaletxea");

    private lateinit var nombre: String

    constructor(nombre: String) {
        this.nombre = nombre
    }

    public fun getNombre(): String {
        return nombre;
    }
}