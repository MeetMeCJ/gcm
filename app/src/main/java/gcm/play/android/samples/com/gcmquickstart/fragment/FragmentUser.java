package gcm.play.android.samples.com.gcmquickstart.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gcm.play.android.samples.com.gcmquickstart.R;
import gcm.play.android.samples.com.gcmquickstart.adapter.AdaptadorUsuarios;
import gcm.play.android.samples.com.gcmquickstart.db.Contrato;


public class FragmentUser extends Fragment {

    private Context c;

    public static FragmentUser newInstance(Context c) {
        FragmentUser fragment = new FragmentUser();
        return fragment;
    }

    public FragmentUser() {
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

        Cursor usuarios = c.getContentResolver().query(Contrato.TablaUsuario.CONTENT_URI, null, null, null, null);

        Log.v("ASDF", "FragmentUser " + usuarios.getCount());

        AdaptadorUsuarios adaptador = new AdaptadorUsuarios(usuarios);
        rv.setAdapter(adaptador);

        rv.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false));

        return v;
    }

}