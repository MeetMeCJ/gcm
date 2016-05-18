package gcm.play.android.samples.com.gcmquickstart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.apache.commons.io.IOUtils;
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
import gcm.play.android.samples.com.gcmquickstart.service.SyncContact;

/**
 * Created by Admin on 20/04/2016.
 */
public class Manager {
    public static final String API_KEY = "AIzaSyCF2MH1r1DOBlF3Lz7ma1hNFEQVJldt71U";

    public static void sendMessage(Context contexto, final String message, final String destination) {
        Log.v("ASDF", "EN EL CONVERSATION");

        SharedPreferences prefs = contexto.getSharedPreferences(contexto.getResources().getString(R.string.preference), Context.MODE_PRIVATE);
        final String ourToken = prefs.getString(contexto.getResources().getString(R.string.str_token), "");
        final String ourTelephone = prefs.getString(contexto.getString(R.string.str_telephone), "");

        new AsyncTask() {

            private String aux;

            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Log.v("ASDF", "empieza la hebra a mandar un msg");
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
                    Log.v("ASDF", url.toString());
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

                    Log.v("ASDF", "respuesta " + resp);
                    Log.v("ASDF", "lo manda");


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

        Log.v("ASDF", "Manager registrando token");

        String urlOrigin = "http://192.168.1.34:28914/MeetMe/servlet";

        SharedPreferences prefs = c.getSharedPreferences(c.getResources().getString(R.string.preference), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(c.getResources().getString(R.string.str_token), token);
        editor.commit();

        String tlf = prefs.getString(c.getResources().getString(R.string.str_telephone), "");

        URL url = null;
        BufferedReader in = null;

        try {
            String destination = urlOrigin + "?op=alta&accion=registrar&tlf=" + tlf + "&token=" + token;
            Log.v("ASDF", "url " + destination);
            url = new URL(destination);
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

        if (prefs.getBoolean(c.getResources().getString(R.string.str_register), false))
            c.startService(new Intent(c, SyncContact.class));
        //Manager.syncContact(c);
    }

    public static void syncContact(final Context context) {
        Log.v("ASDF", "Sincornizando contactos");
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                List<Contact> listUser = getListContacts(context);

                for (final Contact telephoneContact : listUser) {
                    String urlOrigin = "http://192.168.1.34:28914/MeetMe/servlet";

                    URL url = null;
                    BufferedReader in = null;
                    String res = "";

                    try {
                        String destination = urlOrigin + "?op=consulta&accion=tlf&tlf=" + telephoneContact.getTelephone().replace("+34", "").replace(" ", "");
                        Log.v("ASDF", "url " + destination);
                        url = new URL(destination);
                        in = new BufferedReader(new InputStreamReader(url.openStream()));
                        String linea;

                        while ((linea = in.readLine()) != null) {
                            res += linea;
                        }

                        in.close();
                        Log.v("ASDF", "json " + res);

                        if (!res.contains("false")) {
                            Log.v("ASDF", "no es false");
                            JSONObject obj = new JSONObject(res);
                            Contact contactServer = Contact.getUsuario(obj.getJSONObject("r"));
                            contactServer.setName(telephoneContact.getName());

                            DBHelper helper = OpenHelperManager.getHelper(context, DBHelper.class);
                            Dao dao;
                            List<Contact> contacts = null;

                            try {
                                Log.v("ASDF", "antes del dao");
                                dao = helper.getContactDao();
                                contacts = dao.queryForAll();

                                if (!contacts.isEmpty()) {
                                    Log.v("ASDF", "Antes del for " + contacts.size());
                                    for (Contact currentContact : contacts) {
                                        String telf = currentContact.getTelephone().replace(" ", "");
                                        Long id = currentContact.getId();

                                        if (telf.contains(contactServer.getTelephone().toString())) {
                                            dao.update(contactServer);
                                            Log.v("ASDF", "sync update");
                                        } else {
                                            dao.createOrUpdate(contactServer);
                                            Log.v("ASDF", "sync insert");
                                        }

                                    }
                                } else {
                                    dao.createOrUpdate(contactServer);
                                    Log.v("ASDF", "sync insert");
                                }
                                Log.v("ASDF", "fuera del if selfe");
                            } catch (java.sql.SQLException e) {
                                e.printStackTrace();
                                Log.e("Helper", "Search user error");
                            }
                        }
                        Log.v("ASDF", "se duerme");
                        Thread.sleep(300);

                    } catch (MalformedURLException e) {
                        Log.e("ASDF", "error3 " + e.toString());
                    } catch (IOException e) {
                        Log.e("ASDF", "error4 " + e.toString());
                    } catch (JSONException e) {
                        Log.e("ASDF", "error5 " + e.toString());
                    } catch (InterruptedException e) {
                        Log.e("ASDF", "error6 " + e.toString());
                    }


                }
                return null;
            }


        }.execute(null, null, null);
    }


    public static List<Contact> getListContacts(Context contexto) {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String proyeccion[] = null;
        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = ? and " +
                ContactsContract.Contacts.HAS_PHONE_NUMBER + "= ?";
        String arguments[] = new String[]{"1", "1"};
        String order = ContactsContract.Contacts.DISPLAY_NAME + " collate localized asc";
        Cursor cursor = contexto.getContentResolver().query(uri, proyeccion, selection, arguments, order);
        int indexId = cursor.getColumnIndex(ContactsContract.Contacts._ID);
        int indexName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        List<Contact> lista = new ArrayList<>();
        Contact contact;
        while (cursor.moveToNext()) {
            contact = new Contact();
            contact.setId(cursor.getLong(indexId));
            contact.setName(cursor.getString(indexName));
            //contact.setTelephone(getListTelephones(contexto, contact.getId()));
            contact.setTelephone(getListTelephones(contexto, contact.getId()).get(0));
            lista.add(contact);
        }
        return lista;
    }

    public static List<String> getListTelephones(Context context, long id) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String proyeccion[] = null;
        String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?";
        String arguments[] = new String[]{id + ""};
        String order = ContactsContract.CommonDataKinds.Phone.NUMBER;
        Cursor cursor = context.getContentResolver().query(uri, proyeccion, selection, arguments, order);
        int indexNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        List<String> list = new ArrayList<>();
        String number;
        while (cursor.moveToNext()) {
            number = cursor.getString(indexNumber);
            list.add(number);
        }
        return list;
    }
}
