package esel.esel.esel.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import esel.esel.esel.Esel;


public class ToastUtils {
    public static void makeToast(final String string) {
        Handler mainThread = new Handler(Looper.getMainLooper());
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Esel.getsInstance(), string, Toast.LENGTH_SHORT).show();
            }
        });
    }
}