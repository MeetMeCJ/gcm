package gcm.play.android.samples.com.gcmquickstart.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import gcm.play.android.samples.com.gcmquickstart.R;
import gcm.play.android.samples.com.gcmquickstart.pojo.Chat;

/**
 * Created by Admin on 17/05/2016.
 */
public class AdapterConversation extends RecyclerView.Adapter<AdapterConversation.ViewHolderAdaptador> {

    private List<Chat> chats;

    public AdapterConversation(List<Chat> chats) {
        this.chats = chats;
    }

    @Override
    public ViewHolderAdaptador onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater i = (LayoutInflater) parent.getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View view = i.inflate(R.layout.item_conversation, null);

        ViewHolderAdaptador tvh = new ViewHolderAdaptador(view);

        return tvh;
    }

    @Override
    public void onBindViewHolder(ViewHolderAdaptador viewHolder, int pos) {

        Chat chat = chats.get(pos);

        viewHolder.bindChat(chat);
    }


    @Override
    public int getItemCount() {
        return chats.size();
    }


    public static class ViewHolderAdaptador extends RecyclerView.ViewHolder {
        private TextView txtMessage;
        private TextView txtConnection;

        private LinearLayout linearLayout;
        private LinearLayout itemConversationParent;
        private Chat chat;
        private View itemView;

        public ViewHolderAdaptador(View itemView) {
            super(itemView);
            this.itemView = itemView;

            linearLayout = (LinearLayout) itemView.findViewById(R.id.itemConversationLayout);
            itemConversationParent = (LinearLayout) itemView.findViewById(R.id.itemConversationParent);
            txtConnection = (TextView) itemView.findViewById(R.id.itemConversationTime);
            txtMessage = (TextView) itemView.findViewById(R.id.itemConversationMessage);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public void bindChat(Chat chat) {
            this.chat = chat;
            if (chat.getTokensender().equals(chat.getTokenconversation())) {
                linearLayout.setBackground(itemView.getContext().getResources().getDrawable(R.drawable.shape_conversation_message_other));
                itemConversationParent.setGravity(Gravity.START);
                linearLayout.setGravity(Gravity.START);
            } else {
                linearLayout.setBackground(itemView.getContext().getResources().getDrawable(R.drawable.shape_conversation_message_myself));
                linearLayout.setGravity(Gravity.END);
            }
            txtMessage.setText(chat.getMessage());
            txtConnection.setText(chat.getTime());
        }
    }
}

