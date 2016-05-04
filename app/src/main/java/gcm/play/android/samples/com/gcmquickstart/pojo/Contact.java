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
    public static final String NAME = "name";
    public static final String NICK = "nick";
    public static final String TELEPHONE = "telephone";
    public static final String TOKEN = "token";
    public static final String DESCRIPTION = "description";
    public static final String LASTCONNECTION = "lastconnection";
    public static final String SEECONNECTION = "seeconnection";
    public static final String PRIVACY = "privacy";

    @DatabaseField(generatedId = true, columnName = ID)
    private Long id;

    @DatabaseField(columnName = NAME)
    private String name;

    @DatabaseField(columnName = NICK)
    private String nick;

    @DatabaseField(columnName = TELEPHONE)
    private List<String> telephone;

    @DatabaseField(columnName = TOKEN)
    private String token;

    @DatabaseField(columnName = DESCRIPTION)
    private String description;

    @DatabaseField(columnName = LASTCONNECTION)
    private String lastconnection;

    @DatabaseField(columnName = SEECONNECTION)
    private String seeconnection;

    @DatabaseField(columnName = PRIVACY)
    private String privacy;

    public Contact() {

    }

    public Contact(Long id, String name, String nick, List<String> telephone, String token, String description, String lastconnection, String seeconnection, String privacy) {
        this.id = id;
        this.name = name;
        this.nick = nick;
        this.telephone = telephone;
        this.token = token;
        this.description = description;
        this.lastconnection = lastconnection;
        this.seeconnection = seeconnection;
        this.privacy = privacy;
    }

    public Contact(String name, String nick, List<String> telephone, String token, String description, String lastconnection, String seeconnection, String privacy) {
        this.name = name;
        this.nick = nick;
        this.telephone = telephone;
        this.token = token;
        this.description = description;
        this.lastconnection = lastconnection;
        this.seeconnection = seeconnection;
        this.privacy = privacy;
    }

    protected Contact(Parcel in) {
        name = in.readString();
        nick = in.readString();
        telephone = in.createStringArrayList();
        token = in.readString();
        description = in.readString();
        lastconnection = in.readString();
        seeconnection = in.readString();
        privacy = in.readString();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public List<String> getTelephone() {
        return telephone;
    }

    public void setTelephone(List<String> telephone) {
        this.telephone = telephone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeeconnection() {
        return seeconnection;
    }

    public void setSeeconnection(String seeconnection) {
        this.seeconnection = seeconnection;
    }

    public String getLastconnection() {
        return lastconnection;
    }

    public void setLastconnection(String lastconnection) {
        this.lastconnection = lastconnection;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public static Contact getUsuario(JSONObject json) {
        Contact u = new Contact();
        try {
            ArrayList<String> telephones = new ArrayList<>();
            telephones.add(json.getInt("telephone") + "");
            
            u.setDescription(json.getString("description"));
            u.setNick(json.getString("nick"));
            u.setPrivacy(json.getString("privacy"));
            u.setTelephone(telephones);
            u.setToken(json.getString("token"));
            u.setLastconnection(json.getString("last"));
            u.setSeeconnection(json.getString("see"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }

    public JSONObject getJSON() {
        JSONObject result = new JSONObject();
        try {
            result.put("token", token);
            result.put("telephone", telephone);
            result.put("nick", nick);
            result.put("description", description);
            result.put("last", lastconnection);
            result.put("see", seeconnection);
            result.put("privacy", privacy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ContentValues getContentValue() {
        ContentValues cv = new ContentValues();

        cv.put(NAME, name);
        cv.put(TOKEN, token);
        cv.put(TELEPHONE, telephone.get(0));
        cv.put(DESCRIPTION, description);
        cv.put(NICK, nick);
        cv.put(PRIVACY, privacy);
        cv.put(LASTCONNECTION, lastconnection);
        cv.put(SEECONNECTION, seeconnection);

        return cv;
    }


    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", nick='" + nick + '\'' +
                ", telephone='" + telephone + '\'' +
                ", token='" + token + '\'' +
                ", description='" + description + '\'' +
                ", lastconnection='" + lastconnection + '\'' +
                ", seeconnection='" + seeconnection + '\'' +
                ", privacy='" + privacy + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(nick);
        dest.writeStringList(telephone);
        dest.writeString(token);
        dest.writeString(description);
        dest.writeString(lastconnection);
        dest.writeString(seeconnection);
        dest.writeString(privacy);
    }
}
