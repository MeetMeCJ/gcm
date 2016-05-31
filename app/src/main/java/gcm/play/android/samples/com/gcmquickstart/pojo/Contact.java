package gcm.play.android.samples.com.gcmquickstart.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    public static final String FACEBOOK = "facebook";
    public static final String TWITTER = "twitter";
    public static final String EMAIL = "email";
    public static final String BIRTH = "birth";
    public static final String NATIONALITY = "nationality";
    public static final String PRIVACY = "privacy";

    @DatabaseField(generatedId = true, columnName = ID)
    private Long id;

    @DatabaseField(columnName = NAME)
    private String name;

    @SerializedName("nick")
    @DatabaseField(columnName = NICK)
    private String nick;

    @SerializedName("telephone")
    @DatabaseField(columnName = TELEPHONE)
    private String telephone;

    @SerializedName("token")
    @DatabaseField(columnName = TOKEN)
    private String token;

    @SerializedName("description")
    @DatabaseField(columnName = DESCRIPTION)
    private String description;

    @SerializedName("lastconnection")
    @DatabaseField(columnName = LASTCONNECTION)
    private String lastconnection;

    @SerializedName("seeconnection")
    @DatabaseField(columnName = SEECONNECTION)
    private String seeconnection;

    @SerializedName("facebook")
    @DatabaseField(columnName = FACEBOOK)
    private String facebook;

    @SerializedName("twitter")
    @DatabaseField(columnName = TWITTER)
    private String twitter;

    @SerializedName("email")
    @DatabaseField(columnName = EMAIL)
    private String email;

    @SerializedName("birth")
    @DatabaseField(columnName = BIRTH)
    private String birth;

    @SerializedName("nationality")
    @DatabaseField(columnName = NATIONALITY)
    private String nationality;

    @SerializedName("privacy")
    @DatabaseField(columnName = PRIVACY)
    private String privacy;


    public Contact() {
    }

    public Contact(String token, String telephone) {
        this.token = token;
        this.telephone = telephone;
    }

    public Contact(String nick, String telephone, String token, String description, String lastconnection, String facebook, String email, String birth, String twitter, String seeconnection, String nationality, String privacy) {
        this.nick = nick;
        this.telephone = telephone;
        this.token = token;
        this.description = description;
        this.lastconnection = lastconnection;
        this.facebook = facebook;
        this.email = email;
        this.birth = birth;
        this.twitter = twitter;
        this.seeconnection = seeconnection;
        this.nationality = nationality;
        this.privacy = privacy;
    }

    public Contact(Long id, String name, String nick, String telephone, String token, String description, String lastconnection, String seeconnection, String facebook, String twitter, String email, String birth, String nationality, String privacy) {
        this.id = id;
        this.name = name;
        this.nick = nick;
        this.telephone = telephone;
        this.token = token;
        this.description = description;
        this.lastconnection = lastconnection;
        this.seeconnection = seeconnection;
        this.facebook = facebook;
        this.twitter = twitter;
        this.email = email;
        this.birth = birth;
        this.nationality = nationality;
        this.privacy = privacy;
    }

    public Contact(String privacy, String name, String nick, String telephone, String token, String description, String lastconnection, String seeconnection, String facebook, String twitter, String email, String birth, String nationality) {
        this.privacy = privacy;
        this.name = name;
        this.nick = nick;
        this.telephone = telephone;
        this.token = token;
        this.description = description;
        this.lastconnection = lastconnection;
        this.seeconnection = seeconnection;
        this.facebook = facebook;
        this.twitter = twitter;
        this.email = email;
        this.birth = birth;
        this.nationality = nationality;
    }

    protected Contact(Parcel in) {
        name = in.readString();
        nick = in.readString();
        telephone = in.readString();
        token = in.readString();
        description = in.readString();
        lastconnection = in.readString();
        seeconnection = in.readString();
        facebook = in.readString();
        twitter = in.readString();
        email = in.readString();
        birth = in.readString();
        nationality = in.readString();
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

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String nacimiento) {
        this.birth = birth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nacionalidad) {
        this.nationality = nationality;
    }

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
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

    public void getUsuario(JSONObject json) {
        try {
            ArrayList<String> telephones = new ArrayList<>();
            telephones.add(json.getInt("telephone") + "");

            setDescription(json.getString("description"));
            setNick(json.getString("nick"));
            setPrivacy(json.getString("privacy"));
            setTelephone(telephones.get(0));
            setToken(json.getString("token"));
            setLastconnection(json.getString("last"));
            setSeeconnection(json.getString("see"));
            setTwitter(json.getString("twitter"));
            setFacebook(json.getString("facebook"));
            setEmail(json.getString("email"));
            setNationality(json.getString("nationality"));
            setBirth(json.getString("birth"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJSON() {
        JSONObject result = new JSONObject();
        try {
            result.put("name", name);
            result.put("token", token);
            result.put("telephone", telephone);
            result.put("nick", nick);
            result.put("description", description);
            result.put("last", lastconnection);
            result.put("privacy", privacy);
            result.put("see", seeconnection);
            result.put("facebook", facebook);
            result.put("twitter", twitter);
            result.put("email", email);
            result.put("nationality", nationality);
            result.put("birth", birth);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nick='" + nick + '\'' +
                ", telephone='" + telephone + '\'' +
                ", token='" + token + '\'' +
                ", description='" + description + '\'' +
                ", lastconnection='" + lastconnection + '\'' +
                ", seeconnection='" + seeconnection + '\'' +
                ", facebook='" + facebook + '\'' +
                ", twitter='" + twitter + '\'' +
                ", email='" + email + '\'' +
                ", birth='" + birth + '\'' +
                ", nationality='" + nationality + '\'' +
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
        dest.writeString(telephone);
        dest.writeString(token);
        dest.writeString(description);
        dest.writeString(lastconnection);
        dest.writeString(seeconnection);
        dest.writeString(facebook);
        dest.writeString(twitter);
        dest.writeString(email);
        dest.writeString(birth);
        dest.writeString(nationality);
        dest.writeString(privacy);
    }
}
