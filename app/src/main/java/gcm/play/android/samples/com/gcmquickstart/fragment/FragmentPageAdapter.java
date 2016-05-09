package gcm.play.android.samples.com.gcmquickstart.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class FragmentPageAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private Context c;
    private String tabTitles[] = new String[]{"Chat", "Contacts",};

    public FragmentPageAdapter(FragmentManager fm, Context c) {
        super(fm);
        this.c = c;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment f = null;

        switch (position) {
            case 0:
                Log.v("ASDF", "Chat");
                f = FragmentMain.newInstance(FragmentMain.CHAT);
                break;
            case 1:
                Log.v("ASDF", "Contact");
                f = FragmentMain.newInstance(FragmentMain.CONTACT);
                break;
        }
        if (f != null)
            Log.v("ASDF", "fragmento no nulo");
        return f;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}