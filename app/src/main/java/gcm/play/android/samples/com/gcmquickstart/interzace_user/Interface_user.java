package gcm.play.android.samples.com.gcmquickstart.interzace_user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import gcm.play.android.samples.com.gcmquickstart.R;

/**
 * Created by Carmen on 10/05/2016.
 */
public class Interface_user extends AppCompatActivity {



    private Titular titular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //App bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getSupportActionBar().openOptionsMenu();
        getSupportActionBar().setTitle("Contacto");
        titular = new Titular("Carme", "9873763876", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit.");

        //RecyclerView
        RecyclerView lstLista = (RecyclerView)findViewById(R.id.lstLista);

        ArrayList<Titular> datos = new ArrayList<>();

        datos.add(titular);

        AdaptadorTitulares adaptador = new AdaptadorTitulares(datos);
        lstLista.setAdapter(adaptador);

        lstLista.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    //...
}

