package gcm.play.android.samples.com.gcmquickstart.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import gcm.play.android.samples.com.gcmquickstart.db.DBHelper;
import gcm.play.android.samples.com.gcmquickstart.pojo.Chat;
import gcm.play.android.samples.com.gcmquickstart.pojo.Contact;

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
        DBHelper helper = OpenHelperManager.getHelper(c, DBHelper.class);
        List<Contact> contacts=null;
        List<Chat> chats=null;
        Dao dao;
        try {
            dao = helper.getChatDao();
            chats = dao.queryForAll();
            dao=helper.getContactDao();
            contacts=dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("Helper", "Search user error");
        }

        if (helper != null) {
            OpenHelperManager.releaseHelper();
            helper = null;
        }

        switch (position) {
            case 0:
                f = FragmentMain.newInstance(contacts,chats);
                break;
            case 1:
                f = FragmentMain.newInstance(contacts,null);
                break;
        }

        return f;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}