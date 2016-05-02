package gcm.play.android.samples.com.gcmquickstart.fragment;

/**
 * Created by Admin on 20/04/2016.
 */
/*
public class FragmentListChat extends Fragment {
    private Context c;

    public static FragmentListChat newInstance(Context c) {
        FragmentListChat fragment = new FragmentListChat();
        return fragment;
    }

    public FragmentListChat() {
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
        View v = inflater.inflate(R.layout.fragment_list_chat, container, false);

        RecyclerView rv = (RecyclerView) v.findViewById(R.id.RecyclerView);

        String[] proyeccion = {"DISTINCT " + Contrato.TablaConversacion.CONVERSACION};

        Cursor conversacion = c.getContentResolver().query(Contrato.TablaConversacion.CONTENT_URI, proyeccion, null, null, null);
        Cursor usuarios = c.getContentResolver().query(Contrato.TablaUsuario.CONTENT_URI, null, null, null, null);

        Log.v("ASDF","FragmentListChat "+conversacion.getCount());

        AdaptadorChat adaptador = new AdaptadorChat(conversacion, usuarios);
        rv.setAdapter(adaptador);

        rv.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false));

        return v;
    }
}
*/

