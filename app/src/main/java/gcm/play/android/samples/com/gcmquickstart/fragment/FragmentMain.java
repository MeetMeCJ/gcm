package gcm.play.android.samples.com.gcmquickstart.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import gcm.play.android.samples.com.gcmquickstart.R;
import gcm.play.android.samples.com.gcmquickstart.adapter.Adapter;
import gcm.play.android.samples.com.gcmquickstart.pojo.Chat;
import gcm.play.android.samples.com.gcmquickstart.pojo.Contact;

/**
 * Created by Admin on 28/04/2016.
 */
public class FragmentMain extends Fragment {
    private static final String ARG_CONTACT = "contacts";
    private static final String ARG_CHAT = "chats";

    private Context c;
    private List<Contact> contacts;
    private List<Chat> chats;

    public static FragmentMain newInstance(List<Contact> contacts, List<Chat> chats) {
        FragmentMain fragment = new FragmentMain();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_CONTACT, (ArrayList<? extends Parcelable>) contacts);
        args.putParcelableArrayList(ARG_CHAT, (ArrayList<? extends Parcelable>) chats);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user, container, false);

        RecyclerView rv = (RecyclerView) v.findViewById(R.id.RecyclerView);
        Adapter adaptador=null;
        if (chats.isEmpty())
            adaptador = new Adapter(contacts);
        else
            adaptador = new Adapter(contacts,chats);
        rv.setAdapter(adaptador);

        rv.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false));

        return v;
    }

}