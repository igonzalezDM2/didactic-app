package com.example.didactic_app.utilis;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.didactic_app.PartidaSQLiteHelper;
import com.example.didactic_app.enums.Lugar;

import java.util.function.Consumer;

public class Utils {
    public static void anadirSuperado(Context contexto, Lugar lugar) {
        operarBD(contexto, db -> {
            ContentValues cv = new ContentValues();
            cv.put("superado", true);
            db.update("partida", cv, "lugar = ?", new String[]{lugar.toString()});
        });
    }

    public static void operarBD(Context contexto, Consumer<SQLiteDatabase> callback) {
        PartidaSQLiteHelper helper = new PartidaSQLiteHelper(contexto, "dbpartida", null, 1);
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            callback.accept(db);
        }
    }

}
