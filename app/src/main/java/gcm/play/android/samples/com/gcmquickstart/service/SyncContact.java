package gcm.play.android.samples.com.gcmquickstart.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import gcm.play.android.samples.com.gcmquickstart.Manager;

/**
 * Created by Admin on 18/05/2016.
 */
public class SyncContact extends Service {

    public SyncContact() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Manager.syncContact(this);
        return START_STICKY;
    }
}
