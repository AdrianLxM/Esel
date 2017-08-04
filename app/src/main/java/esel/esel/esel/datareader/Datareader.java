package esel.esel.esel.datareader;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by adrian on 04/08/17.
 */

public class Datareader {

    public static String readData() throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec("su");
        DataOutputStream dos = new DataOutputStream(p.getOutputStream());

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(p.getInputStream()));

        dos.writeBytes("sqlite3 -csv " + Config.DB_LOCATION + " \"select timestamp, glucoseLevel from glucosereadings order by timestamp desc LIMIT 2;\"\n");
        dos.writeBytes("exit\n");
        dos.flush();
        dos.close();
        p.waitFor();
        String data;

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
