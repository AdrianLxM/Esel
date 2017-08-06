package esel.esel.esel;

import android.app.Application;
import android.content.res.Resources;

import esel.esel.esel.receivers.KeepAliveReceiver;
import esel.esel.esel.receivers.ReadReceiver;

/**
 * Created by adrian on 04/08/17.
 */

public class Esel extends Application {

    private static Esel sInstance;
    private static Resources sResources;
    private ReadReceiver readReceiver;
    private KeepAliveReceiver keepAliveReceiver;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sResources = getResources();
        startReadReceiver();
        startKeepAliveService();
    }

    public static Esel getsInstance() {
        return sInstance;
    }

    public static Resources getsResources() {
        return sResources;
    }


    public synchronized void startReadReceiver() {

        if (readReceiver == null) {
            readReceiver = new ReadReceiver();
            readReceiver.setAlarm(this);
        } else {
            readReceiver.setAlarm(this);
        }
    }


    public synchronized void stopReadReceiver() {
        if (readReceiver != null)
            readReceiver.cancelAlarm(this);
            readReceiver = null;
    }


    private void startKeepAliveService() {
        if (keepAliveReceiver == null) {
            keepAliveReceiver = new KeepAliveReceiver();
            keepAliveReceiver.setAlarm(this);
        }
    }


    public void stopKeepAliveService() {
        if (keepAliveReceiver != null)
            keepAliveReceiver.cancelAlarm(this);
    }

}
