package gcm.play.android.samples.com.gcmquickstart.adapter;

import android.support.v7.widget.RecyclerView;
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
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Contact> contacts;
    private List<Chat> chats;

    public Adapter(List<Contact> contact) {
        this.contacts = contact;
    }

    public Adapter(List<Contact> contacts, List<Chat> chats) {
        this.contacts = contacts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);

        ViewHolder tvh = new ViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int pos) {
        if (chats.isEmpty()) {
            Contact contact = contacts.get(pos);
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
        if (chats != null)
            return contacts.size();
        else if(contacts!=null)
            return chats.size();
        return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtPerson;

        public ViewHolder(View itemView) {
            super(itemView);
            txtPerson = (TextView) itemView.findViewById(R.id.txtName);
        }

        public void bindContact(String mParam1) {
            txtPerson.setText(mParam1);
        }

        public void bindChat(String mParam1) {
            txtPerson.setText(mParam1);
        }
    }
}

