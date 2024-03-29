package edu.ucsd.cse110.projects110;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        LoadProfile();
    }

    public void LoadProfile() {
        SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);

        TextView ParentLabel = findViewById(R.id.ParentLocation);
        ParentLabel.setText("Set Parents' Location");
        //+ preferences.getString("Parent_Label", "Parent") +
        TextView FriendLabel = findViewById(R.id.FriendLocation);
        FriendLabel.setText("Set Friend's Location");
        //" + preferences.getString("Friend_Label", "Friend") + "
        TextView AddressLabel = findViewById(R.id.AddressLocation);
        AddressLabel.setText("Set Current Address");
               // + preferences.getString("Address_Label", "Home") + "
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