package gcm.play.android.samples.com.gcmquickstart;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.util.Date;
import java.util.List;

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
        String message = data.getString("message");
        String tokenSender = data.getString("origin");
        Log.d(TAG, getBaseContext().getString(R.string.str_tag_from) + tokenSender);
        Log.d(TAG, getBaseContext().getString(R.string.str_tag_message) + message);


        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */
        DBHelper helper = OpenHelperManager.getHelper(getBaseContext(), DBHelper.class);
        Dao dao;
        List<Contact> contact=null;
        String personORtlf="";

        /**
         * Lanzamos notificacion, para ello vemos si esta guardado o no
         */
        try  {
            dao = helper.getChatDao();
            contact = dao.queryForEq(Contact.TOKEN, tokenSender);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            Log.e("Helper", "Search user error");
        }

        if(!contact.isEmpty())
            if(!contact.get(0).getName().isEmpty())
                personORtlf=contact.get(0).getName();
            else
                personORtlf=contact.get(0).getTelephone().get(0);
        else
            personORtlf="Unknow";

        sendNotification(personORtlf, message);


        /**
         * Guardamos en la base de datos el mensaje
         * */
        Date hoy = new Date();
        try {
            dao = helper.getChatDao();
            Chat user = new Chat(message, tokenSender, tokenSender, hoy.getDay() + "/" + hoy.getMonth() + "/" + (1900 + hoy.getYear()), hoy.getHours() + ":" + hoy.getMinutes());
            dao.create(user);
        } catch (SQLException e) {
            Log.e("Helper", "Create user ERROR");
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

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
    private void sendNotification(String from, String message) {
        Intent intent = new Intent(this, MainActivity.class);
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
}
