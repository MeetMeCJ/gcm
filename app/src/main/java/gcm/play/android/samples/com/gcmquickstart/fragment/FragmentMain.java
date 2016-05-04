package gcm.play.android.samples.com.gcmquickstart.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import gcm.play.android.samples.com.gcmquickstart.R;
import gcm.play.android.samples.com.gcmquickstart.adapter.Adapter;
import gcm.play.android.samples.com.gcmquickstart.db.DBHelper;
import gcm.play.android.samples.com.gcmquickstart.pojo.Chat;
import gcm.play.android.samples.com.gcmquickstart.pojo.Contact;

/**
 * Created by Admin on 28/04/2016.
 */
public class FragmentMain extends Fragment {
    public static final int CHAT =1;
    public static final int CONTACT =2;
    private static final String TYPE="type";

    private Context c;
    private List<Contact> contacts;
    private List<Chat> chats;

    public static FragmentMain newInstance(int fragmentType){//List<Contact> contacts, List<Chat> chats) {
        FragmentMain fragment = new FragmentMain();
        Bundle args = new Bundle();
        args.putInt(TYPE, fragmentType);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentMain() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = getContext();


        DBHelper helper = OpenHelperManager.getHelper(c, DBHelper.class);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user, container, false);

        RecyclerView rv = (RecyclerView) v.findViewById(R.id.RecyclerView);
        Adapter adaptador=null;
        if (getArguments().getInt(TYPE)==1)
            adaptador = new Adapter(contacts,chats);
        else
            adaptador = new Adapter(contacts);
        rv.setAdapter(adaptador);

        rv.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false));

        return v;
    }

}