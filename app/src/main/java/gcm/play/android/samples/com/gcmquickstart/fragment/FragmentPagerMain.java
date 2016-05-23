package gcm.play.android.samples.com.gcmquickstart.fragment;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentPagerMain extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;

    private Drawable myDrawable;

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
//                myDrawable=f.getActivity().getResources().getDrawable(R.drawable.ic_chat_white_24dp);
                break;
            case 1:
                f = FragmentMain.newInstance(FragmentMain.CONTACT);
//                myDrawable = f.getActivity().getResources().getDrawable(R.drawable.ic_account_circle_white_24dp);
                break;
        }

        return f;
    }

    @Override
    public CharSequence getPageTitle(int position) {

//        SpannableStringBuilder sb = new SpannableStringBuilder("   " + tabTitles[position]); // space added before text for convenience
//        try {
//            myDrawable.setBounds(5, 5, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
//            ImageSpan span = new ImageSpan(myDrawable, DynamicDrawableSpan.ALIGN_BASELINE);
//            sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        } catch (Exception e) {
//        }
        return tabTitles[position];
    }

}