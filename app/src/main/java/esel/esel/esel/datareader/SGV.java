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
    public String direction;

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

    public void setDirection(double slope_by_minute) {
        direction = "NONE";
        if (slope_by_minute <= (-3.5)) {
            direction = "DoubleDown";
        } else if (slope_by_minute <= (-2)) {
            direction = "SingleDown";
        } else if (slope_by_minute <= (-1)) {
            direction = "FortyFiveDown";
        } else if (slope_by_minute <= (1)) {
            direction = "Flat";
        } else if (slope_by_minute <= (2)) {
            direction = "FortyFiveUp";
        } else if (slope_by_minute <= (3.5)) {
            direction = "SingleUp";
        } else if (slope_by_minute <= (40)) {
            direction = "DoubleUp";
        }
    }
}
