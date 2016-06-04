package gcm.play.android.samples.com.gcmquickstart.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import gcm.play.android.samples.com.gcmquickstart.pojo.Chat;
import gcm.play.android.samples.com.gcmquickstart.pojo.Contact;

/**
 * Created by Admin on 27/04/2016.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "meetme.db";
    private static final int DATABASE_VERSION = 2;

    private Dao<Chat, Integer> chatDao;
    private Dao<Contact, Integer> contactDao;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Chat.class);
            TableUtils.createTable(connectionSource, Contact.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        onCreate(database, connectionSource);
    }

    public Dao<Chat, Integer> getChatDao() throws SQLException, java.sql.SQLException {
        if (chatDao == null) {
            chatDao = getDao(Chat.class);
        }
        return chatDao;
    }

    public Dao<Contact, Integer> getContactDao() throws SQLException, java.sql.SQLException {
        if (contactDao == null) {
            contactDao = getDao(Contact.class);
        }
        return contactDao;
    }

    @Override
    public void close() {
        super.close();
        chatDao = null;
        contactDao = null;
    }
}