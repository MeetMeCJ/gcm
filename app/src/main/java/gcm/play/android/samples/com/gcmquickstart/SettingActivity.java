package gcm.play.android.samples.com.gcmquickstart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private TextView optionSeeLastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
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


        preferences = getSharedPreferences(getResources().getString(R.string.preference), Context.MODE_PRIVATE);
        editor = preferences.edit();

        ini();
        //Sacamos de las preferencias compartida lo que esta marcado para cada opcion y lo escribimos en su lugar
    }

    public void ini() {
        optionSeeLastTime = (TextView) findViewById(R.id.setting_option_last_hour);
        optionSeeLastTime.setText(preferences.getString(getString(R.string.str_setting_last_hour), getString(R.string.setting_option_see_all)));
    }

    public void settingSeeLastTime(View v) {
        String privacidad[] = new String[3];
        privacidad[0] = getString(R.string.setting_option_see_all);
        privacidad[1] = getString(R.string.setting_option_see_friend);
        privacidad[2] = getString(R.string.setting_option_see_anyone);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_setting_last_hour)
                .setItems(privacidad, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        optionSeeLastTime = (TextView) findViewById(R.id.setting_option_last_hour);
                        switch (which) {
                            case 0:
                                //Guardamos en un preferencia compartida que se ha guardado en esta
                                editor.putString(getString(R.string.str_setting_last_hour), getString(R.string.setting_option_see_all));

                                optionSeeLastTime.setText(preferences.getString(getString(R.string.str_setting_last_hour), getString(R.string.setting_option_see_all)));

                                //Nos bajamos del servidor el objeto contact con nuetro numero de telefono
                                //Lo editamos metiendole en privacidad este nuevo campo
                                //y lo actualizamos en el servidor
                                break;
                            case 1:
                                editor.putString(getString(R.string.str_setting_last_hour), getString(R.string.setting_option_see_friend));
                                optionSeeLastTime.setText(preferences.getString(getString(R.string.str_setting_last_hour), getString(R.string.setting_option_see_friend)));
                                break;
                            case 2:
                                editor.putString(getString(R.string.str_setting_last_hour), getString(R.string.setting_option_see_anyone));
                                optionSeeLastTime.setText(preferences.getString(getString(R.string.str_setting_last_hour), getString(R.string.setting_option_see_anyone)));
                                break;
                        }
                        editor.commit();
                    }
                });
        builder.create().show();
    }

}
