package esel.esel.esel.util;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import esel.esel.esel.Esel;
import esel.esel.esel.datareader.SGV;

/**
 * Created by adrian on 04/08/17.
 */

public class LocalBroadcaster {

    public static final String XDRIP_PLUS_NS_EMULATOR = "com.eveningoutpost.dexdrip.NS_EMULATOR";
    public static final String ACTION_DATABASE = "info.nightscout.client.DBACCESS";


    private static final String TAG = "LocalBroadcaster";
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);


    public static void broadcast(SGV sgv) {
        try {

            if (SP.getBoolean("send_to_AAPS", true)) {
                final JSONArray entriesBody = new JSONArray();
                addSgvEntry(entriesBody, sgv);
                sendBundle("add", "entries", entriesBody, XDRIP_PLUS_NS_EMULATOR);
            }

            if (SP.getBoolean("send_to_NS", true)) {
                sendBundle("dbAdd", "entries", generateSgvEntry(sgv), ACTION_DATABASE);
            }

        } catch (Exception e) {
            Log.e(TAG, "Unable to send bundle: " + e);
        }
    }

    private static void addSgvEntry(JSONArray entriesArray, SGV sgv) throws Exception {
        JSONObject json = new JSONObject();
        json.put("sgv", sgv.value);
        if (sgv.direction == null) {
            json.put("direction", "NONE");
        } else {
            json.put("direction", sgv.direction);
        }
        json.put("device", "ESEL");
        json.put("type", "sgv");
        json.put("date", sgv.timestamp);
        json.put("dateString", format.format(sgv.timestamp));

        entriesArray.put(json);
    }

    private static JSONObject generateSgvEntry(SGV sgv) throws Exception {
        JSONObject json = new JSONObject();
        json.put("sgv", sgv.value);
        if (sgv.direction == null) {
            json.put("direction", "NONE");
        } else {
            json.put("direction", sgv.direction);
        }
        json.put("device", "ESEL");
        json.put("type", "sgv");
        json.put("date", sgv.timestamp);
        json.put("dateString", format.format(sgv.timestamp));

        return json;
    }

    private static void sendBundle(String action, String collection, Object json, String intentIdAction) {
        final Bundle bundle = new Bundle();
        bundle.putString("action", action);
        bundle.putString("collection", collection);
        bundle.putString("data", json.toString());
        final Intent intent = new Intent(intentIdAction);
        intent.putExtras(bundle).addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

        List<ResolveInfo> receivers = Esel.getsInstance().getPackageManager().queryBroadcastReceivers(intent, 0);

        /* With receiving apps targeting O+ we need to explicitly send broadcasts to the packages
        for them to wake from the background*/
        for (ResolveInfo resolveInfo : receivers) {
            String packageName = resolveInfo.activityInfo.packageName;
            if (packageName != null) {
                intent.setPackage(packageName);
                Esel.getsInstance().sendBroadcast(intent);
                Log.d(TAG, "sent to: " + packageName);
            }
        }
    }
}
