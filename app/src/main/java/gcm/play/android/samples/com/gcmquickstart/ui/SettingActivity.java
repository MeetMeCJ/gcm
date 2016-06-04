package gcm.play.android.samples.com.gcmquickstart.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import gcm.play.android.samples.com.gcmquickstart.manager.Manager;
import gcm.play.android.samples.com.gcmquickstart.R;

public class SettingActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private TextView settingNick;
    private TextView optionSeeLastTime;
    private TextView optionSeePhoto;
    private TextView optionSeeDescription;
    private TextView optionSeeNotificationMessage;
    private TextView optionSeeNotificationGroup;
    private TextView optionVibrationMessage;
    private TextView optionVibrationGroup;
    private TextView optionFontSize;

    private final String sizeFont[] = new String[3];
    private final String privacy[] = new String[3];
    private final String notificationMessage[] = new String[2];
    private final String durationVibration[] = new String[3];


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

    }

    public void ini() {
        String nick = preferences.getString(getString(R.string.key_nick), "Nick");
        settingNick = (TextView) findViewById(R.id.setting_nick);
        settingNick.setText(nick);


        optionSeeLastTime = (TextView) findViewById(R.id.setting_option_last_hour);
        optionSeeLastTime.setText(preferences.getString(getString(R.string.key_last_connection), getString(R.string.setting_option_see_all)));

        optionSeePhoto = (TextView) findViewById(R.id.setting_option_profile_photo);
        optionSeePhoto.setText(preferences.getString(getString(R.string.str_setting_see_photo), getString(R.string.setting_option_see_all)));

        optionSeeDescription = (TextView) findViewById(R.id.setting_option_description);
        optionSeeDescription.setText(preferences.getString(getString(R.string.key_privacy), getString(R.string.setting_option_see_all)));

        optionSeeNotificationMessage = (TextView) findViewById(R.id.setting_option_notification_message);
        optionSeeNotificationMessage.setText(preferences.getString(getString(R.string.str_setting_notification), getString(R.string.str_setting_emergent_notification_always)));

        optionSeeNotificationGroup = (TextView) findViewById(R.id.setting_option_notification_group);
        optionSeeNotificationGroup.setText(preferences.getString(getString(R.string.str_setting_notification), getString(R.string.str_setting_emergent_notification_always)));

        optionVibrationMessage = (TextView) findViewById(R.id.setting_option_vibration_message);
        optionVibrationMessage.setText(preferences.getString(getString(R.string.str_setting_vibration), getString(R.string.str_setting_vibration_option_default)));

        optionVibrationGroup = (TextView) findViewById(R.id.setting_option_vibration_group);
        optionVibrationGroup.setText(preferences.getString(getString(R.string.str_setting_vibration), getString(R.string.str_setting_vibration_option_default)));

        optionFontSize = (TextView) findViewById(R.id.setting_option_font_size);
        optionFontSize.setText(preferences.getString(getString(R.string.str_setting_font_size), getString(R.string.str_setting_font_size)));


        privacy[0] = getString(R.string.setting_option_see_all);
        privacy[1] = getString(R.string.setting_option_see_friend);
        privacy[2] = getString(R.string.setting_option_see_anyone);

        sizeFont[0] = getString(R.string.str_setting_font_size_big);
        sizeFont[1] = getString(R.string.str_setting_font_size_medium);
        sizeFont[2] = getString(R.string.str_setting_font_size_small);

        notificationMessage[0] = getString(R.string.str_setting_emergent_notification_always);
        notificationMessage[1] = getString(R.string.str_setting_emergent_notification_never);

        durationVibration[0] = getString(R.string.str_setting_vibration_option_default);
        durationVibration[1] = getString(R.string.str_setting_vibration_option_short);
        durationVibration[2] = getString(R.string.str_setting_vibration_option_long);

    }

    public void settingSeeLastTime(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_setting_last_hour)
                .setItems(privacy, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putString(getString(R.string.key_last_connection), privacy[which]);
                        optionSeeLastTime.setText(privacy[which]);
                        editor.commit();
                    }
                });
        builder.create().show();
        Manager.syncOurSelves(this);
    }


    public void settingSeePhotoProfile(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_setting_see_photo)
                .setItems(privacy, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Guardamos en un preferencia compartida que se ha guardado en esta
                        editor.putString(getString(R.string.str_setting_see_photo), privacy[which]);
                        optionSeePhoto.setText(privacy[which]);

                        //Nos bajamos del servidor el objeto contact con nuetro numero de telefono
                        //Lo editamos metiendole en privacidad este nuevo campo
                        //y lo actualizamos en el servidor
                        editor.commit();
                    }
                });
        builder.create().show();

    }


    public void settingSeeDescription(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_setting_see_description)
                .setItems(privacy, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putString(getString(R.string.key_privacy), privacy[which]);
                        optionSeeDescription.setText(privacy[which]);
                        editor.commit();
                    }
                });
        builder.create().show();

        Manager.syncOurSelves(this);
    }

    public void settingSeeNotificationMessage(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_setting_notification)
                .setItems(notificationMessage, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putString(getString(R.string.str_setting_notification), notificationMessage[which]);
                        optionSeeNotificationMessage.setText(notificationMessage[which]);
                        editor.commit();
                    }
                });
        builder.create().show();

    }

    public void settingSeeNotificationGroup(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_setting_notification_group)
                .setItems(notificationMessage, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putString(getString(R.string.str_setting_notification_group), notificationMessage[which]);
                        optionSeeNotificationGroup.setText(notificationMessage[which]);
                        editor.commit();
                    }
                });
        builder.create().show();

    }


    public void settingVibrationMessage(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_setting_vibration)
                .setItems(durationVibration, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putString(getString(R.string.str_setting_vibration), durationVibration[which]);
                        optionVibrationMessage.setText(preferences.getString(getString(R.string.str_setting_vibration), durationVibration[which]));
                        editor.commit();
                    }
                });
        builder.create().show();
    }

    public void settingVibrationGroup(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_setting_vibration)
                .setItems(durationVibration, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putString(getString(R.string.str_setting_vibration), durationVibration[which]);
                        optionVibrationGroup.setText(preferences.getString(getString(R.string.str_setting_vibration), durationVibration[which]));
                        editor.commit();
                    }
                });
        builder.create().show();
    }

    public void settingFontSize(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_setting_font_size_info)
                .setItems(sizeFont, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putString(getString(R.string.str_setting_font_size), sizeFont[which]);
                        optionFontSize.setText(preferences.getString(getString(R.string.str_setting_font_size), sizeFont[which]));
                        editor.commit();
                    }
                });
        builder.create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Manager.syncOurSelves(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Manager.syncOurSelves(this);
    }
}
