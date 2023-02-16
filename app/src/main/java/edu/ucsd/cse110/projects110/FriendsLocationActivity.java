package edu.ucsd.cse110.projects110;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class FriendsLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_location);
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
        EditText LatView= findViewById(R.id.FriendsLat);
        String latitude= preferences.getString("FLatitude","");
        LatView.setText(latitude);
        EditText LongView= findViewById(R.id.FriendsLong);
        String longitude= preferences.getString("FLongitude","");
        LongView.setText(longitude);
    }
    public void saveProfile() {
        SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        EditText LatView = findViewById(R.id.FriendsLat);
        EditText LongView = findViewById(R.id.FriendsLong);
        boolean latV=ValidLocations.isValidLat(LatView.getText().toString());
        boolean longV=ValidLocations.isValidLong(LongView.getText().toString());
        if(latV&&longV) {
            editor.putString("FLatitude", LatView.getText().toString());
            editor.putString("FLongitude", LongView.getText().toString());
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