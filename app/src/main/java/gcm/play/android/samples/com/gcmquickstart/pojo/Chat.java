package gcm.play.android.samples.com.gcmquickstart.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Admin on 20/04/2016.
 */
@DatabaseTable
public class Chat {
    public static final String ID = "id";
    public static final String MENSAJE = "mensaje";
    public static final String CONVERSACION = "tokenconversacion";//Siempre el del otro
    public static final String TOKENEMISOR = "tokenemisor";//Puede ser el nuestro o el del otro
    public static final String FECHA = "fecha";
    public static final String HORA = "hora";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;

    @DatabaseField(columnName = MENSAJE)
    private String mensaje;

    @DatabaseField(columnName = CONVERSACION)
    private String tokenconversacion;

    @DatabaseField(columnName = TOKENEMISOR)
    private String tokenemisor;

    @DatabaseField(columnName = FECHA)
    private String fecha;

    @DatabaseField(columnName = HORA)
    private String hora;


    public Chat() {
    }

    public Chat(String mensaje, String tokenconversacion, String tokenemisor, String fecha, String hora) {
        this.mensaje = mensaje;
        this.tokenconversacion = tokenconversacion;
        this.tokenemisor = tokenemisor;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Chat(int id, String mensaje, String tokenconversacion, String tokenemisor, String fecha, String hora) {
        this.id = id;
        this.mensaje = mensaje;
        this.tokenconversacion = tokenconversacion;
        this.tokenemisor = tokenemisor;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTokenconversacion() {
        return tokenconversacion;
    }

    public void setTokenconversacion(String tokenconversacion) {
        this.tokenconversacion = tokenconversacion;
    }

    public String getTokenemisor() {
        return tokenemisor;
    }

    public void setTokenemisor(String tokenemisor) {
        this.tokenemisor = tokenemisor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", mensaje='" + mensaje + '\'' +
                ", tokenconversacion='" + tokenconversacion + '\'' +
                ", tokenemisor='" + tokenemisor + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                '}';
    }
}
