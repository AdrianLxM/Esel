package esel.esel.esel;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import esel.esel.esel.datareader.Datareader;
import esel.esel.esel.datareader.SGV;
import esel.esel.esel.preferences.Preferences;
import esel.esel.esel.preferences.PrefsFragment;
import esel.esel.esel.receivers.ReadReceiver;
import esel.esel.esel.util.LocalBroadcaster;
import esel.esel.esel.util.SP;
import esel.esel.esel.util.ToastUtils;

public class MainActivity extends MenuActivity {

    private Button buttonReadValue;
    private Button buttonSync;
    private Button buttonExport;
    private TextView textViewValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView(R.layout.activity_main);
        askForBatteryOptimizationPermission();
        buttonReadValue = (Button) findViewById(R.id.button_readvalue);
        buttonSync = (Button) findViewById(R.id.button_manualsync);
        buttonExport = (Button) findViewById(R.id.button_exportdata);
        textViewValue = (TextView) findViewById(R.id.textview_main);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        buttonReadValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    long currentTime = System.currentTimeMillis();

                    long syncTime = 30 * 60 * 1000L;

                    List<SGV> valueArray = Datareader.readDataFromContentProvider(getBaseContext(), 6, currentTime - syncTime);

                    if (valueArray != null && valueArray.size() > 0) {
                        textViewValue.setText("");
                        for (int i = 0; i < valueArray.size(); i++) {
                            SGV sgv = valueArray.get(i);
                            textViewValue.append(sgv.toString() + "\n");
                            //LocalBroadcaster.broadcast(sgv);
                        }
                    } else {
                        ToastUtils.makeToast("DB not readable!");
                    }


                }catch (android.database.CursorIndexOutOfBoundsException eb) {
                        eb.printStackTrace();
                    ToastUtils.makeToast("DB is empty!\nIt can take up to 15min with running Eversense App until values are available!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        buttonSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sync = 8;
                try {

                    sync = SP.getInt("max-sync-hours", sync);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                ReadReceiver receiver = new ReadReceiver();
                int written = receiver.FullSync(getBaseContext(), sync);
                textViewValue.setText("Read " + written + " values from DB\n(last " + sync + " hours)");

            }
        });

        buttonExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sync = 8;
                try {

                    sync = SP.getInt("max-sync-hours", sync);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                ReadReceiver receiver = new ReadReceiver();
                String output = receiver.FullExport(getBaseContext(), sync);

                String filename = "esel_output_" + System.currentTimeMillis() + ".json";
                String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + Environment.DIRECTORY_DOWNLOADS;
                File file = new File(path,filename);
                if(!file.getParentFile().exists()){
                    file.getParentFile().mkdir();
                }
                if(!file.getParentFile().canWrite()){
                    ToastUtils.makeToast("Error: can not write data. Please enable the storage access permission for Esel.");
                }
                if(!file.exists()){
                    try{
                        file.createNewFile();
                        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(output.toString());
                        bufferedWriter.close();
                        textViewValue.setText("Created file " + file.getAbsoluteFile() + " containing values from DB\n(last " + sync + " hours)");
                    }catch(IOException err){
                        ToastUtils.makeToast("Error creating file: " + err.toString() + " occured at: "+  err.getStackTrace().toString());
                    }
                }
            }
        });

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
