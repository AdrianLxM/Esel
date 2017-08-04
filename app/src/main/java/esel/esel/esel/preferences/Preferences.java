package esel.esel.esel.preferences;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by adrian on 04/08/17.
 */


public class Preferences extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragment()).commit();
    }
}