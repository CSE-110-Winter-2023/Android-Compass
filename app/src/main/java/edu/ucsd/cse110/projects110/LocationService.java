package edu.ucsd.cse110.projects110;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LocationService implements LocationListener {
    public long currentTime;
    public MutableLiveData<Boolean> active;
    private static LocationService instance;
    private Activity activity;
    private MutableLiveData<Pair<Double,Double>> locationValue;

    private final LocationManager locationManager;

    public static LocationService singleton(Activity activity){
        if(instance==null){
            instance= new LocationService(activity);
        }
        return instance;
    }
    protected LocationService(Activity activity){
        this.locationValue= new MutableLiveData<>();
        this.activity=activity;
        this.locationManager=(LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        this.registerLocationListener();
        this.active = new MutableLiveData<>();
        this.currentTime = System.currentTimeMillis();
    }

    private void registerLocationListener(){
        if(ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity,
                        Manifest.permission.ACCESS_COARSE_LOCATION)!=
                        PackageManager.PERMISSION_GRANTED){

            throw new IllegalStateException("App needs location " +
                    "permission to access latest location");

        }
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0,0,this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location){
        currentTime = System.currentTimeMillis();
        this.active.postValue(true);
        this.locationValue.postValue(new Pair<Double,Double>(location.getLatitude(),
                location.getLongitude()));
    }
    public void unregisterLocationListener(){
        locationManager.removeUpdates(this);
    }

    public LiveData<Pair<Double,Double>> getLocation(){
        return this.locationValue;
    }

    public void setMockOrientationSource(MutableLiveData<Pair<Double,Double>> mockDataSource){
        unregisterLocationListener();
        this.locationValue=mockDataSource;
    }
}
