package gcm.play.android.samples.com.gcmquickstart;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import gcm.play.android.samples.com.gcmquickstart.fragment.FragmentPagerMain;
import gcm.play.android.samples.com.gcmquickstart.service.SyncContact;

/**
 * Created by Admin on 20/04/2016.
 */
// Jaime
public class MainActivity extends AppCompatActivity {
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentPagerMain(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);

        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.preference), Context.MODE_PRIVATE);
        String ourToken = prefs.getString(getResources().getString(R.string.str_token), "");
        Log.v("ASDF", "NUESTRO TOKEN " + ourToken);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_sync:
                startService(new Intent(this, SyncContact.class));
                return true;
            case R.id.menu_main_setting:
                Intent i = new Intent(this, SettingActivity.class);
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
