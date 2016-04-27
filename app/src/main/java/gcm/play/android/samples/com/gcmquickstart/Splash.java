/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
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
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroViewPager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import gcm.play.android.samples.com.gcmquickstart.fragment.FragmentPageSlider;

public class Splash extends AppIntro2 implements FragmentPageSlider.OnFragmentInteractionListener {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "Splash";

    private EditText telefono;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView mInformationTextView;
    private boolean isReceiverRegistered;
//Esto es un comentario modificado

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        final SharedPreferences prefs =getSharedPreferences(getResources().getString(R.string.preference), Context.MODE_PRIVATE);
        if(prefs.getBoolean(getResources().getString(R.string.str_register),false)){
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        }

        FragmentPageSlider first_fragment = FragmentPageSlider.newInstance(getString(R.string.str_splash_title),getString(R.string.str_splash_content),R.drawable.cartoon2);
        FragmentPageSlider second_fragment = FragmentPageSlider.newInstance(getString(R.string.str_splash_title),getString(R.string.str_splash_content),R.drawable.cartoon10);
        FragmentPageSlider third_fragment = FragmentPageSlider.newInstance(getString(R.string.str_splash_title),getString(R.string.str_splash_content),R.drawable.cartoon12);
        FragmentPageSlider fourth_fragment = FragmentPageSlider.newInstance(getString(R.string.str_splash_title),getString(R.string.str_splash_content),R.drawable.cartoon14);

        addSlide(first_fragment);
        addSlide(second_fragment);
        addSlide(third_fragment);
        addSlide(fourth_fragment);

        setProgressButtonEnabled(true);
        setVibrate(false);
        setFlowAnimation();

        telefono= (EditText) findViewById(R.id.editText);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

                boolean sentToken = sharedPreferences.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken && prefs.getBoolean(getResources().getString(R.string.str_register),false)) {
                    Intent i=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);
                } else {
                    mInformationTextView.setText(getString(R.string.token_error_message));
                }
            }
        };
        mInformationTextView = (TextView) findViewById(R.id.informationTextView);

        // Registering BroadcastReceiver
        registerReceiver();
    }

    @Override
    public void onNextPressed() {
        AppIntroViewPager pager = getPager();
        boolean pagingState = pager.isNextPagingEnabled();
        setSwipeLock(pagingState);
    }

    @Override
    public void onDonePressed() {

    }

    @Override
    public void onSlideChanged() {

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

    /***********************************************************************************************/

    public void registrar(View v){
        if (checkPlayServices()) {

            SharedPreferences prefs =getSharedPreferences(getResources().getString(R.string.preference),Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(getResources().getString(R.string.str_telephone), telefono.getText().toString());
            editor.commit();

            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    /***********************************************************************************************/


    private void registerReceiver() {
        if (!isReceiverRegistered) {
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
                Log.i(TAG, getString(R.string.str_checkplay_tag));
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
