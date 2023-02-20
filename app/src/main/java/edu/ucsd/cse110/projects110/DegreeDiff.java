package edu.ucsd.cse110.projects110;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import java.lang.Math;

public class DegreeDiff {
    public static float calculateAngle(Pair<Double,Double> loc1, Pair<Double,Double> loc2) {
        double lat1 = loc1.first;
        double lon1 = loc1.second;
        double lat2 = loc2.first;
        double lon2 = loc2.second;

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


