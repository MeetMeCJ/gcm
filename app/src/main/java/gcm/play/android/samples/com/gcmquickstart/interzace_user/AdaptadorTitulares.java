package gcm.play.android.samples.com.gcmquickstart.interzace_user;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import gcm.play.android.samples.com.gcmquickstart.R;

/**
 * Created by Carmen on 07/05/2016.
 */
public class AdaptadorTitulares extends RecyclerView.Adapter<AdaptadorTitulares.TitularesViewHolder> {

    private ArrayList<Titular> datos;

    //...

    public static class TitularesViewHolder
            extends RecyclerView.ViewHolder {

        private EditText etName;
        private EditText etTelephone;
        private EditText etDescription;

        public TitularesViewHolder(View itemView) {
            super(itemView);

            etName= (EditText)itemView.findViewById(R.id.etName);
            etTelephone = (EditText)itemView.findViewById(R.id.etTelephone);
            etDescription = (EditText)itemView.findViewById(R.id.etDescription);
        }

        public void bindTitular(Titular t) {
            etName.setText(t.getName());
            etTelephone.setText(t.getTelephone());
            etDescription.setText(t.getDescription());
        }
    }

    public AdaptadorTitulares(ArrayList<Titular> datos) {
        this.datos = datos;
    }

    @Override
    public TitularesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listitem_titular, viewGroup, false);

        TitularesViewHolder tvh = new TitularesViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(TitularesViewHolder viewHolder, int pos) {
        Titular item = datos.get(pos);

        viewHolder.bindTitular(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    //...
}