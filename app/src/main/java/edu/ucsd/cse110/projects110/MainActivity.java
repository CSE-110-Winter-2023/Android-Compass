package edu.ucsd.cse110.projects110;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LocationService locationService;
    private OrientationService orientationService;
    private float currOri=0f;
    private TimeService timeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},200);
        }

        SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        locationService= LocationService.singleton(this);
        orientationService=OrientationService.singleton(this);
        TextView textViewO=(TextView) findViewById(R.id.curr_Orientation);
        TextView textView=(TextView) findViewById(R.id.curr_location);
        TextView UserT=(TextView) findViewById(R.id.UserExample);


        //viewModel is used for accessing database
        var viewModel = new ViewModelProvider(this).get(UserListViewModel.class);
        //everything in database here
        LiveData<List<User>> users= viewModel.getUsers();
        //use for loop later to get all locations and their data for later UI update
        locationService.getLocation().observe(this,loc->{
            String setCurrLoc = String.format("Current Location: %.2f, %.2f",loc.first, loc.second);

            editor.putString("OurLat", loc.first.toString());
            editor.putString("OurLong", loc.second.toString());
            editor.apply();

            //TextView userExample = findViewById(R.id.UserExample);

            textView.setText(setCurrLoc);
            users.observe(this, userList -> {
                for(User user:userList){
                    double d= DistanceCalculator.calculateDistance(loc.first,loc.second,user.latitude,user.longitude);
                    float f=DegreeDiff.calculateAngle(loc.first,loc.second,user.latitude,user.longitude);

                   UserDisplay.addUserLoaction(this, user.label, user.public_code.hashCode(), user.latitude, user.longitude);


                    //userExample.setText(Integer.valueOf(user.public_code.hashCode()));

                    Log.i("distance",Double.toString(d));
                    Log.i("Degree",Float.toString(f));
                }
            });
        });



        orientationService.getOrientation().observe(this,Ori->{
            String setCurrOri =String.format("Current Orientation: %.2f",Ori);
            textViewO.setText(setCurrOri);
            currOri=Ori;
            editor.putString("OurOri", Float.toString(currOri));
            editor.apply();
        });

        //Stuff for zoom

        ZoomDisplay.displayZoom(this);

    }

    public void onLaunchLocations(View view) {
        Intent intent = new Intent(this,UserListActivity.class);
        startActivity(intent);
    }

    public void onLaunchChangeLabels(View view) {
        Intent intent = new Intent(this, ChangeLabelsActivity.class);
        startActivity(intent);
    }

    public void onZoomInButtonClicked(View view) {

        SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        int zoomFactor = preferences.getInt("zoomFactor", 0);

        //if zoom factor already at max (3), make it stay 3, else increment.

        if (zoomFactor == 3) {
            editor.putInt("zoomFactor", 3);
            editor.apply();
        }
        else {
            editor.putInt("zoomFactor", zoomFactor + 1);
            editor.apply();
        }

        ZoomDisplay.displayZoom(this);

    }

    public void onZoomOutButtonClicked(View view) {

        SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        int zoomFactor = preferences.getInt("zoomFactor", 0);

        //if zoom factor already at min (0), make it stay 0, else decrement.

        if (zoomFactor == 0) {
            editor.putInt("zoomFactor", 0);
            editor.apply();
        }
        else {
            editor.putInt("zoomFactor", zoomFactor - 1);
            editor.apply();
        }

        ZoomDisplay.displayZoom(this);

    }

   /* @Override
    public void onPause(){
        super.onPause();
        orientationService.unregisterSensorListener();
    }**/

}