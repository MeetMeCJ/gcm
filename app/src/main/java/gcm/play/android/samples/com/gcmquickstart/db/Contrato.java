package gcm.play.android.samples.com.gcmquickstart.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Admin on 12/04/2016.
 */
public class Contrato {

    public static abstract class TablaUsuario implements BaseColumns {
        public static final String TABLA = "usuario";
        public static final String NOMBRE = "nombre";
        public static final String NICK = "nick";
        public static final String TELEFONO = "telefono";
        public static final String TOKEN = "token";
        public static final String DESCRIPCION = "descripcion";
        public static final String ULTIMACONEXION = "ultimaconexion";
        public static final String VERCONEXION = "verconexion";
        public static final String PRIVACIDAD = "privacidad";

        public final static String AUTHORITY ="gcm.play.android.samples.com.gcmquickstart.Proveedor";
        public final static Uri CONTENT_URI =Uri.parse("content://" + AUTHORITY + "/" + TABLA);
        public final static String SINGLE_MIME ="vnd.android.cursor.item/vnd." + AUTHORITY + TABLA;
        public final static String MULTIPLE_MIME ="vnd.android.cursor.dir/vnd." + AUTHORITY + TABLA;
    }

    public static abstract class TablaConversacion implements BaseColumns {
        public static final String TABLA = "conversacion";
        public static final String MENSAJE = "mensaje";
        public static final String CONVERSACION = "tokenconversacion";//Siempre el del otro
        public static final String TOKENEMISOR = "tokenemisor";//Puede ser el nuestro o el del otro
        public static final String FECHA = "fecha";
        public static final String HORA = "hora";

        public final static String AUTHORITY ="gcm.play.android.samples.com.gcmquickstart.Proveedor";
        public final static Uri CONTENT_URI =Uri.parse("content://" + AUTHORITY + "/" + TABLA);
        public final static String SINGLE_MIME ="vnd.android.cursor.item/vnd." + AUTHORITY + TABLA;
        public final static String MULTIPLE_MIME ="vnd.android.cursor.dir/vnd." + AUTHORITY + TABLA;
    }
}
