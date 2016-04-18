package gcm.play.android.samples.com.gcmquickstart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListaConversacionesFragment extends Fragment {

    private RecyclerView recyclerView;

    public ListaConversacionesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lista_conversaciones, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler);

        ArrayList<String> aux = new ArrayList<>();
        aux.add("Hola");
        aux.add("Adios");
        Adaptador adt = new Adaptador(aux);

        recyclerView.setAdapter(adt);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return v;
    }
}
