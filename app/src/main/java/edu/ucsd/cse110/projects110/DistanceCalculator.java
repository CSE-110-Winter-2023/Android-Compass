package edu.ucsd.cse110.projects110;
import java.lang.Math;
public class DistanceCalculator {
    public static final double EARTH_RADIUS = 6371.00; // mean radius of the Earth in kilometers

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double EARTH_RADIUS = 3958.8; // Earth's radius in miles

        double lat1Radians = Math.toRadians(lat1);
        double lon1Radians = Math.toRadians(lon1);
        double lat2Radians = Math.toRadians(lat2);
        double lon2Radians = Math.toRadians(lon2);

        double x = (lon2Radians - lon1Radians) * Math.cos((lat1Radians + lat2Radians) / 2);
        double y = (lat2Radians - lat1Radians);
        double distance = Math.sqrt(x * x + y * y) * EARTH_RADIUS;

        return distance;
    }

    public static final int circleDecider(double distance){
        if(distance<1.0){
            return 1;
        }
        else if(distance<10.0){
            return 2;
        }
        else if (distance<500.0) {
            return 3;
        }
        return 4;
    }
}










