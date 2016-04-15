package gcm.play.android.samples.com.gcmquickstart;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import gcm.play.android.samples.com.gcmquickstart.db.Contrato;

public class Conversacion extends AppCompatActivity {

    private EditText mandar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversacion);
    }
    public void onClick(final View view) {
        if (view == findViewById(R.id.send)) {

            new AsyncTask() {

                private String aux;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    mandar = (EditText) findViewById(R.id.editText);
                    aux = mandar.getText().toString();

                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    Cursor c = getContentResolver().query(Contrato.TablaConversacion.CONTENT_URI, null, null,
                            null, null);

                    String mensaje = "";
                    if (c.moveToFirst()) {
                        //Esta registrado

                        do {
                            mensaje += c.getString(c.getColumnIndex(Contrato.TablaConversacion.MENSAJE)) + "\n";
                        } while (c.moveToNext());
                        //mInformationTextView.setText(mensaje);
                    }
                    mandar.setText("");
                }

                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        // Prepare JSON containing the GCM message content. What to send and where to send.
                        JSONObject jGcmData = new JSONObject();
                        JSONObject jData = new JSONObject();
                        jData.put("message", aux);
                        jData.put("origen", "Nuestro Token");
                        // Where to send GCM message.

                        jGcmData.put("to", "ckHzgRiOBBU:APA91bFh0zvC1jXGW2itL7-xidHTVkRGmSI8BmzbqQXIPJUPML3ks9Iq797C-pF6SV_xcgSahJ6gDV0L0jUYkMtg9mRvZlS6ScAdnlWQFbngi0wua7M6COMNw1LIak4eo5yzkjA7SlTn");

                        // What to send in GCM message.
                        jGcmData.put("data", jData);

                        // Create connection to send GCM Message request.
                        URL url = new URL("https://android.googleapis.com/gcm/send");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestProperty("Authorization", "key=" + "AIzaSyDGfemBxn5VyoWjxVGBXhUPgQBhPVGfRd8");
                        conn.setRequestProperty("Content-Type", "application/json");
                        conn.setRequestMethod("POST");
                        conn.setDoOutput(true);

                        // Send GCM message content.
                        OutputStream outputStream = conn.getOutputStream();
                        outputStream.write(jGcmData.toString().getBytes());

                        // Read GCM response.
                        InputStream inputStream = conn.getInputStream();
                        String resp = "";
                        System.out.println(resp);
                        System.out.println("Check your device/emulator for notification or logcat for " +
                                "confirmation of the receipt of the GCM message.");
                    } catch (IOException e) {
                        System.out.println("Unable to send GCM message.");
                        System.out.println("Please ensure that API_KEY has been replaced by the server " +
                                "API key, and that the device's registration token is correct (if specified).");
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return "";
                }
            }.execute(null, null, null);
        }
    }
}
