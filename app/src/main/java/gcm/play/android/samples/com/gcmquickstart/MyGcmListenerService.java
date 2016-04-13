package gcm.play.android.samples.com.gcmquickstart;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.Date;

import gcm.play.android.samples.com.gcmquickstart.db.Contrato;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        String tokenSender = data.getString("origen");
        Log.d(TAG, "From: " + tokenSender);
        Log.d(TAG, "Message: " + message);


        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */
        Cursor c = getContentResolver().query(Contrato.TablaUsuario.CONTENT_URI, null, Contrato.TablaUsuario.TOKEN + " = ?",
                new String[]{tokenSender + ""}, null);
        if (c.moveToFirst()) {
            //Esta registrado
            String nombre = "";
            String telefono = "";
            do {
                nombre = c.getString(c.getColumnIndex(Contrato.TablaUsuario.NOMBRE));
                telefono = c.getString(c.getColumnIndex(Contrato.TablaUsuario.TELEFONO));
            } while (c.moveToNext());
            /**
             * In some cases it may be useful to show a notification indicating to the user
             * that a message was received.
             */
            sendNotification(nombre, message);
        } else {
            //No esta registrado , por lo tanto se buscara el numero de telefono en el servidor nuestro
            sendNotification("951753456", message);
        }

        /**
         * Guardamos en la base de datos el mensaje
         * */

        Date dfecha = new Date();
        String sfecha = dfecha.getDay() + "/" + dfecha.getMonth() + "/" + dfecha.getYear();
        String hora = dfecha.getHours() + ":" + dfecha.getMinutes();



        ContentValues cv = new ContentValues();
        cv.put(Contrato.TablaConversacion.MENSAJE, message);
        cv.put(Contrato.TablaConversacion.TOKEN, tokenSender);
        cv.put(Contrato.TablaConversacion.FECHA, sfecha);
        cv.put(Contrato.TablaConversacion.HORA, hora);

        getContentResolver().insert(Contrato.TablaConversacion.CONTENT_URI, cv);


        // [END_EXCLUDE]
    }
    // [END receive_message]

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
