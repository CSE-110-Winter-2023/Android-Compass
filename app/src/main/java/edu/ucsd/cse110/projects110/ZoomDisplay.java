package edu.ucsd.cse110.projects110;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class ZoomDisplay {

    /*
        This method displays the rings according to the zoom factor.
     */

    public static void displayZoom(Activity activity) {

        SharedPreferences preferences = activity.getSharedPreferences("pref", Context.MODE_PRIVATE);

        int zoomFactor = preferences.getInt("zoomFactor", 2);

        //The counter will tell us how zoomed in we are.
        //Because we do not have saved locations implemented, this will help a lot

        TextView counter = activity.findViewById(R.id.counter);

        ConstraintLayout Circle1Layout = (ConstraintLayout) activity.findViewById(R.id.Circle1Constraint);
        ConstraintLayout Circle2Layout = (ConstraintLayout) activity.findViewById(R.id.Circle2Constraint);
        ConstraintLayout Circle3Layout = (ConstraintLayout) activity.findViewById(R.id.Circle3Constraint);
        ConstraintLayout Circle4Layout = (ConstraintLayout) activity.findViewById(R.id.Circle4Constraint);

        ConstraintLayout.LayoutParams Circle1LayoutParams = (ConstraintLayout.LayoutParams) Circle1Layout.getLayoutParams();
        ConstraintLayout.LayoutParams Circle2LayoutParams = (ConstraintLayout.LayoutParams) Circle2Layout.getLayoutParams();
        ConstraintLayout.LayoutParams Circle3LayoutParams = (ConstraintLayout.LayoutParams) Circle3Layout.getLayoutParams();
        ConstraintLayout.LayoutParams Circle4LayoutParams = (ConstraintLayout.LayoutParams) Circle4Layout.getLayoutParams();

        if (zoomFactor == 0) {
            //show all rings
            Circle1Layout.setVisibility(View.VISIBLE);
            Circle2Layout.setVisibility(View.VISIBLE);
            Circle3Layout.setVisibility(View.VISIBLE);
            Circle4Layout.setVisibility(View.VISIBLE);

            //setting to "original" values
            Circle1LayoutParams.width = 500;
            Circle1LayoutParams.height = 500;
            Circle2LayoutParams.width = 700;
            Circle2LayoutParams.height = 700;
            Circle3LayoutParams.width = 900;
            Circle3LayoutParams.height = 900;
            Circle4LayoutParams.width = 1100;
            Circle4LayoutParams.height = 1100;

            counter.setText("Displaying 500+ Mile Radius");
        } else if (zoomFactor == 1) {
            //show first 3 rings
            Circle1Layout.setVisibility(View.VISIBLE);
            Circle2Layout.setVisibility(View.VISIBLE);
            Circle3Layout.setVisibility(View.VISIBLE);
            Circle4Layout.setVisibility(View.GONE);

            //setting first 3 rings
            Circle1LayoutParams.width = 675;
            Circle1LayoutParams.height = 675;
            Circle2LayoutParams.width = 875;
            Circle2LayoutParams.height = 875;
            Circle3LayoutParams.width = 1075;
            Circle3LayoutParams.height = 1075;

            counter.setText("Displaying up to 500 Mile Radius");
        } else if (zoomFactor == 2) {
            //show first 2 rings
            Circle1Layout.setVisibility(View.VISIBLE);
            Circle2Layout.setVisibility(View.VISIBLE);
            Circle3Layout.setVisibility(View.GONE);
            Circle4Layout.setVisibility(View.GONE);

            //setting first 2 rings
            Circle1LayoutParams.width = 850;
            Circle1LayoutParams.height = 850;
            Circle2LayoutParams.width = 1050;
            Circle2LayoutParams.height = 1050;

            counter.setText("Displaying up to 10 Mile Radius");
        } else if (zoomFactor == 3) {
            //show first ring
            Circle1Layout.setVisibility(View.VISIBLE);
            Circle2Layout.setVisibility(View.GONE);
            Circle3Layout.setVisibility(View.GONE);
            Circle4Layout.setVisibility(View.GONE);

            //setting first ring
            Circle1LayoutParams.width = 1025;
            Circle1LayoutParams.height = 1025;

            counter.setText("Displaying up to 1 Mile Radius");
        }

        //setting layout parameters
        Circle1Layout.setLayoutParams(Circle1LayoutParams);
        Circle2Layout.setLayoutParams(Circle2LayoutParams);
        Circle3Layout.setLayoutParams(Circle3LayoutParams);
        Circle4Layout.setLayoutParams(Circle4LayoutParams);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
        This method finds and returns the correct radius per circle for each zoom factor.
        The circles change sizes, so the radius will as well.
     */

    public static int radius (int Circle, int Zoom) {

        int radius = 0;

        switch (Zoom) {

            case 3:

                switch (Circle) {
                    case 1:
                        radius = 480;
                        break;
                    default:

                        //note: '9999999' is an absurdly large #,
                        //representing that we don't want it displaying.

                        radius = 999999;
                }
                break;

            case 2:

                switch (Circle) {
                    case 1:
                        radius = 395;
                        break;
                    case 2:
                        radius = 500;
                        break;
                    default:
                        radius = 9999999;
                }
                break;

            case 1:

                switch (Circle) {
                    case 1:
                        radius = 305;
                        break;
                    case 2:
                        radius = 400;
                        break;
                    case 3:
                        radius = 510;
                        break;
                    default:
                        radius = 9999999;
                }
                break;

            case 0:

                switch (Circle) {
                    case 1:
                        radius = 210;
                        break;
                    case 2:
                        radius = 315;
                        break;
                    case 3:
                        radius = 420;
                        break;
                    case 4:
                        radius = 525;
                        break;
                    default:
                        radius = 9999999;
                }
                break;
        }
        return radius;
    }

}
