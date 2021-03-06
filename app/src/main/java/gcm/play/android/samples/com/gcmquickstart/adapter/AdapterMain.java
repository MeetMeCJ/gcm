package gcm.play.android.samples.com.gcmquickstart.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gcm.play.android.samples.com.gcmquickstart.ui.ConversationActivity;
import gcm.play.android.samples.com.gcmquickstart.manager.Manager;
import gcm.play.android.samples.com.gcmquickstart.R;
import gcm.play.android.samples.com.gcmquickstart.pojo.Chat;
import gcm.play.android.samples.com.gcmquickstart.pojo.Contact;

/**
 * Created by Admin on 28/04/2016.
 */
public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolderAdaptador> {

    private List<Contact> contacts;
    private List<Chat> chats;

    public AdapterMain(List<Contact> contacts, List<Chat> chats) {
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
            Chat chat = chats.get(chats.size()-1);
            for (Contact current : contacts) {
                if (chat.getTokenconversation().equals(current.getToken()))
                    viewHolder.bindChat(current, chat);
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
        private TextView txtMessage;
        private TextView txtConnection;
        private Contact contact;

        public ViewHolderAdaptador(View itemView) {
            super(itemView);
            txtPerson = (TextView) itemView.findViewById(R.id.itemName);
            txtConnection = (TextView) itemView.findViewById(R.id.itemConnection);
            txtMessage = (TextView) itemView.findViewById(R.id.itemMessage);
            itemView.setOnClickListener(this);
        }

        public void bindContact(Contact contact) {
            this.contact = contact;
            txtPerson.setText(contact.getName());
            txtMessage.setText(contact.getDescription());
            txtConnection.setText(Html.fromHtml(contact.getLastconnection()+"."));
        }

        public void bindChat(Contact contact, Chat chat) {
            this.contact = contact;
            txtPerson.setText(contact.getName());
            txtMessage.setText(chat.getMessage());
            txtConnection.setText(chat.getDate() + " " + chat.getTime());
        }

        @Override
        public void onClick(View v) {
            Manager.updateContact(v.getContext(), contact);
            Manager.syncOurSelves(v.getContext());
            Intent i = new Intent(v.getContext(), ConversationActivity.class);
            i.putExtra(v.getContext().getString(R.string.key_token), contact);
            v.getContext().startActivity(i);
        }
    }
}

