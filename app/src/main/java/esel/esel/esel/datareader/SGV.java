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

        if (this.value < 0) { this.value = 38;}
        else if (this.value < 40) { this.value = 39;}
        else if (this.value > 1000) { this.value = 38;}
        else if (this.value > 400) { this.value = 400;}
    }

    @Override
    public String toString(){
        DateFormat df = SimpleDateFormat.getDateTimeInstance();
        return df.format(new Date(timestamp)) + ": " + value;
    }
}
