package edu.ucsd.cse110.projects110;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

//disregard this !

public class LocationActivityClass extends AppCompatActivity {

    private int ID;
    private int Latitude;
    private int Longitude;
    private String Latitude_Key;
    private String Longitude_Key;

    public LocationActivityClass (String Find_Who) {

        if (Find_Who.equals("Friends")) {
            this.ID = R.layout.activity_friends_location;
            this.Latitude = R.id.FriendsLat;
            this.Longitude = R.id.FriendsLong;
            this.Latitude_Key = "FLatitude";
            this.Longitude_Key = "FLongitude";
        }
        else if (Find_Who.equals("Family")) {
            this.ID = R.layout.activity_home_location;
            this.Latitude = R.id.ParentsLat;
            this.Longitude = R.id.ParentsLong;
            this.Latitude_Key = "Platitude";
            this.Longitude_Key = "PLongitude";
        }
        else {
            this.ID = R.layout.activity_my_address;
            this.Latitude = R.id.AddressLat;
            this.Longitude = R.id.AddressLong;
            this.Latitude_Key = "ALatitude";
            this.Longitude_Key = "ALongitude";
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.ID);
        LoadProfile();
    }

    public void onSaveFClicked(View view) {
        saveProfile();
    }

    public void onExitFClicked(View view){
        Intent intent = new Intent(this,LocationActivity.class);
        startActivity(intent);

    }

    public void onBackHomeClicked(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


    public void LoadProfile() {
        SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);
        EditText LatView= findViewById(this.Latitude);
        String latitude= preferences.getString(this.Latitude_Key,"");
        LatView.setText(latitude);
        EditText LongView= findViewById(this.Longitude);
        String longitude= preferences.getString(this.Longitude_Key,"");
        LongView.setText(longitude);
    }
    public void saveProfile() {
        SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        EditText LatView = findViewById(this.Latitude);
        EditText LongView = findViewById(this.Longitude);
        boolean latV=ValidLocations.isValidLat(LatView.getText().toString());
        boolean longV=ValidLocations.isValidLong(LongView.getText().toString());
        if(latV&&longV) {
            editor.putString(this.Latitude_Key, LatView.getText().toString());
            editor.putString(this.Longitude_Key, LongView.getText().toString());
            editor.apply();
        }
        else{
            showAlert("Please enter a valid Coordinate");
        }
    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}