package gcm.play.android.samples.com.gcmquickstart.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gcm.play.android.samples.com.gcmquickstart.R;
import gcm.play.android.samples.com.gcmquickstart.pojo.Chat;

/**
 * Created by Admin on 18/04/2016.
 */
/*
public class AdaptadorChat extends RecyclerView.Adapter<AdaptadorChat.ConversacionesViewHolder> {

    private Cursor conversaciones;
    private Cursor usuarios;

    public AdaptadorChat(Cursor conversaciones, Cursor usuarios) {
        this.conversaciones = conversaciones;
        this.usuarios = usuarios;
    }

    @Override
    public ConversacionesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);

        ConversacionesViewHolder tvh = new ConversacionesViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(ConversacionesViewHolder viewHolder, int pos) {
        conversaciones.moveToPosition(pos);
        Chat chat = new Chat();

        if (usuarios != null)
            usuarios.moveToFirst();
            do {
                if (chat.getTokenconversacion().equals(usuarios.getString(usuarios.getColumnIndex(Contrato.TablaUsuario.NOMBRE)))) {
                    viewHolder.bindConversacion(usuarios.getString(usuarios.getColumnIndex(Contrato.TablaUsuario.NOMBRE)));
                }
            } while (usuarios.moveToNext());
    }

    @Override
    public int getItemCount() {
        return conversaciones.getCount();
    }


    public static class ConversacionesViewHolder extends RecyclerView.ViewHolder {

        private TextView txtPersona;

        public ConversacionesViewHolder(View itemView) {
            super(itemView);
            txtPersona = (TextView) itemView.findViewById(R.id.nombre);
        }

        public void bindConversacion(String t) {
            txtPersona.setText(t);
        }
    }
}
*/