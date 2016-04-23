package gcm.play.android.samples.com.gcmquickstart.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Admin on 12/04/2016.
 */

public class Proveedor extends ContentProvider {
    private Ayudante abd;
    private static final UriMatcher convierteUri;
    private static final int USUARIOS = 1;
    private static final int USUARIO_ID = 2;
    private static final int CONVERSACIONES = 3;
    private static final int CONVERSACIONES_ID = 4;


    static {
        convierteUri = new UriMatcher(UriMatcher.NO_MATCH);
        convierteUri.addURI(Contrato.TablaUsuario.AUTHORITY, Contrato.TablaUsuario.TABLA, USUARIOS);
        convierteUri.addURI(Contrato.TablaUsuario.AUTHORITY, Contrato.TablaUsuario.TABLA + "/#", USUARIO_ID);
        convierteUri.addURI(Contrato.TablaConversacion.AUTHORITY, Contrato.TablaConversacion.TABLA, CONVERSACIONES);
        convierteUri.addURI(Contrato.TablaConversacion.AUTHORITY, Contrato.TablaConversacion.TABLA + "/#", CONVERSACIONES_ID);

    }

    @Override
    public boolean onCreate() {
        abd = new Ayudante(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[]
            selectionArgs, String sortOrder) {
        SQLiteDatabase db = abd.getReadableDatabase();
        int match = convierteUri.match(uri);
        Cursor c;
        switch (match) {

            case USUARIOS:
                c = db.query(Contrato.TablaUsuario.TABLA, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;

            case USUARIO_ID:
                long idActividad = ContentUris.parseId(uri);
                c = db.query(Contrato.TablaUsuario.TABLA, projection,
                        Contrato.TablaUsuario._ID + " = ?",
                        new String[]{idActividad + ""}, null, null, sortOrder);
                break;

            case CONVERSACIONES:
                c = db.query(Contrato.TablaConversacion.TABLA, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;

            case CONVERSACIONES_ID:
                long idActividad2 = ContentUris.parseId(uri);
                c = db.query(Contrato.TablaConversacion.TABLA, projection,
                        Contrato.TablaConversacion._ID + " = ?",
                        new String[]{idActividad2 + ""}, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }

        switch (match) {
            case USUARIOS:
            case USUARIO_ID:
                c.setNotificationUri(getContext().getContentResolver(), Contrato.TablaUsuario.CONTENT_URI);
                break;

            case CONVERSACIONES:
            case CONVERSACIONES_ID:
                c.setNotificationUri(getContext().getContentResolver(), Contrato.TablaConversacion.CONTENT_URI);
                break;


        }
        return c;

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
//        if (convierteUri.match(uri) != USUARIOS) {
//            throw new IllegalArgumentException("URI desconocida: " + uri);
//        }

        if (values == null) {
            throw new IllegalArgumentException("Cliente null");
        }

        SQLiteDatabase db = abd.getWritableDatabase();


        switch (convierteUri.match(uri)) {

            case USUARIOS:

                long rowIdUsuario = db.insert(Contrato.TablaUsuario.TABLA, null, values);

                if (rowIdUsuario > 0) {
                    //Si se ha insertado el elemento correctamente, entonces devolvemos la uri del elemento que se acaba de insertar
                    Uri uri_actividad = ContentUris.withAppendedId(Contrato.TablaUsuario.CONTENT_URI, rowIdUsuario);
                    getContext().getContentResolver().notifyChange(uri_actividad, null);
                    return uri_actividad;
                }
                throw new SQLException("Error al insertar fila en : " + uri);


            case CONVERSACIONES:
                long rowIdConversacion = db.insert(Contrato.TablaConversacion.TABLA, null, values);
                if (rowIdConversacion > 0) {
                    //Si se ha insertado el elemento correctamente, entonces devolvemos la uri del elemento que se acaba de insertar
                    Uri uri_actividad = ContentUris.withAppendedId(Contrato.TablaConversacion.CONTENT_URI, rowIdConversacion);
                    getContext().getContentResolver().notifyChange(uri_actividad, null);
                    return uri_actividad;
                }
                throw new SQLException("Error al insertar fila en : " + uri);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = abd.getWritableDatabase();
        int match = convierteUri.match(uri);
        int affected;
        switch (match) {
            case USUARIOS:
                affected = db.delete(Contrato.TablaUsuario.TABLA, selection,
                        selectionArgs);
                break;

            case USUARIO_ID:
                long idActividad = ContentUris.parseId(uri);
                affected = db.delete(Contrato.TablaUsuario.TABLA,
                        Contrato.TablaUsuario._ID + "= ?",
                        new String[]{idActividad + ""});
                break;

            case CONVERSACIONES:
                affected = db.delete(Contrato.TablaConversacion.TABLA, selection,
                        selectionArgs);
                break;

            case CONVERSACIONES_ID:
                long idActividad2 = ContentUris.parseId(uri);
                affected = db.delete(Contrato.TablaConversacion.TABLA,
                        Contrato.TablaUsuario._ID + "= ?",
                        new String[]{idActividad2 + ""});
                break;

            default:
                throw new IllegalArgumentException(
                        "Elemento actividad desconocido: " + uri);
        }
        if (affected > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return affected;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = abd.getWritableDatabase();
        int affected;
        switch (convierteUri.match(uri)) {
            case USUARIOS:
                affected = db.update(Contrato.TablaUsuario.TABLA,
                        values, selection, selectionArgs);
                break;
            case USUARIO_ID:
                String idActividad = uri.getPathSegments().get(1);
                idActividad = uri.getLastPathSegment();
                long id = ContentUris.parseId(uri);
                affected = db.update(Contrato.TablaUsuario.TABLA, values,
                        Contrato.TablaUsuario._ID + "= ? ",
                        new String[]{idActividad});
                break;

            case CONVERSACIONES:
                affected = db.update(Contrato.TablaConversacion.TABLA,
                        values, selection, selectionArgs);
                break;

            case CONVERSACIONES_ID:
                String idActividad2 = uri.getPathSegments().get(1);
                idActividad = uri.getLastPathSegment();
                long id2 = ContentUris.parseId(uri);
                affected = db.update(Contrato.TablaConversacion.TABLA, values,
                        Contrato.TablaUsuario._ID + "= ? ",
                        new String[]{idActividad});
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " +
                        uri);
        }
        if (affected > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return affected;
    }

    @Override
    public String getType(Uri uri) {
        switch (convierteUri.match(uri)) {
            case USUARIOS:
                return Contrato.TablaUsuario.MULTIPLE_MIME;
            case USUARIO_ID:
                return Contrato.TablaUsuario.SINGLE_MIME;
            case CONVERSACIONES:
                return Contrato.TablaConversacion.MULTIPLE_MIME;
            case CONVERSACIONES_ID:
                return Contrato.TablaConversacion.SINGLE_MIME;
            default:
                throw new IllegalArgumentException(
                        "Tipo de actividad desconocida: " + uri);
        }
    }

}