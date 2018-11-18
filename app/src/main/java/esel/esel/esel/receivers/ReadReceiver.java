package esel.esel.esel.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.PowerManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import esel.esel.esel.Esel;
import esel.esel.esel.R;
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


        int sync = 8;
        try {

            sync = SP.getInt("max-sync-hours", sync);

        } catch (Exception e) {
            e.printStackTrace();
        }
        long syncTime = sync * 60 * 60 * 1000L;
        long currentTime = System.currentTimeMillis();

        try {

            SP.putLong("readReceiver-called", System.currentTimeMillis());
            //TODO: KeepAlive und ReadReceiver bei App-Beenden stoppen.

            //String datastring = Datareader.readData();


            long lastReadingTime = SP.getLong("lastReadingTime", currentTime);

            if (lastReadingTime + syncTime < currentTime) {
                lastReadingTime = currentTime - syncTime;
            }

            broadcastData(context, lastReadingTime, true);


        } catch (Exception e) {
            ToastUtils.makeToast("Exception: " + e.getMessage());
        }


        //auto full sync in specific time intervals
        long lastFullSync = SP.getLong("last_full_sync", currentTime);
        long autoSycInterval = SP.getInt("auto-sync-interval",3)* 60 * 60 * 1000L ;

        if(autoSycInterval > 0 && (currentTime - lastFullSync) > autoSycInterval){
            FullSync(context,sync);
        }


        wl.release();
    }

    public int FullSync(Context context, int syncHours){
        long currentTime = System.currentTimeMillis();
        long syncTime = syncHours * 60 * 60 * 1000L;
        long lastTimestamp = currentTime - syncTime;

        //disable smoothing as historical data will be overwritten
        int written = broadcastData(context, lastTimestamp, false);

        SP.putLong("last_full_sync", System.currentTimeMillis());

        ToastUtils.makeToast("Full Sync done: Read " + written + " values from DB\n(last " + syncHours + " hours)");

        return written;
    }

    public int broadcastData(Context context, long lastReadingTime, boolean smoothEnabled) {
        int result = 0;
        try {


            SP.putLong("readReceiver-called", System.currentTimeMillis());

            int size = 2;
            long updatedReadingTime = lastReadingTime;

            do {
                lastReadingTime = updatedReadingTime;

                List<SGV> valueArray = Datareader.readDataFromContentProvider(context, size, lastReadingTime);

                if (valueArray == null || valueArray.size() == 0) {
                    ToastUtils.makeToast("DB not readable!");
                    //wl.release();
                    return result;
                }

                if (valueArray.size() != size) {
                    //ToastUtils.makeToast("DB not readable!");
                    //wl.release();
                    return result;
                }

                for (int i = 0; i < valueArray.size(); i++) {
                    SGV sgv = valueArray.get(i);

                    long oldTime = SP.getLong("lastReadingTime", -1L);
                    int oldValue = SP.getInt("lastReadingValue", -1);

                    if (oldTime != sgv.timestamp) {

                        double slopeByMinute = 0d;
                        if (oldTime != sgv.timestamp) {
                            slopeByMinute = (oldValue - sgv.value) * 60000.0d / ((oldTime - sgv.timestamp) * 1.0d);
                        }
                        sgv.setDirection(slopeByMinute);


                        if (sgv.value >= 39 && oldValue >= 39) {
                            //ToastUtils.makeToast(sgv.toString());
                            if(SP.getBoolean("smooth_data",false) && smoothEnabled){
                                sgv.smooth(oldValue);
                            }

                            LocalBroadcaster.broadcast(sgv);
                            result++;
                        } else {
                            ToastUtils.makeToast("NOT A READING!");
                        }
                        SP.putLong("lastReadingTime", sgv.timestamp);
                        SP.putInt("lastReadingValue", sgv.value);
                    }
                }

                updatedReadingTime = SP.getLong("lastReadingTime", lastReadingTime);
            } while (updatedReadingTime != lastReadingTime);

            //} catch (IOException e) {
            //   ToastUtils.makeToast("IOException");
            //} catch (InterruptedException e) {
            //    ToastUtils.makeToast("InterruptedException");
        } catch (android.database.CursorIndexOutOfBoundsException eb) {
            eb.printStackTrace();
            ToastUtils.makeToast("DB is empty!\nIt can take up to 15min with running Eversense App until values are available!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //wl.release();

        return result;
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
