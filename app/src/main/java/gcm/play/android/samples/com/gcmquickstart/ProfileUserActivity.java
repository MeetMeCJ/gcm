package gcm.play.android.samples.com.gcmquickstart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import gcm.play.android.samples.com.gcmquickstart.pojo.Contact;

public class ProfileUserActivity extends AppCompatActivity {
    private EditText etDescription;
    private TextView txtCounter;

    private Contact contact;

    private Toolbar toolbar;

    private EditText telephone;
    private EditText email;
    private EditText nationality;
    private EditText birth;
    private EditText nick;
    private EditText description;
    private EditText facebook;
    private EditText twitter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        toolbar = (Toolbar) findViewById(R.id.profileAppbar);
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
        contact = getIntent().getExtras().getParcelable(getString(R.string.key_token));

        getSupportActionBar().setTitle(contact.getName());

        telephone = (EditText) findViewById(R.id.profileTelephone);
        email = (EditText) findViewById(R.id.profileEmail);
        nationality = (EditText) findViewById(R.id.profileNationality);
        birth = (EditText) findViewById(R.id.profileDate);
        nick = (EditText) findViewById(R.id.profileNick);
        description = (EditText) findViewById(R.id.profileDescription);
        facebook = (EditText) findViewById(R.id.profileFacebook);
        twitter = (EditText) findViewById(R.id.profileTwitter);

        telephone.setEnabled(false);
        email.setEnabled(false);
        nationality.setEnabled(false);
        birth.setEnabled(false);
        nick.setEnabled(false);
        description.setEnabled(false);
        facebook.setEnabled(false);
        twitter.setEnabled(false);

        switch (contact.getPrivacy().toLowerCase()) {
            case "mis contactos":
            case "todos":
                email.setText(contact.getEmail());
                nationality.setText(contact.getBirth());
                birth.setText(contact.getBirth());
                description.setText(contact.getDescription());
                facebook.setText(contact.getFacebook());
                twitter.setText(contact.getTwitter());
            case "nadie":
            default:
                telephone.setText(contact.getTelephone());
                nick.setText(contact.getNick());

        }


    }
}