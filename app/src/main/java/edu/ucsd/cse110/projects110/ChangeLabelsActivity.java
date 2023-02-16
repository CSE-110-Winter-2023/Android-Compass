package edu.ucsd.cse110.projects110;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ChangeLabelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_labels);
        LoadProfile();
    }

    public void LoadProfile() {
        SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);

        TextView ParentLabel= findViewById(R.id.ChangeParentID);
        ParentLabel.setText(preferences.getString("Parent_Label", ParentLabel.getText().toString()));

        TextView FriendLabel= findViewById(R.id.ChangeFriendID);
        FriendLabel.setText(preferences.getString("Friend_Label", FriendLabel.getText().toString()));

        TextView AddressLabel= findViewById(R.id.ChangeAddressID);
        AddressLabel.setText(preferences.getString("Address_Label", AddressLabel.getText().toString()));
    }

    public void SaveProfile() {
        SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        TextView ParentLabel = findViewById(R.id.ChangeParentID);
        editor.putString("Parent_Label", ParentLabel.getText().toString());

        TextView FriendLabel = findViewById(R.id.ChangeFriendID);
        editor.putString("Friend_Label", FriendLabel.getText().toString());

        TextView AddressLabel = findViewById(R.id.ChangeAddressID);
        editor.putString("Address_Label", AddressLabel.getText().toString());

        editor.apply();

    }

    public void onExitClicked(View view) {
        SaveProfile();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}