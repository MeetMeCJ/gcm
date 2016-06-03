package gcm.play.android.samples.com.gcmquickstart.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import gcm.play.android.samples.com.gcmquickstart.manager.Manager;
import gcm.play.android.samples.com.gcmquickstart.R;

public class MyProfile extends AppCompatActivity {
    private TextView txtCounter;

    private EditText etDescription;
    private EditText telephone;
    private EditText email;
    private EditText nationality;
    private EditText birth;
    private EditText nick;
    private EditText description;
    private EditText facebook;
    private EditText twitter;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().openOptionsMenu();
        etDescription = (EditText) findViewById(R.id.profileDescription);
        txtCounter = (TextView) findViewById(R.id.txtCounter2);

        TextWatcher watch = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtCounter.setText(String.valueOf(s.length() + "/" + "150"));
                if (etDescription.getText().length() > 150) {
                    etDescription.setError("Error");
                }


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                //Este método se llama para informale de que, dentro de s, count empezando en start está a punto
                // de ser reemplazado por nuevo texto con longitud after


            }

            @Override
            public void afterTextChanged(Editable s) {
                //Este método se llama para informarle de que, en algún lugar dentro de s, el texto ha sido cambiado.
            }
        };

        etDescription.addTextChangedListener(watch);


        ini();

    }

    public void ini() {
        preferences = getSharedPreferences(getResources().getString(R.string.preference), Context.MODE_PRIVATE);
        editor = preferences.edit();

        String ourTelephone = preferences.getString(getString(R.string.key_telephone), "");
        String ourNationality = preferences.getString(getString(R.string.key_nationality), "");
        String ourDescripcion = preferences.getString(getString(R.string.key_description), "I am using MeetMe");
        String ourEmail = preferences.getString(getString(R.string.key_email), "");
        String ourFacebook = preferences.getString(getString(R.string.key_facebook), "");
        String ourBirth = preferences.getString(getString(R.string.key_birth), "");
        String ourNick = preferences.getString(getString(R.string.key_nick), "Nick");
        String ourTwitter = preferences.getString(getString(R.string.key_twitter), "");

        telephone = (EditText) findViewById(R.id.profileTelephone);
        email = (EditText) findViewById(R.id.profileEmail);
        nationality = (EditText) findViewById(R.id.profileNationality);
        birth = (EditText) findViewById(R.id.profileDate);
        nick = (EditText) findViewById(R.id.profileNick);
        description = (EditText) findViewById(R.id.profileDescription);
        facebook = (EditText) findViewById(R.id.profileFacebook);
        twitter = (EditText) findViewById(R.id.profileTwitter);

        getSupportActionBar().setTitle(ourNick);

        email.setText(ourEmail);
        nationality.setText(ourNationality);
        birth.setText(ourBirth);
        description.setText(ourDescripcion);
        facebook.setText(ourFacebook);
        twitter.setText(ourTwitter);
        telephone.setText(ourTelephone);
        nick.setText(ourNick);

        telephone.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Manager.syncOurSelves(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        editor.putString(getString(R.string.key_nationality), nationality.getText().toString().replace("ñ","n"));
        editor.putString(getString(R.string.key_description), description.getText().toString());
        editor.putString(getString(R.string.key_email), email.getText().toString());
        editor.putString(getString(R.string.key_facebook), facebook.getText().toString());
        editor.putString(getString(R.string.key_birth), birth.getText().toString());
        editor.putString(getString(R.string.key_nick), nick.getText().toString());
        editor.putString(getString(R.string.key_twitter), twitter.getText().toString());
        editor.commit();

        Manager.syncOurSelves(this);
    }

}
