package edu.ucsd.cse110.projects110;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MyAddressActivity extends AppCompatActivity {
    LoadAndSave lS= new CurrentAddressLoadAndSave();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        lS.loadProfile(this);
    }

    public void onSaveAClicked(View view) {
        lS.saveProfile(this);
    }

    public void onExitAClicked(View view) {
        Intent intent = new Intent(this,LocationActivity.class);
        startActivity(intent);
    }

    public void onBackHomeClicked(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
/**
    public void LoadProfile() {
        SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);
        EditText LatView= findViewById(R.id.AddressLat);
        String latitude= preferences.getString("ALatitude","");
        LatView.setText(latitude);
        EditText LongView= findViewById(R.id.AddressLong);
        String longitude= preferences.getString("ALongitude","");
        LongView.setText(longitude);
    }
    public void saveProfile() {
        SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        EditText LatView = findViewById(R.id.AddressLat);
        EditText LongView = findViewById(R.id.AddressLong);
        boolean latV=ValidLocations.isValidLat(LatView.getText().toString());
        boolean longV=ValidLocations.isValidLong(LongView.getText().toString());
        if(latV&&longV) {
            editor.putString("ALatitude", LatView.getText().toString());
            editor.putString("ALongitude", LongView.getText().toString());
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
    }**/
}