package gcm.play.android.samples.com.gcmquickstart.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gcm.play.android.samples.com.gcmquickstart.ConversationActivity;
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
        this.contacts = contacts;
        this.chats = chats;
    }

    @Override
    public ViewHolderAdaptador onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater i = (LayoutInflater) parent.getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View view = i.inflate(R.layout.item_main, null);

        ViewHolderAdaptador tvh = new ViewHolderAdaptador(view);

        return tvh;
    }

    @Override
    public void onBindViewHolder(ViewHolderAdaptador viewHolder, int pos) {
        if (chats == null) {
            Contact contact = contacts.get(pos);
            viewHolder.bindContact(contact);
        } else {
            Chat chat = chats.get(0);
            for (Contact current : contacts) {
                if (chat.getTokenconversation().equals(current.getToken()))
                    viewHolder.bindChat(current);
            }
        }
    }


    @Override
    public int getItemCount() {
        int aux;
        if (chats != null)
            aux = chats.size();
        else
            aux = contacts.size();
        return aux;
    }


    public static class ViewHolderAdaptador extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtPerson;
        private Contact contact;

        public ViewHolderAdaptador(View itemView) {
            super(itemView);

            txtPerson = (TextView) itemView.findViewById(R.id.itemName);
            itemView.setOnClickListener(this);
        }

        public void bindContact(Contact contact) {
            txtPerson.setText(contact.getName());
            this.contact = contact;
        }

        public void bindChat(Contact contact) {
            txtPerson.setText(contact.getName());
            this.contact = contact;
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), ConversationActivity.class);
            i.putExtra(v.getContext().getString(R.string.str_token), contact.getToken());
            v.getContext().startActivity(i);
        }
    }
}

