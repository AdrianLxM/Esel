package esel.esel.esel.preferences;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import esel.esel.esel.R;

/**
 * Created by adrian on 04/08/17.
 */


public class PrefsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}