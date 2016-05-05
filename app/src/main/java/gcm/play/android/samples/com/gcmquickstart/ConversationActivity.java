package gcm.play.android.samples.com.gcmquickstart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import gcm.play.android.samples.com.gcmquickstart.db.DBHelper;
import gcm.play.android.samples.com.gcmquickstart.pojo.Chat;

public class ConversationActivity extends AppCompatActivity {

    private DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ini();
    }

    public void ini() {
        String token = (String) getIntent().getExtras().get(getString(R.string.str_token));

        helper = OpenHelperManager.getHelper(getBaseContext(), DBHelper.class);

        Dao dao;
        try  {
            dao = helper.getChatDao();

            List messages = dao.queryForEq(Chat.CONVERSATION, token);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("Helper", "Search user error");
        }
    }

    public void conversationSend(View v){

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
