package edu.ucsd.cse110.projects110;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.widget.EditText;

public class ParentsLoadAndSave implements LoadAndSave{
    public void loadProfile(Activity activity){
        SharedPreferences preferences = activity.getSharedPreferences("pref",MODE_PRIVATE);
        EditText LatView= activity.findViewById(R.id.ParentsLat);
        String latitude= preferences.getString("PLatitude","");
        LatView.setText(latitude);
        EditText LongView= activity.findViewById(R.id.ParentsLong);
        String longitude= preferences.getString("PLongitude","");
        LongView.setText(longitude);
    }
    public void saveProfile(Activity activity){
        SharedPreferences preferences = activity.getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        EditText LatView = activity.findViewById(R.id.ParentsLat);
        EditText LongView = activity.findViewById(R.id.ParentsLong);
        boolean latV=ValidLocations.isValidLat(LatView.getText().toString());
        boolean longV=ValidLocations.isValidLong(LongView.getText().toString());
        if(latV&&longV) {
            editor.putString("PLatitude", LatView.getText().toString());
            editor.putString("PLongitude", LongView.getText().toString());
            editor.apply();
        }
        else{
            Alert.showAlert("Please enter a valid Coordinate",activity);
        }
    }


}
