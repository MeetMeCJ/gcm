package gcm.play.android.samples.com.gcmquickstart;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
    private TextView mensajes;
    private String nuestroToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversacion);

        mensajes = (TextView) findViewById(R.id.textView);

        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.preference), Context.MODE_PRIVATE);

        nuestroToken = prefs.getString(getResources().getString(R.string.token), "");
    }

    public void onClick(final View view) {

        new AsyncTask() {

            private String aux;
            private Cursor c;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mandar = (EditText) findViewById(R.id.editText);
                aux = mandar.getText().toString();
                Cursor c = getContentResolver().query(Contrato.TablaConversacion.CONTENT_URI, null, null, null, null);

                String men = "";
                if (c.moveToFirst()) {
                    //Esta registrado
                    do {
                        men += c.getString(c.getColumnIndex(Contrato.TablaConversacion.MENSAJE)) + "\n";
                    } while (c.moveToNext());
                    mensajes.setText(men);
                }
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);

                ContentValues cv = new ContentValues();
                cv.put(Contrato.TablaConversacion.MENSAJE, aux);
                cv.put(Contrato.TablaConversacion.TOKENEMISOR, nuestroToken);
                cv.put(Contrato.TablaConversacion.CONVERSACION, "fJpayYvrQkI:APA91bEGuMUi6qlrgzf2SBC3f36q02W-UAbG8gxeDkN0puXq8XnPiQ1STrCuhXytsPGMmC2vATNBKtw_hM8FAXTGXgOPzFlm-57rAsTG6cpqfkw8tHCuOVn61W02AVzSKyO9peRo_CW3");

                Uri u = getContentResolver().insert(Contrato.TablaConversacion.CONTENT_URI, cv);

                c = getContentResolver().query(Contrato.TablaConversacion.CONTENT_URI, null, null, null, null);

                String mensaje = "";
                if (c.moveToFirst()) {
                    //Esta registrado
                    do {
                        mensaje += c.getString(c.getColumnIndex(Contrato.TablaConversacion.MENSAJE)) + "\n";
                    } while (c.moveToNext());
                    mensajes.setText(mensaje);
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
                    jData.put("origen", nuestroToken);
                    // Where to send GCM message.

                    jGcmData.put("to", "fJpayYvrQkI:APA91bEGuMUi6qlrgzf2SBC3f36q02W-UAbG8gxeDkN0puXq8XnPiQ1STrCuhXytsPGMmC2vATNBKtw_hM8FAXTGXgOPzFlm-57rAsTG6cpqfkw8tHCuOVn61W02AVzSKyO9peRo_CW3");

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
