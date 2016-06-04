package gcm.play.android.samples.com.gcmquickstart.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Admin on 20/04/2016.
 */
@DatabaseTable
public class Chat {
    public static final String ID = "id";
    public static final String MESSAGE = "message";
    public static final String CONVERSATION = "tokenconversation";
    public static final String TOKENSENDER = "tokensender";
    public static final String DATE = "date";
    public static final String TIME = "time";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;

    @DatabaseField(columnName = MESSAGE)
    private String message;

    @DatabaseField(columnName = CONVERSATION)
    private String tokenconversation;

    @DatabaseField(columnName = TOKENSENDER)
    private String tokensender;

    @DatabaseField(columnName = DATE)
    private String date;

    @DatabaseField(columnName = TIME)
    private String time;


    public Chat(){}

    public Chat(String message, String tokenconversation, String tokensender, String date, String time) {
        this.message = message;
        this.tokenconversation = tokenconversation;
        this.tokensender = tokensender;
        this.date = date;
        this.time = time;
    }

    public Chat(int id, String message, String tokenconversation, String tokensender, String date, String time) {
        this.id = id;
        this.message = message;
        this.tokenconversation = tokenconversation;
        this.tokensender = tokensender;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTokenconversation() {
        return tokenconversation;
    }

    public void setTokenconversation(String tokenconversation) {
        this.tokenconversation = tokenconversation;
    }

    public String getTokensender() {
        return tokensender;
    }

    public void setTokensender(String tokensender) {
        this.tokensender = tokensender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", tokenconversation='" + tokenconversation + '\'' +
                ", tokensender='" + tokensender + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
