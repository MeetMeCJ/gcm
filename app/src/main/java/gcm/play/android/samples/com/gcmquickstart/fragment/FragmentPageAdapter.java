package gcm.play.android.samples.com.gcmquickstart.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class FragmentPageAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;

    private String tabTitles[] = new String[]{"Chat", "Contacts"};

    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
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
                Log.v("ASDF", "FragmentPageAdapter: Chat");
                f = FragmentMain.newInstance(FragmentMain.CHAT);
                break;
            case 1:
                Log.v("ASDF", "FragmentPageAdapter: Contact");
                f = FragmentMain.newInstance(FragmentMain.CONTACT);
                break;
        }
        if (f != null)
            Log.v("ASDF", "FragmentPageAdapter: fragmento no nulo");
        return f;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}