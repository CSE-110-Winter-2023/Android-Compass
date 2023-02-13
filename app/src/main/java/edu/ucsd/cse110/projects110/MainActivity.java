package edu.ucsd.cse110.projects110;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.content.Intent;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private LocationService locationService;
    //private TimeService timeService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.compass_arrow);
        imageView.setVisibility(View.INVISIBLE);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},200);
        }

        locationService= LocationService.singleton(this);

        TextView textView=(TextView) findViewById(R.id.hello);

        locationService.getLocation().observe(this,loc->{
            textView.setText(Double.toString(loc.first)+" , "+Double.toString(loc.second));
        });
        /*
        timeService = new TimeService();
        TextView textView1 = findViewById(R.id.textView3);
        timeService.getTime().observe(this,time->{
            textView1.setText(Long.toString(time));
        });**/

        SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);
        String parentLat=preferences.getString("PLatitude","");
        String parentLong=preferences.getString("PLongitude","");
        TextView hello2= findViewById(R.id.hello2);
        MutableLiveData<Pair<Double,Double>> parentsLocation = new MutableLiveData<Pair<Double, Double>>() {
        };
        parentsLocation.postValue(new Pair<>(Double.parseDouble(parentLat), Double.parseDouble(parentLong)));
        LiveData<Pair<Double,Double>> curr= locationService.getLocation();
        parentsLocation.observe(this, pairP -> {
            curr.observe(this, pairC -> {
                float PAngle = DegreeDiff.calculateAngle(pairC,pairP);
                hello2.setText("Parent's Home Location");
                imageView.setRotation(PAngle-90f);
                imageView.setVisibility(View.VISIBLE);
            });

        });


    }

    public void onLaunchLocations(View view) {
        Intent intent = new Intent(this,LocationActivity.class);
        startActivity(intent);
    }
}