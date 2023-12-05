package com.example.didactic_app.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogoFinJuegoLanzamiento extends DialogFragment {

    private OnDialogoConfirmacionListener listener;

    private TextView tvMensaje;

    private int aciertos;

    public DialogoFinJuegoLanzamiento(int aciertos) {
        this.aciertos = aciertos;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(aciertos == 6 ? "Goazen hurrengo tokira" : String.format("%d/6 lortu duzu\nBerriz saiatu nahi duzu?", aciertos))
                .setTitle(aciertos == 6 ? "OSO ONDO" : "Berriz saiatu")
                .setPositiveButton("ADOS", (dialog, which) -> {
                    listener.onPossitiveButtonClick();
                });

        if (aciertos < 6) {
            builder.setNegativeButton("EZ", (dialog, which) -> {
                        listener.onNegativeButtonClick();
                    });
        }

//        LinearLayout linearLayout = new LinearLayout(getContext());
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//        tvMensaje = new TextView(getContext());
//        linearLayout.addView(tvMensaje);
//
//
//        builder.setView(linearLayout);

        AlertDialog dialogo = builder.create();
        dialogo.setCancelable(false);
        dialogo.setCanceledOnTouchOutside(false);

//        dialogo.setOnShowListener((v) -> {
//            dialogo
//                    .getButton(AlertDialog.BUTTON_POSITIVE)
//                    .setOnClickListener(v1 -> comprobarAcceso(dialogo));
//        });

        return dialogo;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (OnDialogoConfirmacionListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+" no implementó OnDialogoConfirmacionListener");
        }
    }
}