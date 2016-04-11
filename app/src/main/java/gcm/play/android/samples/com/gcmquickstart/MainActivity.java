/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gcm.play.android.samples.com.gcmquickstart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    private EditText mandar;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    private TextView mInformationTextView;
    private boolean isReceiverRegistered;

    private GoogleCloudMessaging gcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    mInformationTextView.setText(getString(R.string.gcm_send_message));
                } else {
                    mInformationTextView.setText(getString(R.string.token_error_message));
                }
            }
        };
        mInformationTextView = (TextView) findViewById(R.id.informationTextView);

        // Registering BroadcastReceiver
        registerReceiver();

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    private void registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }
    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    public void onClick(final View view) {
        if (view == findViewById(R.id.send)) {

            new AsyncTask() {

                private String aux;
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    mandar= (EditText) findViewById(R.id.editText);
                    aux=mandar.getText().toString();
                }

                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        // Prepare JSON containing the GCM message content. What to send and where to send.
                        JSONObject jGcmData = new JSONObject();
                        JSONObject jData = new JSONObject();
                        jData.put("message", aux);
                        jData.put("origen","Carmen");
                        // Where to send GCM message.

                        jGcmData.put("to", "daTVtHPsOog:APA91bFIFoKLuRm1zC-E6_F74AEnwSjckxI-LlzldRunNjPLdxanky-X8gFUz230nlXVPW7MoW4FQ_VsAdjj5-27SVXvKlfe80FzzSOBTNXamXPACnn0v_rpXC2iS3daBtyGCA8RrpvK");

                        // What to send in GCM message.
                        jGcmData.put("data", jData);

                        // Create connection to send GCM Message request.
                        URL url = new URL("https://android.googleapis.com/gcm/send");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestProperty("Authorization", "key=" + "AIzaSyDGfemBxn5VyoWjxVGBXhUPgQBhPVGfRd8");
                        conn.setRequestProperty("Content-Type", "application/json");
                        conn.setRequestMethod("POST");
                        conn.setDoOutput(true);

                        // Send GCM message content.
                        OutputStream outputStream = conn.getOutputStream();
                        outputStream.write(jGcmData.toString().getBytes());

                        // Read GCM response.
                        InputStream inputStream = conn.getInputStream();
                        String resp = "";
                        System.out.println(resp);
                        System.out.println("Check your device/emulator for notification or logcat for " +
                                "confirmation of the receipt of the GCM message.");
                    } catch (IOException e) {
                        System.out.println("Unable to send GCM message.");
                        System.out.println("Please ensure that API_KEY has been replaced by the server " +
                                "API key, and that the device's registration token is correct (if specified).");
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return "";
                }
            }.execute(null, null, null);

        }
    }

}
