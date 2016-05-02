package gcm.play.android.samples.com.gcmquickstart;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import gcm.play.android.samples.com.gcmquickstart.db.DBHelper;
import gcm.play.android.samples.com.gcmquickstart.pojo.Contact;

/**
 * Created by Admin on 20/04/2016.
 */
public class Gestor {
    public static final String API_KEY = "AIzaSyBOWMgkVq6efI1uZQsL_wcGZeHK5bBea1k";

    public static void sendMessage(Context c, final String mensaje, final String destino) {

        SharedPreferences prefs = c.getSharedPreferences(c.getResources().getString(R.string.preference), Context.MODE_PRIVATE);
        final String nuestroToken = prefs.getString(c.getResources().getString(R.string.str_token), "");

        new AsyncTask() {

            private String aux;

            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    // Prepare JSON containing the GCM message content. What to send and where to send.
                    JSONObject jGcmData = new JSONObject();
                    JSONObject jData = new JSONObject();
                    jData.put("message", mensaje);
                    jData.put("origen", nuestroToken);
                    // Where to send GCM message.

                    jGcmData.put("to", destino);

                    // What to send in GCM message.
                    jGcmData.put("data", jData);

                    // Create connection to send GCM Message request.
                    URL url = new URL("https://android.googleapis.com/gcm/send");
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

    public static void registrationToServer(Context c, String token) {

        Log.v("ASDF", "Gestor registrando token");

        String urlOrigen = "http://192.168.1.35:28914/PruebaMeetMe/servlet";

        SharedPreferences prefs = c.getSharedPreferences(c.getResources().getString(R.string.preference), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(c.getResources().getString(R.string.str_token), token);
        editor.commit();

        String tlf = prefs.getString(c.getResources().getString(R.string.str_telephone), "");

        URL url = null;
        BufferedReader in = null;

        try {
            String destino = urlOrigen + "?op=alta&accion=registrar&tlf=" + tlf + "&token=" + token;
            Log.v("ASDF", "url " + destino);
            url = new URL(destino);
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            in.close();

            editor.putBoolean(c.getResources().getString(R.string.str_register), true);
            editor.commit();

        } catch (MalformedURLException e) {
            Log.e("ASDF", "error1 " + e.toString());
        } catch (IOException e) {
            Log.e("ASDF", "error2 " + e.toString());
        }
        Log.v("ASDF", "Fin gestor ");

        Gestor.syncContact(c);
    }

    public static void syncContact(final Context context) {
        Log.v("ASDF", "Sincornizando contactos");
        List<Contact> listaUser = getListaContactos(context);

        for (final Contact contact : listaUser) {
            for (final String telefono : contact.getTelefono()) {
                new AsyncTask() {

                    @Override
                    protected Object doInBackground(Object[] params) {
                        String urlOrigen = "http://192.168.1.35:28914/PruebaMeetMe/servlet";

                        URL url = null;
                        BufferedReader in = null;
                        String res = "";

                        try {
                            String destino = urlOrigen + "?op=consulta&accion=tlf&tlf=" + telefono.replace("+34", "").replace(" ", "");
                            Log.v("ASDF", "url " + destino);
                            url = new URL(destino);
                            in = new BufferedReader(new InputStreamReader(url.openStream()));
                            String linea;

                            while ((linea = in.readLine()) != null) {
                                res += linea;
                            }

                            in.close();
                            Log.v("ASDF", "json " + res);

                            if (!res.contains("false")) {
                                JSONObject obj = new JSONObject(res);
                                Contact contactServer = Contact.getUsuario(obj.getJSONObject("r"));
                                contactServer.setNombre(contact.getNombre());

                                DBHelper helper = OpenHelperManager.getHelper(context, DBHelper.class);
                                Dao dao;
                                List<Contact> contact = null;

                                try {
                                    dao = helper.getChatDao();
                                    contact = dao.queryForAll();

                                    if (!contact.isEmpty()) {
                                        for (Contact currentContact : contact) {
                                            String telf = currentContact.getTelefono().get(0).replace(" ", "");
                                            Long id = currentContact.getId();

                                            if (telf.contains(contactServer.getTelefono().get(0).toString())) {
                                                //dao.update(contactServer);
                                                Log.v("ASDF", "sync update");
                                            } else {
                                                dao.create(contactServer);
                                                Log.v("ASDF", "sync insert");
                                            }

                                        }
                                    } else {
                                        dao.create(contactServer);
                                        Log.v("ASDF", "sync insert");
                                    }
                                } catch (java.sql.SQLException e) {
                                    e.printStackTrace();
                                    Log.e("Helper", "Search user error");
                                }

                            }

                            Thread.sleep(300);


                        } catch (
                                MalformedURLException e
                                )

                        {
                            Log.e("ASDF", "error3 " + e.toString());
                        } catch (
                                IOException e
                                )

                        {
                            Log.e("ASDF", "error4 " + e.toString());
                        } catch (
                                JSONException e
                                )

                        {
                            Log.e("ASDF", "error5 " + e.toString());
                        } catch (
                                InterruptedException e
                                )

                        {
                            Log.e("ASDF", "error6 " + e.toString());
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        Log.v("ASDF", "Fin sincornizando contactos");
                    }
                }.execute(null, null, null);
            }
        }

    }

    public static List<Contact> getListaContactos(Context contexto) {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String proyeccion[] = null;
        String seleccion = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = ? and " +
                ContactsContract.Contacts.HAS_PHONE_NUMBER + "= ?";
        String argumentos[] = new String[]{"1", "1"};
        String orden = ContactsContract.Contacts.DISPLAY_NAME + " collate localized asc";
        Cursor cursor = contexto.getContentResolver().query(uri, proyeccion, seleccion, argumentos, orden);
        int indiceId = cursor.getColumnIndex(ContactsContract.Contacts._ID);
        int indiceNombre = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        List<Contact> lista = new ArrayList<>();
        Contact contacto;
        while (cursor.moveToNext()) {
            contacto = new Contact();
            contacto.setId(cursor.getLong(indiceId));
            contacto.setNombre(cursor.getString(indiceNombre));
            contacto.setTelefono(getListaTelefono(contexto, contacto.getId()));
            lista.add(contacto);
        }
        return lista;
    }

    public static List<String> getListaTelefono(Context contexto, long id) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String proyeccion[] = null;
        String seleccion = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?";
        String argumentos[] = new String[]{id + ""};
        String orden = ContactsContract.CommonDataKinds.Phone.NUMBER;
        Cursor cursor = contexto.getContentResolver().query(uri, proyeccion, seleccion, argumentos, orden);
        int indiceNumero = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        List<String> lista = new ArrayList<>();
        String numero;
        while (cursor.moveToNext()) {
            numero = cursor.getString(indiceNumero);
            lista.add(numero);
        }
        return lista;
    }
}
