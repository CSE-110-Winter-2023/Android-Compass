package edu.ucsd.cse110.projects110;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private LocationService locationService;
    private OrientationService orientationService;
    private double currOri=0f;
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
        ImageView ParentArrow = findViewById(R.id.parent_arrow);
        ImageView FriendArrow = findViewById(R.id.friend_arrow);
        ImageView AddrArrow = findViewById(R.id.addr_arrow);
        ParentArrow.setVisibility(View.INVISIBLE);
        FriendArrow.setVisibility(View.INVISIBLE);
        AddrArrow.setVisibility(View.INVISIBLE);
        orientationService=OrientationService.singleton(this);

        /*timeService = new TimeService();
        TextView textView1 = findViewById(R.id.textView3);
        timeService.getTime().observe(this,time->{
            textView1.setText(Long.toString(time));
        });**/
        locationService= LocationService.singleton(this);
        TextView textViewO=(TextView) findViewById(R.id.curr_Orientation);
        TextView textView=(TextView) findViewById(R.id.curr_location);

        locationService.getLocation().observe(this,loc->{
            String setCurrLoc = String.format("Current Location: %.2f, %.2f",loc.first, loc.second);
            textView.setText(setCurrLoc);
        });

        String orientationString = preferences.getString("Orientation", "0");
        float orientation = Float.parseFloat(orientationString);

       orientationService.getOrientation().observe(this,Ori->{
           textViewO.setText(String.format("CurrentOrientation: %.2f",orientation - (Ori * 180 / Math.PI)));
           currOri=orientation * (Math.PI / 180) - Ori;
        });

        //parent

        String parentLat=preferences.getString("PLatitude","");
        String parentLong=preferences.getString("PLongitude","");
        if(parentLat.equals("")||parentLong.equals("")) {
            findViewById(R.id.TagP).setVisibility(View.INVISIBLE);
        }
        else {
            TextView ParentTag = findViewById(R.id.TagP);
            MutableLiveData<Pair<Double,Double>> parentsLocation = new MutableLiveData<Pair<Double, Double>>() {
            };
            parentsLocation.postValue(new Pair<>(Double.parseDouble(parentLat), Double.parseDouble(parentLong)));
            LiveData<Pair<Double,Double>> curr= locationService.getLocation();
            parentsLocation.observe(this, pairP -> {
                curr.observe(this, pairC -> {
                    float PAngle = DegreeDiff.calculateAngle(pairC,pairP);
                    PAngle=PAngle+(float)Math.toDegrees(currOri);;
                    ParentTag.setText(preferences.getString("Parent_Label", "Parent"));

                    ConstraintLayout ParentLayout = (ConstraintLayout) findViewById(R.id.constraintLayout2);
                    ConstraintSet ParentLayoutSet = new ConstraintSet();
                    ParentLayoutSet.clone(ParentLayout);
                    ParentLayoutSet.constrainCircle(R.id.TagP, R.id.circle, 475, PAngle);
                    ParentLayoutSet.applyTo(ParentLayout);
                    ParentArrow.setRotation(PAngle);
                    ParentArrow.setVisibility(View.VISIBLE);
                });

            });
        }

        //friends

        String friendLat=preferences.getString("FLatitude","");
        String friendLong=preferences.getString("FLongitude","");
        if(friendLat.equals("")||friendLong.equals("")) {
            findViewById(R.id.TagF).setVisibility(View.INVISIBLE);
        }
        else {
            TextView FriendTag = findViewById(R.id.TagF);
            MutableLiveData<Pair<Double,Double>> friendsLocation = new MutableLiveData<Pair<Double, Double>>() {
            };
            friendsLocation.postValue(new Pair<>(Double.parseDouble(friendLat), Double.parseDouble(friendLong)));
            LiveData<Pair<Double,Double>> curr= locationService.getLocation();
            friendsLocation.observe(this, pairF -> {
                curr.observe(this, pairC -> {
                    float FAngle = DegreeDiff.calculateAngle(pairC,pairF);
                    FAngle=FAngle+(float)Math.toDegrees(currOri);;
                    FriendTag.setText(preferences.getString("Friend_Label", "Friend"));
                    ConstraintLayout FriendLayout = (ConstraintLayout) findViewById(R.id.constraintLayout2);
                    ConstraintSet FriendLayoutSet = new ConstraintSet();
                    FriendLayoutSet.clone(FriendLayout);
                    FriendLayoutSet.constrainCircle(R.id.TagF, R.id.circle, 475, FAngle);
                    FriendLayoutSet.applyTo(FriendLayout);
                    FriendArrow.setRotation(FAngle);
                    FriendArrow.setVisibility(View.VISIBLE);
                });

            });
        }

        //home address

        String addressLat=preferences.getString("ALatitude","");
        String addressLong=preferences.getString("ALongitude","");
        if(addressLat.equals("")||addressLong.equals("")) {
            findViewById(R.id.TagA).setVisibility(View.INVISIBLE);
        }
        else {
            TextView AddressTag = findViewById(R.id.TagA);
            MutableLiveData<Pair<Double,Double>> addressLocation = new MutableLiveData<Pair<Double, Double>>() {
            };
            addressLocation.postValue(new Pair<>(Double.parseDouble(addressLat), Double.parseDouble(addressLong)));
            LiveData<Pair<Double,Double>> curr= locationService.getLocation();
            addressLocation.observe(this, pairA -> {
                curr.observe(this, pairC -> {
                    float AAngle = DegreeDiff.calculateAngle(pairC,pairA);
                    AAngle=AAngle+(float)Math.toDegrees(currOri);
                    AddressTag.setText(preferences.getString("Address_Label", "Address"));
                    ConstraintLayout AddressLayout = (ConstraintLayout) findViewById(R.id.constraintLayout2);
                    ConstraintSet AddressLayoutSet = new ConstraintSet();
                    AddressLayoutSet.clone(AddressLayout);
                    AddressLayoutSet.constrainCircle(R.id.TagA, R.id.circle, 475, AAngle);
                    AddressLayoutSet.applyTo(AddressLayout);
                    AddrArrow.setRotation(AAngle);
                    AddrArrow.setVisibility(View.VISIBLE);
                });

            });
        }


    }

    public void onLaunchLocations(View view) {
        Intent intent = new Intent(this,LocationActivity.class);
        startActivity(intent);
    }

    public void onLaunchChangeLabels(View view) {
        Intent intent = new Intent(this, ChangeLabelsActivity.class);
        startActivity(intent);
    }

   @Override
    public void onPause(){
        super.onPause();
        orientationService.unregisterSensorListener();
    }

}