package gcm.play.android.samples.com.gcmquickstart.interzace_user;

/**
 * Created by Carmen on 07/05/2016.
 */
public class Titular {
    private String name;
    private String telephone;
    private String description;

    public Titular(){

    }

    public Titular(String name, String telephone, String description) {
        this.name = name;
        this.telephone = telephone;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}