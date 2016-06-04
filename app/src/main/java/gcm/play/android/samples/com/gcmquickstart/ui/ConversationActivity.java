package gcm.play.android.samples.com.gcmquickstart.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
import java.util.Date;
import java.util.List;

import gcm.play.android.samples.com.gcmquickstart.manager.Manager;
import gcm.play.android.samples.com.gcmquickstart.R;
import gcm.play.android.samples.com.gcmquickstart.adapter.AdapterConversation;
import gcm.play.android.samples.com.gcmquickstart.db.DBHelper;
import gcm.play.android.samples.com.gcmquickstart.pojo.Chat;
import gcm.play.android.samples.com.gcmquickstart.pojo.Contact;
import gcm.play.android.samples.com.gcmquickstart.service.QuickstartPreferences;

public class ConversationActivity extends AppCompatActivity {
    public static final String API_KEY = "AIzaSyCF2MH1r1DOBlF3Lz7ma1hNFEQVJldt71U";

    private DBHelper helper;

    private EditText myText;

    private Contact contact;

    private String token;

    private List<Chat> messages;

    private RecyclerView recyclerView;
    private AdapterConversation adapterConversation;

    private BroadcastReceiver mConversationBroadcastReceiver;

    private Toolbar toolbar;

    private boolean registerReciver = false;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        toolbar = (Toolbar) findViewById(R.id.toolbarConversation);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ProfileUserActivity.class);
                i.putExtra(getString(R.string.key_token), contact);
                startActivity(i);
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(true);

        ini();
    }

    public void ini() {
        preferences = getSharedPreferences(getResources().getString(R.string.preference), Context.MODE_PRIVATE);

        contact = getIntent().getExtras().getParcelable(getString(R.string.key_token));

        token = contact.getToken();

        myText = (EditText) findViewById(R.id.conversation_editText);


        updateList();


        mConversationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateList();
            }
        };

        registerReceiver();
    }

    private void registerReceiver() {
        if (!registerReciver) {

            LocalBroadcastManager.getInstance(this).registerReceiver(mConversationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.CONVERSATION));

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(getResources().getString(R.string.str_register_broadcast), true);
            editor.commit();

            registerReciver = true;
        }
    }

    public void conversationSend(View v) {
        if (!myText.getText().toString().isEmpty() && !myText.getText().toString().trim().equals("")) {
            sendMessage(myText.getText().toString(), token);
            Dao dao;
            try {
                dao = helper.getChatDao();
                Chat message = new Chat();
                message.setMessage(myText.getText().toString());
                message.setTokenconversation(token);
                message.setTokensender(preferences.getString(getString(R.string.key_token), ""));

                Date fecha = new Date();
                String date = fecha.getDay() + "/" + fecha.getMonth() + "/" + (1900 + fecha.getYear());
                String minute = "";
                if (fecha.getMinutes() < 0)
                    minute = "0";
                message.setTime(fecha.getHours() + ":" + minute + fecha.getMinutes());
                message.setDate(date);
                dao.create(message);

                messages.add(message);

                adapterConversation = new AdapterConversation(messages);
                recyclerView.setAdapter(adapterConversation);
                recyclerView.scrollToPosition(messages.size() - 1);
            } catch (SQLException e) {
                e.printStackTrace();
                Log.e("Helper", "Search user error");
            }
        }
        myText.setText("");

        Manager.syncOurSelves(this);
    }

    public void goToLastMessage(View v) {
        recyclerView.scrollToPosition(messages.size() - 1);
    }

    public void sendMessage(final String message, final String destination) {
        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.preference), Context.MODE_PRIVATE);
        final String ourToken = prefs.getString(getResources().getString(R.string.key_token), "");
        final String ourTelephone = prefs.getString(getString(R.string.key_telephone), "");

        Log.v("ASDF", "hebra");
        AsyncTask asyncTask = new AsyncTask() {

            private String aux;

            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Log.v("ASDF", "empieza la hebra a mandar un msg");

                    JSONObject jGcmData = new JSONObject();
                    JSONObject jData = new JSONObject();
                    jData.put("message", message);
                    jData.put("origin", ourToken);
                    jData.put("telephone", ourTelephone);


                    jGcmData.put("to", destination);

                    jGcmData.put("data", jData);

                    URL url = new URL("https://gcm-http.googleapis.com/gcm/send");
                    Log.v("ASDF", url.toString());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Authorization", "key=" + API_KEY);
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);

                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(jGcmData.toString().getBytes());

                    InputStream inputStream = conn.getInputStream();
                    String resp = IOUtils.toString(inputStream);
                    System.out.println(resp);
                } catch (IOException e) {
                    Log.v("ASDF", "error " + e.toString());
                    e.printStackTrace();
                } catch (JSONException e) {
                    Log.v("ASDF", "error2 " + e.toString());
                    e.printStackTrace();
                }
                return "";
            }
        };
        asyncTask.execute(null, null, null);

    }


    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mConversationBroadcastReceiver);
        if (helper != null) {
            OpenHelperManager.releaseHelper();
            helper = null;
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(getResources().getString(R.string.str_register_broadcast), false);
        editor.commit();

        registerReciver = false;
    }

    public void updateList() {
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
            contact = (Contact) dao.queryForEq(Contact.TOKEN, token).get(0);
            toolbar.setTitle(contact.getName());
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("Helper", "Search user error");
        }

        adapterConversation = new AdapterConversation(messages);

        recyclerView = (RecyclerView) findViewById(R.id.conversationRecycler);
        recyclerView.setAdapter(adapterConversation);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.scrollToPosition(messages.size() - 1);
    }

}
