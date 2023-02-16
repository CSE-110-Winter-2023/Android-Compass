package edu.ucsd.cse110.projects110;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;


public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
    }

    public void onExitClicked(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void onFriendsClicked(View view) {
        Intent intent = new Intent(this,FriendsLocationActivity.class);
        startActivity(intent);
    }

    public void onParentsClicked(View view) {
        Intent intent = new Intent(this,HomeLocationActivity.class);
        startActivity(intent);
    }

    public void onMyClicked(View view) {
        Intent intent = new Intent(this,MyAddressActivity.class);
        startActivity(intent);
    }

    //we can have more input
    //menu for user to choose the starting label


}