package gcm.play.android.samples.com.gcmquickstart.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.util.Date;
import java.util.List;

import gcm.play.android.samples.com.gcmquickstart.ConversationActivity;
import gcm.play.android.samples.com.gcmquickstart.QuickstartPreferences;
import gcm.play.android.samples.com.gcmquickstart.R;
import gcm.play.android.samples.com.gcmquickstart.db.DBHelper;
import gcm.play.android.samples.com.gcmquickstart.pojo.Chat;
import gcm.play.android.samples.com.gcmquickstart.pojo.Contact;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    @Override
    public void onMessageReceived(String from, Bundle data) {
        DBHelper helper = OpenHelperManager.getHelper(getBaseContext(), DBHelper.class);

        String message = data.getString("message");
        String tokenSender = data.getString("origin");
        String telephoneSender = data.getString("telephone");

        Log.d(TAG, getBaseContext().getString(R.string.str_tag_from) + tokenSender);
        Log.d(TAG, getBaseContext().getString(R.string.str_tag_message) + message);


        if (from.startsWith("/topics/")) {
        } else {
        }

        Contact contact = null;
        String personORtlf = "";

        contact = searchByToken(helper, tokenSender);


        if (contact != null)
            if (!contact.getName().isEmpty())
                personORtlf = contact.getName();
            else
                personORtlf = contact.getTelephone();
        else {
            contact = searchByTelephone(helper, telephoneSender);
            if (contact == null)
                registerContact(helper, tokenSender, telephoneSender);
            else
                updateContact(helper, tokenSender, contact);
            personORtlf = contact.getTelephone();
        }

        SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.preference), Context.MODE_PRIVATE);

        if (preferences.getBoolean(getResources().getString(R.string.str_register_broadcast), false)) {
            Intent registrationComplete = new Intent(QuickstartPreferences.CONVERSATION);
            LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
        } else {
            sendNotification(personORtlf, message, contact);

        }


        registerMessage(helper, message, tokenSender);

        /**
         * Liberamos al ayudante
         */
        if (helper != null) {
            OpenHelperManager.releaseHelper();
            helper = null;
        }
    }

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String from, String message, Contact contact) {
        Intent intent = new Intent(this, ConversationActivity.class);
        intent.putExtra(getString(R.string.str_token), contact);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle(from)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    /**
     * Registra un contacto nuevo o actualiza una ya existente.
     *
     * @param helper
     * @param tokenSender
     * @param telephoneSender
     */
    public void registerContact(DBHelper helper, String tokenSender, String telephoneSender) {
        try {
            Dao dao = helper.getContactDao();

            Contact newContact = new Contact();
            newContact.setTelephone(telephoneSender);
            newContact.setToken(tokenSender);

            dao.createOrUpdate(newContact);

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Guardamos en la base de datos el mensaje
     */
    public void registerMessage(DBHelper helper, String message, String tokenSender) {
        Date hoy = new Date();
        try {
            Dao dao = helper.getChatDao();
            Chat user = new Chat(message, tokenSender, tokenSender, hoy.getDay() + "/" + hoy.getMonth() + "/" + (1900 + hoy.getYear()), hoy.getHours() + ":" + hoy.getMinutes());
            dao.create(user);
        } catch (SQLException e) {
            Log.e("Helper", "Create user ERROR");
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Actualizamos el token del usuario y de los mensajes en caso de encontrar algun usuario por el telefono
     * pero no por el token.
     * @param helper
     * @param tokenSender
     * @param contact
     */
    public void updateContact(DBHelper helper, String tokenSender, Contact contact) {
        String oldToeken = "";
        try {
            Dao dao = helper.getContactDao();
            oldToeken = contact.getToken();
            contact.setToken(tokenSender);

            dao.update(contact);

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        try {
            Dao dao = helper.getChatDao();
            List<Chat> listChat = dao.queryForEq(Chat.CONVERSATION, oldToeken);
            for (Chat currentChat : listChat) {
                currentChat.setTokenconversation(tokenSender);

                if (currentChat.getTokensender().equals(oldToeken))
                    currentChat.setTokensender(tokenSender);

                dao.update(currentChat);
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

    public Contact searchByToken(DBHelper helper, String tokenSender) {
        List<Contact> contacts = null;
        Contact contact = null;
        try {
            Dao dao = helper.getContactDao();
            contacts = dao.queryForEq(Contact.TOKEN, tokenSender);

            if (!contacts.isEmpty() || contact != null)
                contact = contacts.get(0);

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            Log.e("Helper", "Search user error");
        }
        return contact;
    }

    public Contact searchByTelephone(DBHelper helper, String telephoneSender) {
        List<Contact> contacts = null;
        Contact contact = null;
        try {
            Dao dao = helper.getContactDao();
            contacts = dao.queryForEq(Contact.TELEPHONE, telephoneSender);

            if (!contacts.isEmpty())
                contact = contacts.get(0);

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            Log.e("Helper", "Search user error");
        }
        return contact;
    }
}
