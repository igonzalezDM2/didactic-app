package com.example.didactic_app.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class DialogoFinJuegoLanzamiento(private val aciertos: Int) : DialogFragment() {
    private var listener: OnDialogoConfirmacionListener? = null
    private val tvMensaje: TextView? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(
            if (aciertos == 6) "Goazen hurrengo tokira" else String.format(
                "%d/6 lortu duzu\nBerriz saiatu nahi duzu?",
                aciertos
            )
        )
            .setTitle(if (aciertos == 6) "OSO ONDO" else "Berriz saiatu")
            .setPositiveButton("ADOS") { dialog: DialogInterface?, which: Int -> listener!!.onPossitiveButtonClick() }
        if (aciertos < 6) {
            builder.setNegativeButton("EZ") { dialog: DialogInterface?, which: Int -> listener!!.onNegativeButtonClick() }
        }
        val dialogo = builder.create()
        dialogo.setCancelable(false)
        dialogo.setCanceledOnTouchOutside(false)
        return dialogo
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            context as OnDialogoConfirmacionListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context no implementó OnDialogoConfirmacionListener")
        }
    }
}