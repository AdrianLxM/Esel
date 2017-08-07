package esel.esel.esel.datareader;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import esel.esel.esel.Esel;
import esel.esel.esel.R;
import esel.esel.esel.util.SP;

/**
 * Created by adrian on 04/08/17.
 */

public class Datareader {

    public static String readData() throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec("su");
        DataOutputStream dos = new DataOutputStream(p.getOutputStream());

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
        BufferedReader bufferedErrorReader = new BufferedReader(
                new InputStreamReader(p.getErrorStream()));

        String path = SP.getString("db-path-string", Esel.getsResources().getString(R.string.default_db_path));
        dos.writeBytes("sqlite3 -csv " + path + " \"select timestamp, glucoseLevel from glucosereadings order by timestamp desc LIMIT 2;\"\n");
        dos.writeBytes("exit\n");
        dos.flush();
        dos.close();
        p.waitFor();
        String data;

        StringBuilder sb = new StringBuilder("");
        while((data = bufferedErrorReader.readLine()) != null) {
            sb.append(data + "\n");
        }

        SP.putString("last-error", sb.toString());

        if ((data = bufferedReader.readLine()) != null) {
            return data;
        } else {
            return null;
        }
    }

    public static SGV generateSGV(String dataString){
        String[] tokens = dataString.split(",");
        long timestamp = Long.parseLong(tokens[0]);
        int value = Integer.parseInt(tokens[1]);
        return new SGV(value, timestamp);
    }
}
