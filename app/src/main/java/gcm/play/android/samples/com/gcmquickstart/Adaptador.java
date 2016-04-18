package gcm.play.android.samples.com.gcmquickstart;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 18/04/2016.
 */
public class Adaptador extends RecyclerView.Adapter<Adaptador.ConversacionesViewHolder>{

    private ArrayList<String> datos;

    public Adaptador(ArrayList<String> datos) {
        this.datos = datos;
    }

    @Override
    public ConversacionesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);

        ConversacionesViewHolder tvh = new ConversacionesViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(ConversacionesViewHolder viewHolder, int pos) {
        String item = datos.get(pos);

        viewHolder.bindTitular(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }


    public static class ConversacionesViewHolder
            extends RecyclerView.ViewHolder {

        private TextView txtPersona;

        public ConversacionesViewHolder(View itemView) {
            super(itemView);

            txtPersona = (TextView)itemView.findViewById(R.id.nombre);
        }

        public void bindTitular(String t) {
            txtPersona.setText(t);
        }
    }
}
