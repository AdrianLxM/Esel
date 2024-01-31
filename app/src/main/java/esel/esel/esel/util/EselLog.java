package esel.esel.esel.util;

import android.util.Log;

import esel.esel.esel.LogActivity;

public class EselLog {


    public static void LogI(String tag, String value, boolean toast) {
        if(toast) {
            ToastUtils.makeToast("Info: " + value);
        }
        LogI(tag,value);
    }
    public static void LogI(String tag, String value){
        String type = "Info: ";
        Log.v(tag,value);
        LogActivity.addLog(type,tag,value);
    }

    public static void LogE(String tag, String value, boolean toast) {
        if(toast) {
            ToastUtils.makeToast("Error: " + value);
        }

        LogE(tag,value);
    }

    public static void LogE(String tag, String value){
        String type = "Error: ";
        Log.v(tag,value);
        LogActivity.addLog(type,tag,value);
    }

    public static void LogW(String tag, String value, boolean toast) {
        if(toast) {
            ToastUtils.makeToast("Warning: " + value);
        }

        LogW(tag,value);
    }

    public static void LogW(String tag, String value){
        String type = "Warning: ";
        Log.v(tag,value);
        LogActivity.addLog(type,tag,value);
    }

    public static void LogV(String tag, String value, boolean toast) {
        if(toast) {
            ToastUtils.makeToast("Message: " + value);
        }

        LogV(tag,value);
    }

    public static void LogV(String tag, String value){
        String type = "Message: ";
        Log.v(tag,value);
        //LogActivity.addLog(type,tag,value);
    }


}
