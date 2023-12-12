package com.example.didactic_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class AtraparSardinasActivity : AppCompatActivity() {
    private lateinit var tvCantidadSardinas: TextView
    private lateinit var tvTiempo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atrapar_sardinas)

        tvTiempo = findViewById(R.id.tvTiempo)
        tvCantidadSardinas = findViewById(R.id.tvCantidadSardinas)

        tvTiempo.text = getString(R.string.tiempo)
        tvCantidadSardinas.text = 0.toString()

    }
}