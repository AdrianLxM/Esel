package esel.esel.esel;

import android.app.Application;
import android.content.Intent;
import android.content.res.Resources;

import esel.esel.esel.util.ReadReceiver;

/**
 * Created by adrian on 04/08/17.
 */

public class Esel extends Application {

    private static Esel sInstance;
    private static Resources sResources;
    private ReadReceiver readReceiver;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sResources = getResources();
        startReadReceiver();
    }

    public static Esel getsInstance() {
        return sInstance;
    }

    public static Resources getsResources() {
        return sResources;
    }


    private synchronized void startReadReceiver() {
        if (readReceiver == null) {
            readReceiver = new ReadReceiver();
            readReceiver.setAlarm(this);
        }
    }


    public synchronized void stopReadReceiver() {
        if (readReceiver != null)
            readReceiver.cancelAlarm(this);
            readReceiver = null;
    }

}
