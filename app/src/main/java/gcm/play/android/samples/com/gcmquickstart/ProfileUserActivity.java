package gcm.play.android.samples.com.gcmquickstart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileUserActivity extends AppCompatActivity {
    private EditText etDescription;
    private TextView txtCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.profileAppbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getSupportActionBar().openOptionsMenu();
        etDescription = (EditText)findViewById(R.id.profileDescription);
        txtCounter = (TextView)findViewById(R.id.txtCounter2);

        TextWatcher watch = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtCounter.setText(String.valueOf(s.length() + "/" + "150"));
                if(etDescription.getText().length()>150){
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




    }
}