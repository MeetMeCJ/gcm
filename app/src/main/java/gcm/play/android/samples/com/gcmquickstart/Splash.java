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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.paolorotolo.appintro.AppIntro2;

import gcm.play.android.samples.com.gcmquickstart.fragment.FragmentPageSlider;

public class Splash extends AppIntro2 implements FragmentPageSlider.OnFragmentInteractionListener {


    private static final String TAG = "Splash";


//Esto es un comentario modificado

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        final SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.preference), Context.MODE_PRIVATE);
        if (prefs.getBoolean(getResources().getString(R.string.str_register), false)) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        FragmentPageSlider first_fragment = FragmentPageSlider.newInstance(getString(R.string.str_splash_title_frag_1), getString(R.string.str_splash_content_frag_1), R.drawable.medal);
        FragmentPageSlider second_fragment = FragmentPageSlider.newInstance(getString(R.string.str_splash_title_frag_2), getString(R.string.str_splash_content_frag_2), R.drawable.message);
        FragmentPageSlider third_fragment = FragmentPageSlider.newInstance(getString(R.string.str_splash_title_frag_3), getString(R.string.str_splash_content_frag_3), R.drawable.smartphone);
        FragmentPageSlider fourth_fragment = FragmentPageSlider.newInstance(getString(R.string.str_splash_title_frag_4), getString(R.string.str_splash_content_frag_4), R.drawable.light_bulb);

        addSlide(first_fragment);
        addSlide(second_fragment);
        addSlide(third_fragment);
        addSlide(fourth_fragment);

        setScrollDurationFactor(4);

        setProgressButtonEnabled(true);
        setVibrate(false);
        setFlowAnimation();


    }

    @Override
    public void onNextPressed() {
    }

    @Override
    public void onDonePressed() {
        Intent i = new Intent(getBaseContext(), PutTelephone.class);
        startActivity(i);
    }

    @Override
    public void onSlideChanged() {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
