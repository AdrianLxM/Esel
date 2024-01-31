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
    public int value; //unit: md/dl (always used internally)
    public int raw;
    public long timestamp; // UNIX time in ms
    public int record;
    public String direction;

    public SGV(int value, long timestamp, int record){
        this.value = value;
        this.raw = value;
        this.timestamp = timestamp;
        this.record = record;

        if (this.value < 0) { this.value = 38;}
        else if (this.value < 40) { this.value = 39;}
        else if (this.value > 1000) { this.value = 38;}
        else if (this.value > 400) { this.value = 400;}
    }

    static public int Convert(float mmoll){
        float mgdl = mmoll * 18.0182f;
        return Math.round(mgdl);
    }

    @Override
    public String toString(){
        DateFormat df = SimpleDateFormat.getDateTimeInstance();
        return df.format(new Date(timestamp)) + ": " + value;
    }

    public void setDirection(double slope_by_minute) {
        direction = "NONE";
        if (slope_by_minute <= (-3.5d)) {
            direction = "DoubleDown";
        } else if (slope_by_minute <= (-2d)) {
            direction = "SingleDown";
        } else if (slope_by_minute <= (-1d)) {
            direction = "FortyFiveDown";
        } else if (slope_by_minute <= (1d)) {
            direction = "Flat";
        } else if (slope_by_minute <= (2d)) {
            direction = "FortyFiveUp";
        } else if (slope_by_minute <= (3.5d)) {
            direction = "SingleUp";
        } else if (slope_by_minute <= (40d)) {
            direction = "DoubleUp";
        }
    }

    /**
     * Created by bernhard on 2018-11-18.
     */

    public void smooth(int last,boolean enable_smooth){
        double value = (double)this.value;
        double lastSmooth = (double)last;

        if(!enable_smooth){
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
        float lastRaw = SP.getInt("lastReadingRaw", this.value);

        SP.putInt("lastReadingRaw", this.value);

        if(last < 39) {//no useful value, e.g. due to pause in transmitter usage
            lastRaw = this.value;
            lastSmooth = this.value;
        }

        // exponential smoothing, see https://en.wikipedia.org/wiki/Exponential_smoothing
        // y'[t]=y'[t-1] + (a*(y-y'[t-1])) = a*y+(1-a)*y'[t-1]
        // factor is a, value is y, lastSmooth y'[t-1], smooth y'
        // factor between 0 and 1, default 0.3
        // factor = 0: always last smooth (constant)
        // factor = 1: no smoothing
        double smooth=lastSmooth+(factor*(value-lastSmooth));

        // correction: average of delta between raw and smooth value, added to smooth with correction factor
        // correction between 0 and 1, default 0.5
        // correction = 0: no correction, full smoothing
        // correction > 0: less smoothing
        smooth=smooth+(correction*((lastRaw-lastSmooth)+(value-smooth))/2.0d);

        smooth = smooth - descent_factor*(smooth-min(value,smooth));

        SP.putFloat("readingSmooth",(float)smooth);

        if(this.value > SP.getInt("lower_limit",65)){
            this.value = (int)Math.round(smooth);
        }

        Log.d(TAG, "readDataFromContentProvider called, result = " + this.value);

    }
}
