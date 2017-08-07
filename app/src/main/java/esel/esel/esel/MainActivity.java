package esel.esel.esel;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import esel.esel.esel.datareader.Datareader;
import esel.esel.esel.datareader.SGV;
import esel.esel.esel.preferences.Preferences;
import esel.esel.esel.preferences.PrefsFragment;
import esel.esel.esel.util.LocalBroadcaster;
import esel.esel.esel.util.ToastUtils;

public class MainActivity extends MenuActivity {

    private Button buttonReadValue;
    private TextView textViewValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        askForBatteryOptimizationPermission();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        buttonReadValue = (Button) findViewById(R.id.button_readvalue);
        textViewValue = (TextView) findViewById(R.id.textview_main);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        buttonReadValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String datastring = Datareader.readData();
                    if(datastring !=null) {
                        SGV sgv = Datareader.generateSGV(datastring);
                        textViewValue.setText(sgv.toString());
                    } else {
                        ToastUtils.makeToast("DB not readable!");
                    }
                    //sgv.timestamp = System.currentTimeMillis();
                    //LocalBroadcaster.broadcast(sgv);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void askForBatteryOptimizationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final String packageName = getPackageName();
            final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                final Runnable askOptimizationRunnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                            intent.setData(Uri.parse("package:" + packageName));
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            final String msg = "Device does not appear to support battery optimization whitelisting!";
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.makeToast("Device does not appear to support battery optimization whitelisting!");
                                }
                            });
                        }
                    }
                };

                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Please Allow Permission")
                            .setMessage("ESEL needs battery optimalization whitelisting for proper performance")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    SystemClock.sleep(100);
                                    MainActivity.this.runOnUiThread(askOptimizationRunnable);
                                    dialog.dismiss();
                                }
                            }).show();
                } catch (Exception e) {
                    ToastUtils.makeToast("Please whitelist ESEL in the phone settings.");
                }
            }
        }
    }
}
