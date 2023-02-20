package edu.ucsd.cse110.projects110;


import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.widget.EditText;

public class FriendsLoadAndSave implements LoadAndSave{

    public void loadProfile(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("pref",MODE_PRIVATE);
        EditText LatView= activity.findViewById(R.id.FriendsLat);
        String latitude= preferences.getString("FLatitude","");
        LatView.setText(latitude);
        EditText LongView= activity.findViewById(R.id.FriendsLong);
        String longitude= preferences.getString("FLongitude","");
        LongView.setText(longitude);
    }

    public void saveProfile(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        EditText LatView = activity.findViewById(R.id.FriendsLat);
        EditText LongView = activity.findViewById(R.id.FriendsLong);
        boolean latV=ValidLocations.isValidLat(LatView.getText().toString());
        boolean longV=ValidLocations.isValidLong(LongView.getText().toString());
        if(latV&&longV) {
            editor.putString("FLatitude", LatView.getText().toString());
            editor.putString("FLongitude", LongView.getText().toString());
            editor.apply();
        }
        else{
            Alert.showAlert("Please enter a valid Coordinate",activity);
        }
    }

}
