package com.example.didactic_app.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment

/**
 * Clase para mostrar un diálogo al finalizar el juego.
 *
 * @param aciertos la cantidad de respuestas correctas
 * @param total la cantidad total de respuestas
 */
class DialogoFinJuego(private val aciertos: Int, private val total: Int) : DialogFragment() {
    private var listener: OnDialogoConfirmacionListener? = null
    private val tvMensaje: TextView? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(
            if (aciertos == total) "Goazen hurrengo tokira" else String.format(
                "%d/%d lortu duzu\nBerriz saiatu nahi duzu?",
                aciertos, total
            )
        )
            .setTitle(if (aciertos == total) "OSO ONDO" else "Berriz saiatu")
            .setPositiveButton("ADOS") { dialog: DialogInterface?, which: Int -> listener!!.onPossitiveButtonClick() }
        if (aciertos < total) {
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