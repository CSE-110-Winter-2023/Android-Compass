package edu.ucsd.cse110.projects110;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private LocationService locationService;
    private OrientationService orientationService;
    private float currOri=0f;
    private TimeService timeService;
    private ScheduledFuture<?> poller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*
             Setup and making sure parameters for running app are correct
         */
        SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        boolean startScreenCompleted = preferences.getBoolean("StartScreenCompleted", false);

        if (!startScreenCompleted) {
            Intent intent = new Intent(this,OnStartActivity.class);
            startActivity(intent);
        }

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},200);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
            Startup screen- Run only once to get current user's name
         */


        TextView showUserName = (TextView) findViewById(R.id.CurrUserName);
        String userName = preferences.getString("CurrUserName", "");
        showUserName.setText("Your online name is '" + userName + "'- share this with your friends!");

        /*
            Getting Location and Orientation of current user
         */

        locationService= LocationService.singleton(this);
        orientationService=OrientationService.singleton(this);
        TextView textViewO=(TextView) findViewById(R.id.curr_Orientation);
        TextView textView=(TextView) findViewById(R.id.curr_location);

        //viewModel is used for accessing database
        var viewModel = new ViewModelProvider(this).get(UserListViewModel.class);
        //everything in database here
        LiveData<List<User>> users= viewModel.getUsers();

        /*
            Detecting Online / Offline

         */

        MediatorLiveData<LocationManager> OnlineOrOffline = new MediatorLiveData<>();

        //cancel any threads

        if (this.poller != null && !this.poller.isCancelled()) {
            poller.cancel(true);
        }

        //checks GPS status every second

        ScheduledExecutorService backgroundThreadExecutor = Executors.newSingleThreadScheduledExecutor();
        this.poller = backgroundThreadExecutor.scheduleAtFixedRate(() -> {

                    OnlineOrOffline.postValue((LocationManager)getSystemService( Context.LOCATION_SERVICE ));

                },
                1,1, TimeUnit.SECONDS);

        //If we are online, set text to online; if we are offline, set text to offline

        TextView statusView = findViewById(R.id.OnlineOfflineStatus);
        TextView OfflineTime = findViewById(R.id.OfflineTime);

        OnlineOrOffline.observe(this, status-> {

            if (!status.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                statusView.setText("GPS STATUS: OFF");
                statusView.setTextColor(0xFFFF0000);

                //set up offline time in seconds with background thread executor above
                int SecondsOffline = preferences.getInt("OfflineTime", 0);
                editor.putInt("OfflineTime", SecondsOffline + 1);
                editor.apply();
                SecondsOffline = preferences.getInt("OfflineTime", 0);

                OfflineTime.setText("Offline for " + SecondsOffline + " s");
                OfflineTime.setVisibility(View.VISIBLE);

                double CurrUserLat = Double.parseDouble(preferences.getString("OurLat", "0"));
                double CurrUserLong = Double.parseDouble(preferences.getString("OurLong", "0"));
                String setCurrLoc = String.format("Last Known Location: %.2f, %.2f",CurrUserLat, CurrUserLong);

                textView.setText(setCurrLoc);
            }
            else {
                statusView.setText("GPS STATUS: ON");
                statusView.setTextColor(0xFF5BFC4D);

                //resetting time to 0 and hiding the view
                editor.putInt("OfflineTime", 0);
                editor.apply();
                int SecondsOffline = preferences.getInt("OfflineTime", 0);
                OfflineTime.setText("Offline for " + SecondsOffline + " s");
                OfflineTime.setVisibility(View.INVISIBLE);

                locationService.getLocation().observe(this,loc->{

                    editor.putString("OurLat", loc.first.toString());
                    editor.putString("OurLong", loc.second.toString());
                    editor.apply();

                    double CurrUserLat = Double.parseDouble(preferences.getString("OurLat", "0"));
                    double CurrUserLong = Double.parseDouble(preferences.getString("OurLong", "0"));
                    String setCurrLoc = String.format("Current Location: %.2f, %.2f",CurrUserLat, CurrUserLong);

                    textView.setText(setCurrLoc);
                });
            }

            //use for loop later to get all locations and their data for later UI update

            users.observe(this, userList -> {

                double CurrUserLat = Double.parseDouble(preferences.getString("OurLat", "0"));
                double CurrUserLong = Double.parseDouble(preferences.getString("OurLong", "0"));

                for(User user:userList){
                    double d= DistanceCalculator.calculateDistance(CurrUserLat,CurrUserLong,user.latitude,user.longitude);
                    float f=DegreeDiff.calculateAngle(CurrUserLat,CurrUserLong,user.latitude,user.longitude);

                    UserDisplay.addUserLocation(this, user.label, user.public_code.hashCode(), user.latitude, user.longitude);

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

        /*
            Setting up the display
         */

        ZoomDisplay.displayZoom(this);

        /* NEW - Wednesday 11:58 PM

            Creating a User Object for current user

            Please upload this to online database, but not local database, so we don't have duplicates!

            My idea- if we want to change our name, we can add it into local database when we go to 'users activity,'
            then remove it on exit so it does not show

         */





    }

    /*
        Setting up activity to go to interactive database of friends when clicked.
     */

    public void onLaunchLocations(View view) {
        Intent intent = new Intent(this,UserListActivity.class);
        startActivity(intent);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
        Setting up change labels ---------- OUTDATED
     */

    public void onLaunchChangeLabels(View view) {
        Intent intent = new Intent(this, ChangeLabelsActivity.class);
        startActivity(intent);
    }

    /*
        On clicking Zoom In Button, increment zoom and change display
     */

    public void onZoomInButtonClicked(View view) {

        SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        int zoomFactor = preferences.getInt("zoomFactor", 2);

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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
        On clicking Zoom Out button, decrement zoom and change display
     */

    public void onZoomOutButtonClicked(View view) {

        SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        int zoomFactor = preferences.getInt("zoomFactor", 2);

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