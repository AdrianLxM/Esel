package esel.esel.esel.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import esel.esel.esel.Esel;
import esel.esel.esel.util.SP;

/**
 * Created by adrian on 04/08/17.
 */

public class AutostartReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //do nothing. Creating the app should start things.
    }
}
