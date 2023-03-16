package edu.ucsd.cse110.projects110;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Outline;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class UserDisplay {

    //used to find which circle

    public static int whichCircle (Activity activity, double UserLat, double UserLong) {

        SharedPreferences preferences = activity.getSharedPreferences("pref", Context.MODE_PRIVATE);
        double OurLat = Double.parseDouble(preferences.getString("OurLat", "0"));
        double OurLong = Double.parseDouble(preferences.getString("OurLong", "0"));
        double UserDistance = DistanceCalculator.calculateDistance(OurLat, OurLong, UserLat, UserLong);
        return DistanceCalculator.circleDecider(UserDistance);

    }

    //used to find which layout

    public static ConstraintLayout WhichLayout (Activity activity, double UserLat, double UserLong) {

       int whichCircle = whichCircle(activity, UserLat, UserLong);

        if (whichCircle == 1) {
            return (ConstraintLayout) activity.findViewById(R.id.Circle1Constraint);
        }
        else if (whichCircle == 2) {
            return (ConstraintLayout) activity.findViewById(R.id.Circle2Constraint);
        }
        else if (whichCircle == 3) {
            return (ConstraintLayout) activity.findViewById(R.id.Circle3Constraint);
        }
        else {
            return (ConstraintLayout) activity.findViewById(R.id.Circle4Constraint);
        }

    }

    //used to find which circle

    public static int Circle (Activity activity, double UserLat, double UserLong) {

        int whichCircle = whichCircle(activity, UserLat, UserLong);

        if (whichCircle == 1) {
            return R.id.Circle1;
        }
        else if (whichCircle == 2) {
            return R.id.Circle2;
        }
        else if (whichCircle == 3) {
            return R.id.Circle3;
        }
        else {
            return R.id.Circle4;
        }
    }

    //adding a user's location
    //
    //input: User's name, lat, long, and UID
    //input: activity (should be 'this' called from main)
    //outputs: a circle on respective circle

    public static void addUserLoaction(Activity activity, String UserName, int UID, double UserLat, double UserLong) {

        //making sure user lat and long in bounds
        boolean LatBool = ValidLocations.isValidLat(Double.toString(UserLat));
        boolean LongBool = ValidLocations.isValidLong(Double.toString(UserLong));

        if (!LatBool || !LongBool) {
            Alert.showAlert("Please enter a valid Coordinate", activity);
            return;
        }

        SharedPreferences preferences = activity.getSharedPreferences("pref", Context.MODE_PRIVATE);

        //find our angular distance from them
        double OurLat = Double.parseDouble(preferences.getString("OurLat", "0"));
        double OurLong = Double.parseDouble(preferences.getString("OurLong", "0"));

        //used to find what radius
        int zoomFactor = preferences.getInt("zoomFactor", 0);

        //ori is only used to find what angle
        double ourOri = Double.parseDouble(preferences.getString("OurOri", "0"));

        //finding which circle it belongs to
        ConstraintLayout CircleLayout = WhichLayout(activity, UserLat, UserLong);
        int whichCircle = whichCircle(activity, UserLat, UserLong);
        int circle = Circle(activity, UserLat, UserLong);

        //adding of text onto screen

        if (CircleLayout.getViewById(UID) == null) {
            TextView displayUser = new TextView(activity);
            displayUser.setText(UserName);
            displayUser.setTextSize(15);
            displayUser.setId(UID);
            CircleLayout.addView(displayUser);
        }

        resizeUserLocation(activity, whichCircle, CircleLayout, circle, UID, UserLat, UserLong);
    }

    public static void resizeUserLocation (Activity activity, int whichCircle, ConstraintLayout whichLayout,
                                           int Circle, int UID, double UserLat, double UserLong) {

        SharedPreferences preferences = activity.getSharedPreferences("pref", Context.MODE_PRIVATE);
        int zoomFactor = preferences.getInt("zoomFactor", 0);
        double OurLat = Double.parseDouble(preferences.getString("OurLat", "0"));
        double OurLong = Double.parseDouble(preferences.getString("OurLong", "0"));

        float Angle = DegreeDiff.calculateAngle(OurLat, OurLong, UserLat, UserLong);
        double ourOri = Double.parseDouble(preferences.getString("OurOri", "0"));
        Angle = Angle-(float)Math.toDegrees(ourOri);

        int radius = ZoomDisplay.radius(whichCircle, zoomFactor);

        ConstraintSet LayoutSet = new ConstraintSet();
        LayoutSet.clone(whichLayout);
        LayoutSet.constrainCircle(UID, Circle, radius, Angle);
        LayoutSet.applyTo(whichLayout);

    }








}
