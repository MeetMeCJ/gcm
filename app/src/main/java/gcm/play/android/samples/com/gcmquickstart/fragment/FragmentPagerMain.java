package gcm.play.android.samples.com.gcmquickstart.fragment;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentPagerMain extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Chat", "Contacts"};

    public FragmentPagerMain(FragmentManager fm) {
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
                f = FragmentMain.newInstance(FragmentMain.CHAT);
                break;
            case 1:
                f = FragmentMain.newInstance(FragmentMain.CONTACT);
                break;
        }
        return f;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}