package gcm.play.android.samples.com.gcmquickstart.manager;

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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import gcm.play.android.samples.com.gcmquickstart.ApiContact;
import gcm.play.android.samples.com.gcmquickstart.R;
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

    /**
     * Método que registra un nuevo usuario en el servidor
     *
     * @param c     contexto
     * @param token token identificador del nuevo usuario
     */
    public static void registrationToServer(Context c, String token) {
        SharedPreferences prefs = c.getSharedPreferences(c.getResources().getString(R.string.preference), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(c.getResources().getString(R.string.key_token), token);
        editor.commit();
        String tlf = prefs.getString(c.getResources().getString(R.string.key_telephone), "");

        Manager.registerRetrofit(tlf, token, c, editor);

        if (prefs.getBoolean(c.getResources().getString(R.string.str_register), false))
            c.startService(new Intent(c, SyncContact.class));
    }

    /**
     * Método que actualiza al usuario en el servidor
     *
     * @param context
     */
    public static void syncOurSelves(final Context context) {
        updateRetrofit(createMySelf(context));
    }


    /**
     * Sincroniza todos los contactos con los del servidor
     *
     * @param context
     */
    public static void syncContact(final Context context) {

        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                List<Contact> listUser = getListContacts(context);

                for (final Contact telephoneContact : listUser) {
                    String urlOrigin = "http://192.168.1.10:8080/MeetMe/servlet";

                    URL url = null;
                    BufferedReader in = null;
                    String res = "";

                    try {
                        String destination = urlOrigin + "?op=consulta&action=tlf&tlf=" + telephoneContact.getTelephone().replace("+34", "").replace(" ", "");

                        url = new URL(destination);
                        in = new BufferedReader(new InputStreamReader(url.openStream()));
                        String linea;

                        while ((linea = in.readLine()) != null) {
                            res += linea;
                        }

                        in.close();


                        if (!res.contains("false")) {

                            JSONObject obj = new JSONObject(res);
                            Contact contactServer = new Contact();
                            contactServer.getUsuario(obj);
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


                                        if (telf.contains(contactServer.getTelephone().toString())) {
                                            updateChat(currentContact, contactServer, helper);

                                            currentContact = replaceCurrentContactToServer(currentContact, contactServer);

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
//    public static void syncContact(final Context context) {
//        List<Contact> listUser = getListContacts(context);
//
//        DBHelper helper = OpenHelperManager.getHelper(context, DBHelper.class);
//        Dao dao;
//        List<Contact> contacts = null;
//
//        try {
//            dao = helper.getContactDao();
//            contacts = dao.queryForAll();
//
//
//            for (final Contact telephoneContact : listUser) {
//
//                if (!contacts.isEmpty()) {
//                    Log.v("ASDF", "La lista no está vacía");
//                    for (Contact currentContact : contacts) {
//                        String telf = currentContact.getTelephone().replace(" ", "");
//                        searchRetrofitByTelephone(telf, currentContact, dao, helper);
//                    }
//                } else {
//                    Log.v("ASDF", "no tenemos a nadie guardado");
//                    searchRetrofitByTelephone(telephoneContact.getTelephone(), dao, helper);
//                }
//            }
//        } catch (java.sql.SQLException e) {
//            e.printStackTrace();
//            Log.e("Helper", "Search user error");
//        }
//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }


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
     * Sincroniza con la base de datos el contacto
     *
     * @param context
     * @param contact
     */
    public static void updateContact(final Context context, final Contact contact) {

        DBHelper helper = OpenHelperManager.getHelper(context, DBHelper.class);
        Dao dao = null;
        try {
            dao = helper.getContactDao();

            List<Contact> listContact = dao.queryForEq(Contact.TELEPHONE, contact.getTelephone());
            Contact currentContact = listContact.get(0);

            searchRetrofitByTelephone(contact.getTelephone(), currentContact, dao, helper);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

/***********************************************************************************************************************************************************************************************************/
    /**
     * Metódo que devuelve todos los contactos del móvil
     *
     * @param contexto
     * @return Lista de contactos
     */
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

    /**
     * Método que devuelve la lista de teléfonos de un contacto
     *
     * @param context
     * @param id      identificador del usuario
     * @return Lista de teléfonos
     */
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

    /**
     * Método que busca un usuario en la base de datos por token y lo guarda a su vez en la base de datos local
     *
     * @param token
     */
    public static void searchRetrofitByToken(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.10:8080/MeetMe/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiContact api = retrofit.create(ApiContact.class);

        retrofit.Call call = api.getContactByToken(token);

        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Response<Contact> response, Retrofit retrofit) {
                //actualizar
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });
    }

    /**
     * Método que busca un usuario en la base de datos por teléfono y lo guarda a su vez en la base de datos local
     *
     * @param telephone
     */
    public static void searchRetrofitByTelephone(String telephone, final Contact currentContact, final Dao dao, final DBHelper helper) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.10:8080/MeetMe/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiContact api = retrofit.create(ApiContact.class);

        retrofit.Call call = api.getContactByTelephone(telephone);
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Response<Contact> response, Retrofit retrofit) {

                Log.v("ASDF","actualizando contacto");
                if (response.isSuccess()) {
                    Contact contactServer = response.body();
                    try {
                        if (currentContact.getTelephone().equals(contactServer.getTelephone())) {
                            Log.v("ASDF", "serverCONTACT" + contactServer.toString());
                            updateChat(currentContact, contactServer, helper);
                            dao.createOrUpdate(replaceCurrentContactToServer(currentContact, contactServer));

                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });
    }

    public static void searchRetrofitByTelephone(final String telephone, final Dao dao, final DBHelper helper) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.10:8080/MeetMe/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiContact api = retrofit.create(ApiContact.class);

        retrofit.Call call = api.getContactByTelephone(telephone);
        Log.v("ASDF", "sE LANZA LA HEBRA");
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Response<Contact> response, Retrofit retrofit) {

                if (response.isSuccess()) {
                    Log.v("ASDF", "respuesta del servidor correcta");

                    Contact contactServer = response.body();

                    Log.v("ASDF", "CONTACTSERVER" + contactServer.toString());

                    try {

                        if (contactServer.getTelephone().equals("not registered")) {
                            Log.v("ASDF", "Nos llega un contacto válido");
                            if (contactServer.getTelephone().equals(telephone)) {
                                Log.v("ASDF", "serverCONTACT" + contactServer.toString());
                                Log.v("ASDF", "registra a " + contactServer.toString());
                                dao.createOrUpdate(contactServer);
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.v("ASDF", "Ha saltado una excepción al intentar conectar al servidor");
                //t.getLocalizedMessage();
            }
        });
        Log.v("ASDF", "eSPERAMOS A LA HEBRA");
        try {
            call.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Método encargado de llamar al servidor para el registro de un nuevo usuario
     *
     * @param telephone que se va a registrar
     * @param token     identificador del teléfono
     * @param c         contexto
     * @param editor    editor de SharedPreferences
     */
    public static void registerRetrofit(String telephone, String token, final Context c, final SharedPreferences.Editor editor) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.10:8080/MeetMe/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiContact api = retrofit.create(ApiContact.class);

        Call call = api.createContact(telephone, token);

        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Response<Contact> response, Retrofit retrofit) {

                if (response.isSuccess()) {
                    editor.putBoolean(c.getResources().getString(R.string.str_register), true);
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });
    }

    /**
     * Método que se encarga de realizar la llamada al servidor para actualizar un Contacto
     *
     * @param contact que se va a actualizar
     */
    public static void updateRetrofit(Contact contact) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.10:8080/MeetMe/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiContact api = retrofit.create(ApiContact.class);


        Call call = updateContactRetrofit(api, contact);


        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Response<Contact> response, Retrofit retrofit) {

            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });
    }


    /**
     * Devuelve una llamada al servidor pasándole el Contacto, dicha llamada es para actualizar el mismo.
     *
     * @param apicontact
     * @param contact    que se va a actualizar
     * @return Call
     */
    public static Call updateContactRetrofit(ApiContact apicontact, Contact contact) {
        return apicontact.updateContact(contact.getTelephone(), contact.getToken(), contact.getNick(), contact.getDescription(), contact.getLastconnection(), contact.getSeeconnection(),
                contact.getFacebook(), contact.getTwitter(), contact.getEmail(), contact.getBirth(), contact.getNationality(), contact.getPrivacy());
    }

    /**
     * Es un método que devuelve un objeto de tipo Contact con nuestros datos
     *
     * @param context
     * @return contact
     */
    public static Contact createMySelf(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(context.getResources().getString(R.string.preference), Context.MODE_PRIVATE);

        String ourTelephone = prefs.getString(context.getString(R.string.key_telephone), "").replace("+34", "").replace(" ", "");
        String ourToken = prefs.getString(context.getString(R.string.key_token), "");
        String ourNationality = prefs.getString(context.getString(R.string.key_nationality), "");
        String ourDescription = prefs.getString(context.getString(R.string.key_description), "I am using MeetMe");
        String ourEmail = prefs.getString(context.getString(R.string.key_email), "");
        String ourFacebook = prefs.getString(context.getString(R.string.key_facebook), "");
        String ourBirth = prefs.getString(context.getString(R.string.key_birth), "");
        String ourNick = prefs.getString(context.getString(R.string.key_nick), "Nick");
        String ourPrivacy = prefs.getString(context.getString(R.string.key_privacy), "Amigos");
        String ourTwitter = prefs.getString(context.getString(R.string.key_twitter), "");
        String ourLastConnection = prefs.getString(context.getString(R.string.key_last_connection), "Amigos");


        Date lastConnection = new Date();
        String minute = "";

        if (lastConnection.getMinutes() < 10)
            minute = "0";
        minute += lastConnection.getMinutes();

        String ourLastHours = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + (Calendar.getInstance().get(Calendar.YEAR)) +
                "%20" + Calendar.getInstance().get(Calendar.HOUR) + ":" + Calendar.getInstance().get(Calendar.MINUTE);

        Contact contact = new Contact(ourNick, ourTelephone, ourToken, ourDescription, ourLastHours,
                ourFacebook, ourEmail, ourBirth, ourTwitter, ourLastConnection, ourNationality, ourPrivacy);

        return contact;
    }

    public static Contact replaceCurrentContactToServer(Contact currentContact, Contact contactServer) {
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

        return currentContact;

    }
}




