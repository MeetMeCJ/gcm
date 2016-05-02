package gcm.play.android.samples.com.gcmquickstart.pojo;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 20/04/2016.
 */
@DatabaseTable
public class Contact implements Parcelable {
    public static final String ID = "id";
    public static final String NOMBRE = "nombre";
    public static final String NICK = "nick";
    public static final String TELEFONO = "telefono";
    public static final String TOKEN = "token";
    public static final String DESCRIPCION = "descripcion";
    public static final String ULTIMACONEXION = "ultimaconexion";
    public static final String VERCONEXION = "verconexion";
    public static final String PRIVACIDAD = "privacidad";

    @DatabaseField(generatedId = true, columnName = ID)
    private Long id;

    @DatabaseField(columnName = NOMBRE)
    private String nombre;

    @DatabaseField(columnName = NICK)
    private String nick;

    @DatabaseField(columnName = TELEFONO)
    private List<String> telefono;

    @DatabaseField(columnName = TOKEN)
    private String token;

    @DatabaseField(columnName = DESCRIPCION)
    private String descripcion;

    @DatabaseField(columnName = ULTIMACONEXION)
    private String ultimaconexion;

    @DatabaseField(columnName = VERCONEXION)
    private String verconexion;

    @DatabaseField(columnName = PRIVACIDAD)
    private String privacidad;

    public Contact() {

    }

    public Contact(Long id, String nombre, String nick, List<String> telefono, String token, String descripcion, String ultimaconexion, String verconexion, String privacidad) {
        this.id = id;
        this.nombre = nombre;
        this.nick = nick;
        this.telefono = telefono;
        this.token = token;
        this.descripcion = descripcion;
        this.ultimaconexion = ultimaconexion;
        this.verconexion = verconexion;
        this.privacidad = privacidad;
    }

    public Contact(String nombre, String nick, List<String> telefono, String token, String descripcion, String ultimaconexion, String verconexion, String privacidad) {
        this.nombre = nombre;
        this.nick = nick;
        this.telefono = telefono;
        this.token = token;
        this.descripcion = descripcion;
        this.ultimaconexion = ultimaconexion;
        this.verconexion = verconexion;
        this.privacidad = privacidad;
    }

    protected Contact(Parcel in) {
        nombre = in.readString();
        nick = in.readString();
        telefono = in.createStringArrayList();
        token = in.readString();
        descripcion = in.readString();
        ultimaconexion = in.readString();
        verconexion = in.readString();
        privacidad = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

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

    public static Contact getUsuario(JSONObject json) {
        Contact u = new Contact();
        try {
            ArrayList<String> telefonos = new ArrayList<>();
            telefonos.add(json.getInt("telefono") + "");

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

    public ContentValues getContentValue() {
        ContentValues cv = new ContentValues();

        cv.put(NOMBRE, nombre);
        cv.put(TOKEN, token);
        cv.put(TELEFONO, telefono.get(0));
        cv.put(DESCRIPCION, descripcion);
        cv.put(NICK, nick);
        cv.put(PRIVACIDAD, privacidad);
        cv.put(ULTIMACONEXION, ultimaconexion);
        cv.put(VERCONEXION, verconexion);

        return cv;
    }


    @Override
    public String toString() {
        return "Contact{" +
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(nick);
        dest.writeStringList(telefono);
        dest.writeString(token);
        dest.writeString(descripcion);
        dest.writeString(ultimaconexion);
        dest.writeString(verconexion);
        dest.writeString(privacidad);
    }
}
