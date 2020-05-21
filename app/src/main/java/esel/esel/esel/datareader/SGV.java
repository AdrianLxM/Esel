package esel.esel.esel.datareader;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import esel.esel.esel.util.SP;

import static android.content.ContentValues.TAG;
import static java.lang.Math.min;

/**
 * Created by adrian on 04/08/17.
 */

public class SGV {
    public int value;
    public int raw;
    public long timestamp;
    public int record;
    public String direction;

    SGV(int value, long timestamp,int record){
        this.value = value;
        this.raw = value;
        this.timestamp = timestamp;
        this.record = record;

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

    public void smooth(int last,boolean onlyDummyRun){
        double value = this.value;
        double lastSmooth = (double)last;

        if(onlyDummyRun){
            SP.putInt("lastReadingRaw", this.value);
            SP.putFloat("readingSmooth",(float)this.value);
            return;
        }

        try{
            lastSmooth = SP.getFloat("readingSmooth",(float)lastSmooth);
        }catch (Exception e){
            //first time: no value available, fallbacksolution is default value
        }
        double factor = SP.getDouble("smooth_factor",0.3,0.0,1.0);
        double correction = SP.getDouble("correction_factor",0.5,0.0,1.0);
        double descent_factor = SP.getDouble("descent_factor",0.0,0.0,1.0);
        int lastRaw = SP.getInt("lastReadingRaw", this.value);

        SP.putInt("lastReadingRaw", this.value);

        double a=lastSmooth+(factor*(value-lastSmooth));
        double smooth=a+(correction*((lastRaw-lastSmooth)+(value-a))/2);

        smooth = smooth - descent_factor*(smooth-min(value,smooth));

        SP.putFloat("readingSmooth",(float)smooth);

        if(this.value > SP.getInt("lower_limit",65)){
            this.value = (int)Math.round(smooth);
        }

        Log.d(TAG, "readDataFromContentProvider called, result = " + this.value);

    }
}
