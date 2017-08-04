package esel.esel.esel;

import android.app.Application;
import android.content.res.Resources;

/**
 * Created by adrian on 04/08/17.
 */

public class Esel extends Application {

    private static Esel sInstance;
    private static Resources sResources;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sResources = getResources();


    }

    public static Esel getsInstance() {
        return sInstance;
    }

    public static Resources getsResources() {
        return sResources;
    }

}
