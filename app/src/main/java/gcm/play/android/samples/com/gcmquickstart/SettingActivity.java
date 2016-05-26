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
    private TextView optionSeePhoto;
    private TextView optionSeeDescription;
    private TextView optionSeeNotificationMessage;
    private TextView optionSeeNotificationGroup;
    private TextView optionVibrationMessage;
    private TextView optionVibrationGroup;
    private TextView optionFontSize;


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

        optionSeePhoto = (TextView) findViewById(R.id.setting_option_profile_photo);
        optionSeePhoto.setText(preferences.getString(getString(R.string.str_setting_see_photo), getString(R.string.setting_option_see_all)));

        optionSeeDescription = (TextView) findViewById(R.id.setting_option_description);
        optionSeeDescription.setText(preferences.getString(getString(R.string.str_setting_see_description), getString(R.string.setting_option_see_all)));

        optionSeeNotificationMessage = (TextView) findViewById(R.id.setting_option_notification_message);
        optionSeeNotificationMessage.setText(preferences.getString(getString(R.string.str_setting_notification), getString(R.string.str_setting_emergent_notification_always)));

        optionSeeNotificationGroup = (TextView) findViewById(R.id.setting_option_notification_group);
        optionSeeNotificationGroup.setText(preferences.getString(getString(R.string.str_setting_notification), getString(R.string.str_setting_emergent_notification_always)));

        optionVibrationMessage = (TextView) findViewById(R.id.setting_option_vibration_message);
        optionVibrationMessage.setText(preferences.getString(getString(R.string.str_setting_vibration), getString(R.string.str_setting_vibration_option_default)));

        optionVibrationGroup = (TextView) findViewById(R.id.setting_option_vibration_group);
        optionVibrationGroup.setText(preferences.getString(getString(R.string.str_setting_vibration), getString(R.string.str_setting_vibration_option_default)));

        optionFontSize =(TextView) findViewById(R.id.setting_option_font_size);
        optionFontSize.setText(preferences.getString(getString(R.string.str_setting_font_size), getString(R.string.str_setting_font_size)));

    }

    public void settingSeeLastTime(View v) {
        String privacy[] = new String[3];
        privacy[0] = getString(R.string.setting_option_see_all);
        privacy[1] = getString(R.string.setting_option_see_friend);
        privacy[2] = getString(R.string.setting_option_see_anyone);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_setting_last_hour)
                .setItems(privacy, new DialogInterface.OnClickListener() {
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



    public void settingSeePhotoProfile(View v){
        String privacy[] = new String[3];
        privacy[0] = getString(R.string.setting_option_see_all);
        privacy[1] = getString(R.string.setting_option_see_friend);
        privacy[2] = getString(R.string.setting_option_see_anyone);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_setting_see_photo)
                .setItems(privacy, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        optionSeePhoto = (TextView) findViewById(R.id.setting_option_profile_photo);
                        switch (which) {
                            case 0:
                                //Guardamos en un preferencia compartida que se ha guardado en esta
                                editor.putString(getString(R.string.str_setting_see_photo), getString(R.string.setting_option_see_all));
                                optionSeePhoto.setText(preferences.getString(getString(R.string.str_setting_see_photo), getString(R.string.setting_option_see_all)));

                                //Nos bajamos del servidor el objeto contact con nuetro numero de telefono
                                //Lo editamos metiendole en privacidad este nuevo campo
                                //y lo actualizamos en el servidor
                                break;
                            case 1:
                                editor.putString(getString(R.string.str_setting_see_photo), getString(R.string.setting_option_see_friend));
                                optionSeePhoto.setText(preferences.getString(getString(R.string.str_setting_see_photo), getString(R.string.setting_option_see_friend)));
                                break;
                            case 2:
                                editor.putString(getString(R.string.str_setting_see_photo), getString(R.string.setting_option_see_anyone));
                                optionSeePhoto.setText(preferences.getString(getString(R.string.str_setting_see_photo), getString(R.string.setting_option_see_anyone)));
                                break;
                        }
                        editor.commit();
                    }
                });
        builder.create().show();

    }


    public void settingSeeDescription(View v){
        String privacy[] = new String[3];
        privacy[0] = getString(R.string.setting_option_see_all);
        privacy[1] = getString(R.string.setting_option_see_friend);
        privacy[2] = getString(R.string.setting_option_see_anyone);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_setting_see_description)
                .setItems(privacy, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        optionSeeDescription = (TextView) findViewById(R.id.setting_option_description);
                        switch (which) {
                            case 0:
                                //Guardamos en un preferencia compartida que se ha guardado en esta
                                editor.putString(getString(R.string.str_setting_see_description), getString(R.string.setting_option_see_all));
                                optionSeeDescription.setText(preferences.getString(getString(R.string.str_setting_see_description), getString(R.string.setting_option_see_all)));
                                //Nos bajamos del servidor el objeto contact con nuetro numero de telefono
                                //Lo editamos metiendole en privacidad este nuevo campo
                                //y lo actualizamos en el servidor
                                break;
                            case 1:
                                editor.putString(getString(R.string.str_setting_see_description), getString(R.string.setting_option_see_friend));
                                optionSeeDescription.setText(preferences.getString(getString(R.string.str_setting_see_description), getString(R.string.setting_option_see_friend)));
                                break;
                            case 2:
                                editor.putString(getString(R.string.str_setting_see_description), getString(R.string.setting_option_see_anyone));
                                optionSeeDescription.setText(preferences.getString(getString(R.string.str_setting_see_description), getString(R.string.setting_option_see_anyone)));
                                break;
                        }
                        editor.commit();
                    }
                });
        builder.create().show();

    }

    public void settingSeeNotificationMessage(View v){
        String privacy[] = new String[2];
        privacy[0] = getString(R.string.str_setting_emergent_notification_always);
        privacy[1] = getString(R.string.str_setting_emergent_notification_never);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_setting_notification)
                .setItems(privacy, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        optionSeeNotificationMessage = (TextView) findViewById(R.id.setting_option_notification_message);
                        switch (which) {
                            case 0:
                                //Guardamos en un preferencia compartida que se ha guardado en esta
                                editor.putString(getString(R.string.str_setting_notification), getString(R.string.str_setting_emergent_notification_always));
                                optionSeeNotificationMessage.setText(preferences.getString(getString(R.string.str_setting_notification), getString(R.string.str_setting_emergent_notification_always)));

                                //Nos bajamos del servidor el objeto contact con nuetro numero de telefono
                                //Lo editamos metiendole en privacidad este nuevo campo
                                //y lo actualizamos en el servidor
                                break;
                            case 1:
                                editor.putString(getString(R.string.str_setting_see_description), getString(R.string.str_setting_emergent_notification_never));
                                optionSeeNotificationMessage.setText(preferences.getString(getString(R.string.str_setting_see_description), getString(R.string.str_setting_emergent_notification_never)));
                                break;

                        }
                        editor.commit();
                    }
                });
        builder.create().show();

    }

    public void settingSeeNotificationGroup(View v){
        String privacy[] = new String[2];
        privacy[0] = getString(R.string.str_setting_emergent_notification_always);
        privacy[1] = getString(R.string.str_setting_emergent_notification_never);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_setting_notification_group)
                .setItems(privacy, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        optionSeeNotificationGroup = (TextView) findViewById(R.id.setting_option_notification_group);
                        switch (which) {
                            case 0:
                                //Guardamos en un preferencia compartida que se ha guardado en esta
                                editor.putString(getString(R.string.str_setting_notification_group), getString(R.string.str_setting_emergent_notification_always));
                                optionSeeNotificationGroup.setText(preferences.getString(getString(R.string.str_setting_notification_group), getString(R.string.str_setting_emergent_notification_always)));

                                //Nos bajamos del servidor el objeto contact con nuetro numero de telefono
                                //Lo editamos metiendole en privacidad este nuevo campo
                                //y lo actualizamos en el servidor
                                break;
                            case 1:
                                editor.putString(getString(R.string.str_setting_notification_group), getString(R.string.str_setting_emergent_notification_never));
                                optionSeeNotificationGroup.setText(preferences.getString(getString(R.string.str_setting_notification_group), getString(R.string.str_setting_emergent_notification_never)));
                                break;

                        }
                        editor.commit();
                    }
                });
        builder.create().show();

    }


    public void settingVibrationMessage(View v) {
        String privacy[] = new String[3];
        privacy[0] = getString(R.string.str_setting_vibration_option_default);
        privacy[1] = getString(R.string.str_setting_vibration_option_short);
        privacy[2] = getString(R.string.str_setting_vibration_option_long);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_setting_vibration)
                .setItems(privacy, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        optionVibrationMessage = (TextView) findViewById(R.id.setting_option_vibration_message);
                        switch (which) {
                            case 0:
                                //Guardamos en un preferencia compartida que se ha guardado en esta
                                editor.putString(getString(R.string.str_setting_vibration), getString(R.string.str_setting_vibration_option_default));
                                optionVibrationMessage.setText(preferences.getString(getString(R.string.str_setting_vibration), getString(R.string.str_setting_vibration_option_default)));

                                //Nos bajamos del servidor el objeto contact con nuetro numero de telefono
                                //Lo editamos metiendole en privacidad este nuevo campo
                                //y lo actualizamos en el servidor
                                break;
                            case 1:
                                editor.putString(getString(R.string.str_setting_vibration), getString(R.string.str_setting_vibration_option_long));
                                optionVibrationMessage.setText(preferences.getString(getString(R.string.str_setting_vibration), getString(R.string.str_setting_vibration_option_long)));
                                break;
                            case 2:
                                editor.putString(getString(R.string.str_setting_vibration), getString(R.string.str_setting_vibration_option_short));
                                optionVibrationMessage.setText(preferences.getString(getString(R.string.str_setting_vibration), getString(R.string.str_setting_vibration_option_short)));
                                break;
                        }
                        editor.commit();
                    }
                });
        builder.create().show();
    }

    public void settingVibrationGroup(View v) {
        String privacy[] = new String[3];
        privacy[0] = getString(R.string.str_setting_vibration_option_default);
        privacy[1] = getString(R.string.str_setting_vibration_option_short);
        privacy[2] = getString(R.string.str_setting_vibration_option_long);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_setting_vibration)
                .setItems(privacy, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        optionVibrationGroup = (TextView) findViewById(R.id.setting_option_vibration_group);
                        switch (which) {
                            case 0:
                                //Guardamos en un preferencia compartida que se ha guardado en esta
                                editor.putString(getString(R.string.str_setting_vibration), getString(R.string.str_setting_vibration_option_default));
                                optionVibrationGroup.setText(preferences.getString(getString(R.string.str_setting_vibration), getString(R.string.str_setting_vibration_option_default)));

                                //Nos bajamos del servidor el objeto contact con nuetro numero de telefono
                                //Lo editamos metiendole en privacidad este nuevo campo
                                //y lo actualizamos en el servidor
                                break;
                            case 1:
                                editor.putString(getString(R.string.str_setting_vibration), getString(R.string.str_setting_vibration_option_long));
                                optionVibrationGroup.setText(preferences.getString(getString(R.string.str_setting_vibration), getString(R.string.str_setting_vibration_option_long)));
                                break;
                            case 2:
                                editor.putString(getString(R.string.str_setting_vibration), getString(R.string.str_setting_vibration_option_short));
                                optionVibrationGroup.setText(preferences.getString(getString(R.string.str_setting_vibration), getString(R.string.str_setting_vibration_option_short)));
                                break;
                        }
                        editor.commit();
                    }
                });
        builder.create().show();
    }

    public void settingFontSize(View v){
        String privacy[] = new String[3];
        privacy[0] = getString(R.string.str_setting_font_size_big);
        privacy[1] = getString(R.string.str_setting_font_size_medium);
        privacy[2] = getString(R.string.str_setting_font_size_small);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_setting_font_size_info)
                .setItems(privacy, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        optionFontSize = (TextView) findViewById(R.id.setting_option_font_size);
                        switch (which) {
                            case 0:
                                //Guardamos en un preferencia compartida que se ha guardado en esta
                                editor.putString(getString(R.string.str_setting_font_size), getString(R.string.str_setting_font_size_big));
                                optionFontSize.setText(preferences.getString(getString(R.string.str_setting_font_size), getString(R.string.str_setting_font_size_big)));

                                //Nos bajamos del servidor el objeto contact con nuetro numero de telefono
                                //Lo editamos metiendole en privacidad este nuevo campo
                                //y lo actualizamos en el servidor
                                break;
                            case 1:
                                editor.putString(getString(R.string.str_setting_font_size), getString(R.string.str_setting_font_size_medium));
                                optionFontSize.setText(preferences.getString(getString(R.string.str_setting_font_size), getString(R.string.str_setting_font_size_medium)));
                                break;
                            case 2:
                                editor.putString(getString(R.string.str_setting_font_size), getString(R.string.str_setting_font_size_small));
                                optionFontSize.setText(preferences.getString(getString(R.string.str_setting_font_size), getString(R.string.str_setting_font_size_small)));
                                break;
                        }
                        editor.commit();
                    }
                });
        builder.create().show();
    }


}
