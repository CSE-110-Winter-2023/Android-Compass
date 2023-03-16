package edu.ucsd.cse110.projects110;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OnStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_start);
    }

    //all we do here is save current user's name and give them a UID to share with their friends

    public void onSaveToHomeClicked (View view) {

        SharedPreferences preferences = getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        TextView NameInput = findViewById(R.id.enterName);
        String UserName = NameInput.getText().toString();

        editor.putBoolean("StartScreenCompleted", true);
        editor.putString("CurrUserName", UserName);

        //currently, User's private key is their hashcoded name

        editor.putString("CurrUserUID", String.valueOf(UserName.hashCode()));
        editor.apply();

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}