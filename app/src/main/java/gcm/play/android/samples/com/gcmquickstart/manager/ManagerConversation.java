package gcm.play.android.samples.com.gcmquickstart.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import gcm.play.android.samples.com.gcmquickstart.R;

/**
 * Created by Carmen on 02/06/2016.
 */
public class ManagerConversation {

    public static final String API_KEY = "AIzaSyCF2MH1r1DOBlF3Lz7ma1hNFEQVJldt71U";

    public static void sendMessage(Context contexto, final String message, final String destination) {

        SharedPreferences prefs = contexto.getSharedPreferences(contexto.getResources().getString(R.string.preference), Context.MODE_PRIVATE);
        final String ourToken = prefs.getString(contexto.getResources().getString(R.string.key_token), "");
        final String ourTelephone = prefs.getString(contexto.getString(R.string.key_telephone), "");

        new AsyncTask() {

            private String aux;

            @Override
            protected Object doInBackground(Object[] params) {
                try {

                    // Prepare JSON containing the GCM message content. What to send and where to send.
                    JSONObject jGcmData = new JSONObject();
                    JSONObject jData = new JSONObject();
                    jData.put("message", message);
                    jData.put("origin", ourToken);
                    jData.put("telephone", ourTelephone);
                    // Where to send GCM message.

                    jGcmData.put("to", destination);
                    //jGcmData.put("to", "/topics/global");

                    // What to send in GCM message.
                    jGcmData.put("data", jData);

                    // Create connection to send GCM Message request.
                    //URL url = new URL("https://android.googleapis.com/gcm/send");
                    URL url = new URL("https://gcm-http.googleapis.com/gcm/send");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Authorization", "key=" + API_KEY);
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);

                    // Send GCM message content.
                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(jGcmData.toString().getBytes());

                    // Read GCM response.
                    InputStream inputStream = conn.getInputStream();
                    String resp = IOUtils.toString(inputStream);
                    System.out.println(resp);


                } catch (IOException e) {
                    Log.v("ASDF", "error " + e.toString());

                    e.printStackTrace();
                } catch (JSONException e) {
                    Log.v("ASDF", "error2 " + e.toString());
                    e.printStackTrace();
                }
                return "";
            }
        }.execute(null, null, null);
    }

}
