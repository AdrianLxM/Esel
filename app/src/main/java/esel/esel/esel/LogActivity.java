package esel.esel.esel;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import esel.esel.esel.util.SP;

public class LogActivity extends MenuActivity {

    private static TextView textViewValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView(R.layout.activity_errors);
        textViewValue = (TextView) findViewById(R.id.textview_main);
        String msg = SP.getString("logging","");
        textViewValue.setText(msg);
    }
    public static void addLog(String type,String tag, String value){
        String msg = SP.getString("logging","");
        int lines_limit = 500;
        String[] lines = msg.split("\n");
        if(lines.length>lines_limit){
            int limit_to = (int)(lines_limit * 0.7);
            StringBuilder strbuild = new StringBuilder();
            for (int i = 0; i<limit_to; i++){
                strbuild = new StringBuilder(strbuild + lines[i] + "\n");
            }
            msg = strbuild.toString();
        }
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String line = currentTime.format(format) + ": "+type + " "  +value;
        msg = line + "\n" + msg;
        SP.putString("logging",msg);

        if(textViewValue != null){
            textViewValue.setText(msg);
        }
    }

}
