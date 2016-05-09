package gcm.play.android.samples.com.gcmquickstart;

import android.content.Context;
import android.content.SharedPreferences;
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

import java.sql.SQLException;
import java.util.List;

import gcm.play.android.samples.com.gcmquickstart.db.DBHelper;
import gcm.play.android.samples.com.gcmquickstart.pojo.Chat;

public class ConversationActivity extends AppCompatActivity {

    private DBHelper helper;

    private LinearLayout conversationLayout;

    private EditText myText;

    private String token;

    private List<Chat> messages;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ini();
    }

    public void ini() {
        preferences=getSharedPreferences(getResources().getString(R.string.preference), Context.MODE_PRIVATE);

        token = (String) getIntent().getExtras().get(getString(R.string.str_token));
        myText = (EditText) findViewById(R.id.conversation_editText);

        conversationLayout= (LinearLayout) findViewById(R.id.conversation_linear_layout);

        helper = OpenHelperManager.getHelper(getBaseContext(), DBHelper.class);

        Dao dao;
        try {
            dao = helper.getChatDao();
            messages = dao.queryForEq(Chat.CONVERSATION, token);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("Helper", "Search user error");
        }

        if(messages!=null || !messages.isEmpty())
            write();
    }

    public void conversationSend(View v) {
        if (!myText.getText().toString().isEmpty()) {
            Manager.sendMessage(this, myText.getText().toString(), token);
            writeMyMessage(myText.getText().toString());
            Dao dao;
            try {
                dao = helper.getChatDao();
                Chat message=new Chat();
                message.setMessage(myText.getText().toString());
                message.setTokenconversation(token);
                message.setTokensender(preferences.getString(getString(R.string.str_token),""));
                dao.create(message);
            } catch (SQLException e) {
                e.printStackTrace();
                Log.e("Helper", "Search user error");
            }
        }
        myText.setText("");
    }

    public void write(){
        for(Chat message:messages){
            if(message.getTokensender().equals(token))
                writeOtherMessage(message.getMessage());
            else
                writeMyMessage(message.getMessage());
        }
    }

    public void writeMyMessage(String myMessage) {
        TextView newText=new TextView(this);
        newText.setText(myMessage);
        newText.setBackgroundResource(R.drawable.shape_conversation_message_myself);
        newText.setPadding(8, 5, 8, 5);
        newText.setGravity(Gravity.RIGHT);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        conversationLayout.addView(newText, params);
    }
    public void writeOtherMessage(String myMessage) {
        TextView newText=new TextView(this);
        newText.setText(myMessage);
        newText.setBackgroundResource(R.drawable.shape_conversation_message_other);
        newText.setPadding(8, 5, 8, 5);
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
}
