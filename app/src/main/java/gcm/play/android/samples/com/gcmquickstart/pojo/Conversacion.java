package gcm.play.android.samples.com.gcmquickstart.pojo;

/**
 * Created by Admin on 20/04/2016.
 */
public class Conversacion {
    private String mensaje;
    private String tokenconversacion;
    private String tokenemisor;
    private String fecha;
    private String hora;


    public Conversacion() {
    }

    public Conversacion(String mensaje, String tokenconversacion, String tokenemisor, String fecha, String hora) {
        this.mensaje = mensaje;
        this.tokenconversacion = tokenconversacion;
        this.tokenemisor = tokenemisor;
        this.fecha = fecha;
        this.hora = hora;
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
        return "Conversacion{" +
                "mensaje='" + mensaje + '\'' +
                ", tokenconversacion='" + tokenconversacion + '\'' +
                ", tokenemisor='" + tokenemisor + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                '}';
    }
}
