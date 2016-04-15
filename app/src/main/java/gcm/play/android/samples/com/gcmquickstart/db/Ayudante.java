package gcm.play.android.samples.com.gcmquickstart.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Admin on 12/04/2016.
 */
public class Ayudante extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "meetme.db";
    public static final int DATABASE_VERSION = 1;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null,
                DATABASE_VERSION);
        Log.v("ASDF","Ayudante constructor");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        String sql = "drop table if exists "
                + Contrato.TablaUsuario.TABLA;
        db.execSQL(sql);
        sql = "drop table if exists "
                + Contrato.TablaConversacion.TABLA;
        db.execSQL(sql);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql = "create table " + Contrato.TablaUsuario.TABLA +" (" +
                Contrato.TablaUsuario._ID + " integer primary key autoincrement, " +
                Contrato.TablaUsuario.NOMBRE + " text, " +
                Contrato.TablaUsuario.NICK + " text, " +
                Contrato.TablaUsuario.TELEFONO + " text, " +
                Contrato.TablaUsuario.TOKEN + " text, " +
                Contrato.TablaUsuario.ULTIMACONEXION + " text, " +
                Contrato.TablaUsuario.DESCRIPCION + " text, " +
                Contrato.TablaUsuario.PRIVACIDAD + " text, " +
                Contrato.TablaUsuario.VERCONEXION + " text )";
        db.execSQL(sql);


        sql = "CREATE TABLE " + Contrato.TablaConversacion.TABLA +
                " (" + Contrato.TablaConversacion._ID +
                " integer primary key autoincrement, " +
                Contrato.TablaConversacion.MENSAJE + " text, " +
                Contrato.TablaConversacion.TOKENEMISOR + " text, " +
                Contrato.TablaConversacion.CONVERSACION + " text, " +
                Contrato.TablaConversacion.FECHA + " text, " +
                Contrato.TablaConversacion.HORA + " text )";
        db.execSQL(sql);
    }
}
