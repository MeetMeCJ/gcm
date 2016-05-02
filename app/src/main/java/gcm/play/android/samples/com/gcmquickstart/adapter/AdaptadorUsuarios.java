package gcm.play.android.samples.com.gcmquickstart.adapter;

/**
 * Created by Admin on 22/04/2016.
 */
/*
public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.UsuariosViewHolder> {

    private Cursor cursor;

    public AdaptadorUsuarios(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public UsuariosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);

        UsuariosViewHolder tvh = new UsuariosViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(UsuariosViewHolder viewHolder, int pos) {
        cursor.moveToPosition(pos);

        viewHolder.bindConversacion(cursor.getString(cursor.getColumnIndex(Contrato.TablaUsuario.NAME)));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    public static class UsuariosViewHolder extends RecyclerView.ViewHolder {

        private TextView txtPersona;

        public UsuariosViewHolder(View itemView) {
            super(itemView);
            txtPersona = (TextView) itemView.findViewById(R.id.nombre);
        }

        public void bindConversacion(String t) {
            txtPersona.setText(t);
        }
    }
}
*/