package gcm.play.android.samples.com.gcmquickstart.pojo;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gcm.play.android.samples.com.gcmquickstart.db.Contrato;

/**
 * Created by Admin on 20/04/2016.
 */
public class Usuario {
    private Long id;
    private String nombre;
    private String nick;
    private List<String> telefono;
    private String token;
    private String descripcion;
    private String ultimaconexion;
    private String verconexion;
    private String privacidad;

    public Usuario() {

    }

    public Usuario(String nombre, String nick, List<String> telefono, String token, String descripcion, String ultimaconexion, String verconexion, String privacidad) {
        this.nombre = nombre;
        this.nick = nick;
        this.telefono = telefono;
        this.token = token;
        this.descripcion = descripcion;
        this.ultimaconexion = ultimaconexion;
        this.verconexion = verconexion;
        this.privacidad = privacidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public List<String> getTelefono() {
        return telefono;
    }

    public void setTelefono(List<String> telefono) {
        this.telefono = telefono;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getVerconexion() {
        return verconexion;
    }

    public void setVerconexion(String verconexion) {
        this.verconexion = verconexion;
    }

    public String getUltimaconexion() {
        return ultimaconexion;
    }

    public void setUltimaconexion(String ultimaconexion) {
        this.ultimaconexion = ultimaconexion;
    }

    public String getPrivacidad() {
        return privacidad;
    }

    public void setPrivacidad(String privacidad) {
        this.privacidad = privacidad;
    }

    public static Usuario getUsuario(JSONObject json){
        Usuario u=new Usuario();
        try {
            ArrayList<String> telefonos=new ArrayList<>();
            telefonos.add(json.getInt("telefono")+"");

            u.setDescripcion(json.getString("descripcion"));
            u.setNick(json.getString("nick"));
            u.setPrivacidad(json.getString("privacidad"));
            u.setTelefono(telefonos);
            u.setToken(json.getString("token"));
            u.setUltimaconexion(json.getString("ultima"));
            u.setVerconexion(json.getString("ver"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }

    public JSONObject getJSON() {
        JSONObject result = new JSONObject();
        try {
            result.put("token", token);
            result.put("telefono", telefono);
            result.put("nick", nick);
            result.put("descripcion", descripcion);
            result.put("ultima", ultimaconexion);
            result.put("ver", verconexion);
            result.put("privacidad", privacidad);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ContentValues getContentValue(){
        ContentValues cv=new ContentValues();

        cv.put(Contrato.TablaUsuario.NOMBRE,nombre);
        cv.put(Contrato.TablaUsuario.TOKEN,token);
        cv.put(Contrato.TablaUsuario.TELEFONO,telefono.get(0));
        cv.put(Contrato.TablaUsuario.DESCRIPCION,descripcion);
        cv.put(Contrato.TablaUsuario.NICK,nick);
        cv.put(Contrato.TablaUsuario.PRIVACIDAD,privacidad);
        cv.put(Contrato.TablaUsuario.ULTIMACONEXION,ultimaconexion);
        cv.put(Contrato.TablaUsuario.VERCONEXION,verconexion);

        return cv;
    }



    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", nick='" + nick + '\'' +
                ", telefono='" + telefono + '\'' +
                ", token='" + token + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", ultimaconexion='" + ultimaconexion + '\'' +
                ", verconexion='" + verconexion + '\'' +
                ", privacidad='" + privacidad + '\'' +
                '}';
    }
}
