package com.example.didactic_app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SopaActivity : AppCompatActivity() {

    lateinit var wordsGrid: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sopa)

        wordsGrid = findViewById(R.id.letras)






    }
}