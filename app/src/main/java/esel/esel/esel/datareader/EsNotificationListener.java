package esel.esel.esel.datareader;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

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
                   lastReadings.add(sgv);
                } catch (NumberFormatException err) {
                    err.printStackTrace();
                }
            }
        }

    }

    public static List<SGV> getData(int number, long lastReadingTime){
        List<SGV> result = new ArrayList<SGV>();
        for (SGV reading:lastReadings) {
            if(reading.timestamp > lastReadingTime){
                result.add(reading);
            }

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
        float value = Float.parseFloat((String) notification.tickerText);
        return new SGV(value, timestamp,record);
    }

}
