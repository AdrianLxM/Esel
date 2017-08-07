package esel.esel.esel.receivers;

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
import esel.esel.esel.util.LocalBroadcaster;
import esel.esel.esel.util.SP;
import esel.esel.esel.util.ToastUtils;

/**
 * Created by adrian on 04/08/17.
 */

public class ReadReceiver extends BroadcastReceiver {

    public static final long REPEAT_TIME = 1 * 20 * 1000L;

private static final String TAG = "ReadReceiver";

    @Override
    public synchronized void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        Log.d(TAG, "onReceive called");
        setAlarm(Esel.getsInstance());


        try {

            SP.putLong("readReceiver-called", System.currentTimeMillis());
            //TODO: KeepAlive und ReadReceiver bei App-Beenden stoppen.

            String datastring = Datareader.readData();
            if(datastring == null){
                ToastUtils.makeToast("DB not readable!");
                wl.release();
                return;
            }
            SGV sgv = Datareader.generateSGV(datastring);

            long oldTime = SP.getLong("lastReadingTime", -1L);
            int oldValue = SP.getInt("lastReadingValue", -1);

            if(oldTime != sgv.timestamp || oldValue != sgv.value){

                double slopeByMinute = 0d;
                if(oldTime != sgv.timestamp){
                    slopeByMinute = (sgv.value - oldValue)*60000.0d/((sgv.timestamp-oldTime)*1.0d);
                }
                sgv.setDirection(slopeByMinute);

                SP.putLong("lastReadingTime", sgv.timestamp);
                SP.putInt("lastReadingValue", sgv.value);
                if(sgv.value > 38){
                    ToastUtils.makeToast(sgv.toString());
                    LocalBroadcaster.broadcast(sgv);
                } else {
                    ToastUtils.makeToast("NOT A READING!");
                }
            }



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
        am.cancel(pi);
        am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + REPEAT_TIME, pi);
    }

    public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, ReadReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }




}
