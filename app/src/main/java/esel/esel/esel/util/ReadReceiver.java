package esel.esel.esel.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import java.io.IOException;

import esel.esel.esel.Esel;
import esel.esel.esel.datareader.Datareader;
import esel.esel.esel.datareader.SGV;

/**
 * Created by adrian on 04/08/17.
 */

public class ReadReceiver extends BroadcastReceiver {

    public static final long REPEAT_TIME = 1 * 20 * 1000L;

private static final String TAG = "BroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        Log.d(TAG, "onReceive called");
        setAlarm(Esel.getsInstance());


        try {

            //TODO: last-read updaten & KeepAlive
            //TODO: Battery whitelisting

            String datastring = Datareader.readData();
            SGV sgv = Datareader.generateSGV(datastring);

            //if time or value changed
            //store value & time
            //send broadcast
            ToastUtils.makeToast(sgv.toString());

            //TODO: 40-400 beschränken
            //TODO: Alarmzustand 65535



        } catch (IOException e) {
            ToastUtils.makeToast("IOException");
        } catch (InterruptedException e) {
            ToastUtils.makeToast("InterruptedException");
        }




        wl.release();
    }



    public void setAlarm(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, ReadReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        /*try {
            pi.send();
        } catch (PendingIntent.CanceledException e) {
        }*/
        //am.cancel(pi);
        am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + REPEAT_TIME, pi);
    }

    public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, ReadReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }




}
