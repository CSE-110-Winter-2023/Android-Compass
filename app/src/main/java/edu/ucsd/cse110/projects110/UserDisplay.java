package edu.ucsd.cse110.projects110;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Outline;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;

public class UserDisplay {

    /*
        This method finds which of the four circles the User will be on
     */

    public static int whichCircle (Activity activity, double UserLat, double UserLong) {

        SharedPreferences preferences = activity.getSharedPreferences("pref", Context.MODE_PRIVATE);
        double OurLat = Double.parseDouble(preferences.getString("OurLat", "0"));
        double OurLong = Double.parseDouble(preferences.getString("OurLong", "0"));
        double UserDistance = DistanceCalculator.calculateDistance(OurLat, OurLong, UserLat, UserLong);
        return DistanceCalculator.circleDecider(UserDistance);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
        This method finds which layout a User will be displayed on
        This is different from the circle- Layout tells us where, circle tells us "how far" to put it
     */

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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
        This method tells us the circle ID the User 'belongs to'- used to pinpoint radius of circle
        or where on the circle the User will be displayed
     */

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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
        This is where a User is actually added to the display.

        A User's name is what will be displayed.

        User's UID will be the unique ID associated with their respective TextView objects.

        User's Lat and Long will be used to find which circle / how far the User is from us (the CurrUser)
     */

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

        //ori is used to find what angle
        double ourOri = Double.parseDouble(preferences.getString("OurOri", "0"));

        //finding which circle the User belongs to
        ConstraintLayout CircleLayout = WhichLayout(activity, UserLat, UserLong);
        int whichCircle = whichCircle(activity, UserLat, UserLong);
        int circle = Circle(activity, UserLat, UserLong);

        //adding of text onto screen, if it already doesn't exist (prevents duplicates)

        if (CircleLayout.getViewById(UID) == null) {
            TextView displayUser = new TextView(activity);
            displayUser.setText(UserName);
            displayUser.setTextSize(15);
            displayUser.setId(UID);
            CircleLayout.addView(displayUser);
        }

        //Displaying the User in the correct ring and distance from the user, which will change based on zoom
        resizeUserLocation(activity, whichCircle, CircleLayout, circle, UID, UserLat, UserLong);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
        Problem: When we create a displayed TextView object, we set it's radius / how far it is for then and there.
                 When we change zoom factor, it should change as well.

        Solution: We 'dynamically' resize where Users are displayed based on the zoom factor.
     */

    public static void resizeUserLocation (Activity activity, int whichCircle, ConstraintLayout whichLayout,
                                           int Circle, int UID, double UserLat, double UserLong) {

        /*
            On re-displaying a user, we must check if their distance 'jumped' a circle.
            If it did, we delete it from the old circle.
         */

        ConstraintLayout[] allLayouts = new ConstraintLayout[4];
        allLayouts[0] = (ConstraintLayout) activity.findViewById(R.id.Circle1Constraint);
        allLayouts[1] = (ConstraintLayout) activity.findViewById(R.id.Circle2Constraint);
        allLayouts[2] = (ConstraintLayout) activity.findViewById(R.id.Circle3Constraint);
        allLayouts[3] = (ConstraintLayout) activity.findViewById(R.id.Circle4Constraint);


        for (ConstraintLayout layout : allLayouts) {

            if (layout != whichLayout) {
                deleteUserLocation(activity, layout, UID);
            }
        }


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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
        Used to delete a User from the display.

        Note that when we use this in our DAO, it takes in CircleLayout, but that can be calculated with only
        a user's lat and long (using the WhichLayout method).

        ****************************
        THIS WILL DELETE IT FROM VIEW, BUT IT WILL DISPLAY AGAIN IF IT IS NOT REMOVED FROM DATABASE
        ****************************
     */
    public static void deleteUserLocation (Activity activity, ConstraintLayout CircleLayout, int UID) {

        //must check if it exists; if it does not exist and we try to remove it, it will break code
        if (CircleLayout.getViewById(UID) != null) {
            CircleLayout.removeView(activity.findViewById(UID));
        }
    }




}
