package edu.ucsd.cse110.projects110;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import java.lang.Math;

public class DegreeDiff {
    public static float calculateAngle(double lat1, double lon1, double lat2, double lon2) {


        double dLon = (lon2 - lon1);

        double y = Math.sin(Math.toRadians(dLon)) * Math.cos(Math.toRadians(lat2));
        double x = Math.cos(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
                - Math.sin(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(dLon));

        double angle = Math.atan2(y, x);
        angle = Math.toDegrees(angle);
        angle = (angle + 360) % 360;

        return (float) angle;
    }
    //mock method to test the same algorithm
    public static float MockCalculateAngle(double lat1, double lon1, double lat2, double lon2) {

        double dLon = (lon2 - lon1);

        double y = Math.sin(Math.toRadians(dLon)) * Math.cos(Math.toRadians(lat2));
        double x = Math.cos(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
                - Math.sin(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(dLon));

        double angle = Math.atan2(y, x);
        angle = Math.toDegrees(angle);
        angle = (angle + 360) % 360;

        return (float) angle;
    }
}


