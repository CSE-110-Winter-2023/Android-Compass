package edu.ucsd.cse110.projects110;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class HomeLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_location);
        LoadProfile();
    }

    public void onSavePClicked(View view) {
        saveProfile();
    }
    public void LoadProfile() {
        SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);
        EditText LatView= findViewById(R.id.ParentsLat);
        String latitude= preferences.getString("PLatitude","");
        LatView.setText(latitude);
        EditText LongView= findViewById(R.id.ParentsLong);
        String longitude= preferences.getString("PLongitude","");
        LongView.setText(longitude);
    }
    public void saveProfile() {
        SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        EditText LatView = findViewById(R.id.ParentsLat);
        EditText LongView = findViewById(R.id.ParentsLong);
        boolean latV=ValidLocations.isValidLat(LatView.getText().toString());
        boolean longV=ValidLocations.isValidLong(LongView.getText().toString());
        if(latV&&longV) {
            editor.putString("PLatitude", LatView.getText().toString());
            editor.putString("PLongitude", LongView.getText().toString());
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
    public void onExitPClicked(View view) {
        Intent intent = new Intent(this,LocationActivity.class);
        startActivity(intent);
    }

    public void onBackHomeClicked(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}