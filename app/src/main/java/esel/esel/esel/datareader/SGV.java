package esel.esel.esel.datareader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by adrian on 04/08/17.
 */

public class SGV {
    public int value;
    public long timestamp;

    SGV(int value, long timestamp){
        this.value = value;
        this.timestamp = timestamp;
    }

    @Override
    public String toString(){
        DateFormat df = SimpleDateFormat.getDateTimeInstance();
        return df.format(new Date(timestamp)) + ": " + value;
    }
}
