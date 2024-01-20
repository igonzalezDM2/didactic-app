package com.example.didactic_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.didactic_app.enums.Lugar;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase PartidaSQLiteHelper que extiende de SQLiteOpenHelper.
 */
public class PartidaSQLiteHelper extends SQLiteOpenHelper {

    /**
     * Sentencia SQL para crear la tabla partida.
     */
    private static final String SQL_CREATE_PARTIDA =
            "CREATE TABLE partida (" +
                    " lugar TEXT, " +
                    "superado BOOLEAN)";



    /**
     * Constructor de la clase PartidaSQLiteHelper.
     *
     * @param context   contexto de la aplicación
     * @param name      nombre de la base de datos
     * @param factory   cursor factory
     * @param version   versión de la base de datos
     */
    public PartidaSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Método llamado al crear la base de datos por primera vez.
     *
     * @param db base de datos
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PARTIDA);
        insertarLugares(db);
    }

    /**
     * Método llamado cuando se necesita actualizar la base de datos.
     *
     * @param db         base de datos
     * @param oldVersion versión antigua de la base de datos
     * @param newVersion nueva versión de la base de datos
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String[] campos = new String[] {"lugar", "superado"};
        Map<Lugar, Boolean> mapPartida = new HashMap<>(Lugar.values().length);
        Cursor cur = db.query("partida", campos, null, null, null, null, null);
        while (cur.moveToNext()) {
            Lugar lugar = Lugar.valueOf(cur.getString(0));
            boolean superado = cur.getInt(1) > 0;
            mapPartida.put(lugar, superado);
        }
        cur.close();
        db.execSQL("DROP TABLE IF EXISTS `partida`");
        db.execSQL(SQL_CREATE_PARTIDA);

        mapPartida.entrySet().forEach(e -> {
            ContentValues cv = new ContentValues();
            cv.put("lugar", e.getKey().toString());
            cv.put("superado", e.getValue());
            db.insert("partida", null, cv);
        });

    }


    /**
     * Método privado para insertar los lugares en la tabla partida.
     *
     * @param db base de datos
     */
    private void insertarLugares(SQLiteDatabase db) {
        Arrays.stream(Lugar.values()).forEach(l -> {
            db.execSQL("INSERT INTO partida VALUES ('" + l.toString() + "', 0)");
        });
    }
}
