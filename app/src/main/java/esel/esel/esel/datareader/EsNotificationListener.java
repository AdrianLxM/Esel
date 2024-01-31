package esel.esel.esel.datareader;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

import esel.esel.esel.util.SP;

/**
 * Created by bernhard on 24-01-18.
 */
public class EsNotificationListener extends NotificationListenerService {

    private static List<SGV> lastReadings = new ArrayList<SGV>();
    
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        
        boolean use_patched_es = SP.getBoolean("use_patched_es", true);
        if(use_patched_es){
            return;
        }

        if (sbn.getPackageName().equals("com.senseonics.gen12androidapp") ||
                sbn.getPackageName().equals("com.senseonics.androidapp")) {
            Notification notification = sbn.getNotification();
            if (notification != null && notification.tickerText != null) {
                try {
                   SGV sgv = generateSGV(notification,lastReadings.size());
                   if(sgv != null) {
                       lastReadings.add(sgv);
                   }
                } catch (NumberFormatException err) {
                    err.printStackTrace();
                }
            }
        }

    }

    public static List<SGV> getData(int number, long lastReadingTime){
        List<SGV> result = new ArrayList<SGV>();
        for (SGV reading:lastReadings) {
            //if(reading.timestamp > lastReadingTime){
                result.add(reading);
            //}

        }

        while (result.size() > number){
            result.remove(0);
        }

        if(result.size() == number){
            SGV last = lastReadings.get(lastReadings.size()-1);
            lastReadings.clear();
            lastReadings.add(last);
        }

        return  result;
    }

    public static SGV generateSGV(Notification notification ,int record){
        long timestamp = notification.when;
        String tickerText = (String) notification.tickerText;
        int value;
        if(tickerText.contains(".") || tickerText.contains(",")){ //is mmol/l
            float valuef = Float.parseFloat(tickerText);
            value = SGV.Convert(valuef);
        }else{
            value =Integer.parseInt(tickerText);
        }

        if(lastReadings.size()>0) {
            long five_min = 300000l;
            SGV oldSgv = lastReadings.get(lastReadings.size() - 1);
            long lastreadingtime = oldSgv.timestamp; // SP.getLong("lastreadingtime_nl",timestamp);
            int lastreadingvalue = oldSgv.raw; //SP.getInt("lastreadingvalue_nl",value);
            if (value == lastreadingvalue && (lastreadingtime + (five_min * 1.05)) > timestamp ) { // no new value
                return null;
            }
        }

       // SP.putLong("lastreadingtime_nl",timestamp);
        // SP.putInt("lastreadingvalue_nl",value);

        return new SGV(value, timestamp,record);
    }

}
