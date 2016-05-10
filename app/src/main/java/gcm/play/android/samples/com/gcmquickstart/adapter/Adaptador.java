package gcm.play.android.samples.com.gcmquickstart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gcm.play.android.samples.com.gcmquickstart.R;
import gcm.play.android.samples.com.gcmquickstart.pojo.Chat;
import gcm.play.android.samples.com.gcmquickstart.pojo.Contact;

/**
 * Created by Admin on 28/04/2016.
 */
public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolderAdaptador> {

    private List<Contact> contacts;
    private List<Chat> chats;

    public Adaptador(List<Contact> contacts, List<Chat> chats) {
        Log.v("ASDF", "Adapter: onCreateAdapter");
        this.contacts = contacts;
        this.chats = chats;
    }

    @Override
    public ViewHolderAdaptador onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("ASDF", "onCreateViewHolder");
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
//
//        ViewHolderAdaptador tvh = new ViewHolderAdaptador(itemView);
        LayoutInflater i = (LayoutInflater) parent.getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View view = i.inflate(R.layout.item_main, null);

        ViewHolderAdaptador tvh = new ViewHolderAdaptador(view);

        return tvh;
    }

    @Override
    public void onBindViewHolder(ViewHolderAdaptador viewHolder, int pos) {
        Log.v("ASDF", "Adapter: onBindHolder");
        if (chats == null) {
            Contact contact = contacts.get(pos);
            Log.v("ASDF","Adapter: "+ contact.getName());
            viewHolder.bindContact(contact.getName());
        } else {
            Chat chat = chats.get(0);
            for (Contact current : contacts) {
                if (chat.getTokenconversation().equals(current.getToken()))
                    viewHolder.bindChat(current.getName());
            }
        }
    }


    @Override
    public int getItemCount() {
        Log.v("ASDF", "Adapter: tamaño");
        int aux;
        if (chats != null)
            aux = chats.size();
        else
            aux = contacts.size();
        return aux;
    }


    public static class ViewHolderAdaptador extends RecyclerView.ViewHolder {
        private TextView txtPerson;

        public ViewHolderAdaptador(View itemView) {
            super(itemView);
            Log.v("ASDF", "Adapter.ViewHolder: ViewHolder");
            txtPerson = (TextView) itemView.findViewById(R.id.itemName);
        }

        public void bindContact(String mParam1) {
            txtPerson.setText(mParam1);
        }

        public void bindChat(String mParam1) {
            txtPerson.setText(mParam1);
        }
    }
}

