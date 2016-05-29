package gcm.play.android.samples.com.gcmquickstart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.util.SortedList;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import gcm.play.android.samples.com.gcmquickstart.db.DBHelper;
import gcm.play.android.samples.com.gcmquickstart.pojo.Chat;
import gcm.play.android.samples.com.gcmquickstart.pojo.Contact;
import gcm.play.android.samples.com.gcmquickstart.service.SyncContact;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Admin on 20/04/2016.
 */
public class Manager {
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

        String urlOrigin = "http://192.168.1.15:8080/MeetMe/servlet";

        SharedPreferences prefs = c.getSharedPreferences(c.getResources().getString(R.string.preference), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(c.getResources().getString(R.string.key_token), token);
        editor.commit();

        String tlf = prefs.getString(c.getResources().getString(R.string.key_telephone), "");

        URL url = null;
        BufferedReader in = null;

        try {
            String destination = urlOrigin + "?op=alta&action=registrar&tlf=" + tlf + "&token=" + token;
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

    /**
     * Sincroniza todos los contactos con los del servidor
     *
     * @param context
     */
    public static void syncContact(final Context context) {
        Log.v("ASDF", "Sincornizando contactos");
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                List<Contact> listUser = getListContacts(context);

                for (final Contact telephoneContact : listUser) {
                    String urlOrigin = "http://192.168.1.15:8080/MeetMe/servlet";

                    URL url = null;
                    BufferedReader in = null;
                    String res = "";

                    try {
                        String destination = urlOrigin + "?op=consulta&action=tlf&tlf=" + telephoneContact.getTelephone().replace("+34", "").replace(" ", "");
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
                            Contact contactServer = new Contact();
                            contactServer.getUsuario(obj.getJSONObject("r"));
                            contactServer.setName(telephoneContact.getName());

                            DBHelper helper = OpenHelperManager.getHelper(context, DBHelper.class);
                            Dao dao;
                            List<Contact> contacts = null;

                            try {
                                dao = helper.getContactDao();
                                contacts = dao.queryForAll();

                                if (!contacts.isEmpty()) {
                                    for (Contact currentContact : contacts) {
                                        String telf = currentContact.getTelephone().replace(" ", "");
                                        Long id = currentContact.getId();

                                        Log.v("ASDF", "" + contactServer.toString());
                                        if (telf.contains(contactServer.getTelephone().toString())) {
                                            updateChat(currentContact, contactServer, helper);

                                            currentContact.setNick(contactServer.getNick());
                                            currentContact.setToken(contactServer.getToken());
                                            currentContact.setDescription(contactServer.getDescription());
                                            currentContact.setLastconnection(contactServer.getLastconnection());
                                            currentContact.setSeeconnection(contactServer.getSeeconnection());
                                            currentContact.setPrivacy(contactServer.getPrivacy());
                                            currentContact.setFacebook(contactServer.getFacebook());
                                            currentContact.setTwitter(contactServer.getTwitter());
                                            currentContact.setEmail(contactServer.getEmail());
                                            currentContact.setBirth(contactServer.getBirth());
                                            currentContact.setNationality(contactServer.getNationality());

                                            dao.update(currentContact);
                                        } else {
                                            dao.create(contactServer);
                                        }

                                    }
                                } else {
                                    dao.createOrUpdate(contactServer);
                                }
                            } catch (java.sql.SQLException e) {
                                e.printStackTrace();
                                Log.e("Helper", "Search user error");
                            }
                        }
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

    /**
     * Actuliza todos los chat de un contacto (token)
     *
     * @param currentContact
     * @param contactServer
     * @param helper
     * @throws SQLException
     */
    public static void updateChat(Contact currentContact, Contact contactServer, DBHelper helper) throws SQLException {
        Dao dao = helper.getChatDao();
        List<Chat> listChat = dao.queryForEq(Chat.CONVERSATION, currentContact.getToken());
        for (Chat currentChat : listChat) {
            if (currentChat.getTokensender().equals(contactServer.getToken()))
                currentChat.setTokensender(contactServer.getToken());
            currentChat.setTokenconversation(contactServer.getToken());
            dao.update(currentChat);
        }
    }

    /**
     * Sincroniza con la base ede datos un unico contacto
     *
     * @param context
     * @param contact
     */
    public static void updateContact(final Context context, final Contact contact) {
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                DBHelper helper = OpenHelperManager.getHelper(context, DBHelper.class);
                Dao dao = null;
                try {
                    dao = helper.getContactDao();

                    List<Contact> listContact = dao.queryForEq(Contact.TELEPHONE, contact.getTelephone());
                    Contact currentContact = listContact.get(0);
                    String urlOrigin = "http://192.168.1.15:8080/MeetMe/servlet";

                    URL url = null;
                    BufferedReader in = null;
                    String res = "";

                    try {
                        String destination = urlOrigin + "?op=consulta&action=tlf&tlf=" + contact.getTelephone().replace("+34", "").replace(" ", "");
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
                            JSONObject obj = new JSONObject(res);
                            Contact contactServer = new Contact();
                            contactServer.getUsuario(obj.getJSONObject("r"));

                            updateChat(currentContact, contactServer, helper);

                            currentContact.setNick(contactServer.getNick());
                            currentContact.setToken(contactServer.getToken());
                            currentContact.setDescription(contactServer.getDescription());
                            currentContact.setLastconnection(contactServer.getLastconnection());
                            currentContact.setSeeconnection(contactServer.getSeeconnection());
                            currentContact.setPrivacy(contactServer.getPrivacy());
                            currentContact.setFacebook(contactServer.getFacebook());
                            currentContact.setTwitter(contactServer.getTwitter());
                            currentContact.setEmail(contactServer.getEmail());
                            currentContact.setBirth(contactServer.getBirth());
                            currentContact.setNationality(contactServer.getNationality());

                            dao.update(currentContact);

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    public static void syncOurSelves(final Context context) {
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                DBHelper helper = OpenHelperManager.getHelper(context, DBHelper.class);

                SharedPreferences prefs = context.getSharedPreferences(context.getResources().getString(R.string.preference), Context.MODE_PRIVATE);

                String ourTelephone = prefs.getString(context.getString(R.string.key_telephone), "").replace("+34", "").replace(" ", "");
                String ourToken = prefs.getString(context.getString(R.string.key_token), "");
                String ourNationality = prefs.getString(context.getString(R.string.key_nationality), "").replace(" ", "%20");
                String ourDescripcion = prefs.getString(context.getString(R.string.key_description), "I am using MeetMe").replace(" ", "%20");
                String ourEmail = prefs.getString(context.getString(R.string.key_email), "").replace(" ", "%20");
                String ourFacebook = prefs.getString(context.getString(R.string.key_facebook), "").replace(" ", "%20");
                String ourBirth = prefs.getString(context.getString(R.string.key_birth), "").replace(" ", "%20");
                String ourNick = prefs.getString(context.getString(R.string.key_nick), "Nick").replace(" ", "%20");
                String ourPrivacity = prefs.getString(context.getString(R.string.key_privacy), "Amigos").replace(" ", "%20");
                String ourTwitter = prefs.getString(context.getString(R.string.key_twitter), "").replace(" ", "%20");
                String ourLastConnection = prefs.getString(context.getString(R.string.key_last_connection), "Amigos").replace(" ", "%20");

                Date lastConnection = new Date();
                String minute = "";

                if (lastConnection.getMinutes() < 10)
                    minute = "0";
                minute += lastConnection.getMinutes();

                String ourLastHours = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + (Calendar.getInstance().get(Calendar.YEAR)) +
                        "%20" + Calendar.getInstance().get(Calendar.HOUR) + ":" + Calendar.getInstance().get(Calendar.MINUTE);


                Dao dao = null;
                try {
                    dao = helper.getContactDao();

                    String urlOrigin = "http://192.168.1.15:8080/MeetMe/servlet";

                    URL url = null;
                    BufferedReader in = null;
                    String res = "";

                    try {
                        String destination = urlOrigin + "?op=alta&action=actualizar&tlf=" + ourTelephone + "&token=" + ourToken +
                                "&nick=" + ourNick + "&description=" + ourDescripcion + "&last=" + ourLastHours + "&see=" + ourLastConnection +
                                "&privacy=" + ourPrivacity + "&facebook=" + ourFacebook + "&twitter=" + ourTwitter + "&email=" + ourEmail +
                                "&nationality=" + ourNationality + "&birth=" + ourBirth;

                        url = new URL(destination);
                        Log.v("ASDF", destination);
                        in = new BufferedReader(new InputStreamReader(url.openStream()));

                        in.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
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
        cursor.close();
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
        cursor.close();
        return list;
    }

    public static void consultaRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.15:8080/MeetMe/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiContact api = retrofit.create(ApiContact.class);

        Call call = api.getContactByTelephone("672105570");

        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Response<Contact> response, Retrofit retrofit) {
                Log.v("ASDF", "fin call "+ response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });
    }
}
