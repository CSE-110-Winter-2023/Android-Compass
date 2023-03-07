package edu.ucsd.cse110.projects110;
import java.lang.Math;
public class DistanceCalculator {
    public static final double EARTH_RADIUS = 6371.00; // mean radius of the Earth in kilometers

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double lon1Rad = Math.toRadians(lon1);
        double lon2Rad = Math.toRadians(lon2);

        double a = Math.pow(Math.sin((lat2Rad - lat1Rad) / 2), 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.pow(Math.sin((lon2Rad - lon1Rad) / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

        return distance * 0.621371; // Convert from kilometers to miles
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
