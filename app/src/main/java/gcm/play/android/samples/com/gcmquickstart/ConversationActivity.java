package gcm.play.android.samples.com.gcmquickstart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import gcm.play.android.samples.com.gcmquickstart.db.DBHelper;
import gcm.play.android.samples.com.gcmquickstart.pojo.Chat;
import gcm.play.android.samples.com.gcmquickstart.pojo.Contact;

public class ConversationActivity extends AppCompatActivity {
    public static final String API_KEY = "AIzaSyCF2MH1r1DOBlF3Lz7ma1hNFEQVJldt71U";

    private DBHelper helper;

    private LinearLayout conversationLayout;

    private EditText myText;

    private String token;

    private List<Chat> messages;

    private Toolbar toolbar;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);

        ini();
    }

    public void ini() {
        preferences = getSharedPreferences(getResources().getString(R.string.preference), Context.MODE_PRIVATE);

        token = (String) getIntent().getExtras().get(getString(R.string.str_token));
        myText = (EditText) findViewById(R.id.conversation_editText);

        conversationLayout = (LinearLayout) findViewById(R.id.conversation_linear_layout);

        helper = OpenHelperManager.getHelper(getBaseContext(), DBHelper.class);

        Dao dao;
        try {
            dao = helper.getChatDao();
            messages = dao.queryForEq(Chat.CONVERSATION, token);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("Helper", "Search user error");
        }
        Contact contact;
        try {
            dao = helper.getContactDao();
//            contact = (Contact) dao.queryForEq(Contact.TOKEN, token);
//            toolbar.setTitle(contact.getName());
            toolbar.setTitle("Carmen");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("Helper", "Search user error");
        }

        if (messages != null || !messages.isEmpty())
            write();
    }

    public void conversationSend(View v) {
        if (!myText.getText().toString().isEmpty() && !myText.getText().toString().trim().equals("")) {
            //Manager.sendMessage(this, myText.getText().toString(), token);
            sendMessage(myText.getText().toString(), token);
            writeMyMessage(myText.getText().toString());
            Dao dao;
            try {
                dao = helper.getChatDao();
                Chat message = new Chat();
                message.setMessage(myText.getText().toString());
                message.setTokenconversation(token);
                message.setTokensender(preferences.getString(getString(R.string.str_token), ""));
                dao.create(message);
            } catch (SQLException e) {
                e.printStackTrace();
                Log.e("Helper", "Search user error");
            }
        }
        myText.setText("");
    }

    public void write() {
        for (Chat message : messages) {
            if (message.getTokensender().equals(token))
                writeOtherMessage(message.getMessage());
            else
                writeMyMessage(message.getMessage());
        }
    }

    public void writeMyMessage(String myMessage) {
        TextView newText = new TextView(this);
        newText.setText(myMessage);
        newText.setBackgroundResource(R.drawable.shape_conversation_message_myself);
        newText.setPadding(18, 15, 18, 15);
        newText.setGravity(Gravity.END);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        conversationLayout.addView(newText, params);
    }

    public void writeOtherMessage(String myMessage) {
        TextView newText = new TextView(this);
        newText.setText(myMessage);
        newText.setBackgroundResource(R.drawable.shape_conversation_message_other);
        newText.setPadding(18, 15, 18, 15);
        newText.setGravity(Gravity.LEFT);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        conversationLayout.addView(newText, params);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (helper != null) {
            OpenHelperManager.releaseHelper();
            helper = null;
        }
    }

    public void sendMessage(final String message, final String destination) {
        Log.v("ASDF","EN EL CONVERSATION");

        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.preference), Context.MODE_PRIVATE);
        final String ourToken = prefs.getString(getResources().getString(R.string.str_token), "");

        new AsyncTask() {

            private String aux;

            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Log.v("ASDF", "empieza la hebra a mandar un msg");
                    // Prepare JSON containing the GCM message content. What to send and where to send.
                    JSONObject jGcmData = new JSONObject();
                    JSONObject jData = new JSONObject();
                    jData.put("message", message);
                    jData.put("origin", ourToken);
                    // Where to send GCM message.

                    //jGcmData.put("to", destination);
                    jGcmData.put("to", "/topics/global");

                    // What to send in GCM message.
                    jGcmData.put("data", jData);

                    // Create connection to send GCM Message request.
                    //URL url = new URL("https://android.googleapis.com/gcm/send");
                    URL url = new URL("https://gcm-http.googleapis.com/gcm/send");
                    Log.v("ASDF", url.toString());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Authorization", "key=" + API_KEY);
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);

                    // Send GCM message content.
                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(jGcmData.toString().getBytes());

                    // Read GCM response.
                    InputStream inputStream = conn.getInputStream();
                    String resp = IOUtils.toString(inputStream);
                    System.out.println(resp);

                    Log.v("ASDF", "respuesta " + resp);
                    Log.v("ASDF", "lo manda");


                } catch (IOException e) {
                    Log.v("ASDF", "error " + e.toString());
                    e.printStackTrace();
                } catch (JSONException e) {
                    Log.v("ASDF", "error2 " + e.toString());
                    e.printStackTrace();
                }
                return "";
            }
        }.execute(null, null, null);
    }

}
