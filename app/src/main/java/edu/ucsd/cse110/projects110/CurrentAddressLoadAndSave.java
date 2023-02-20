package edu.ucsd.cse110.projects110;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.EditText;

public class CurrentAddressLoadAndSave implements LoadAndSave {
    public void loadProfile(Activity activity){
        SharedPreferences preferences = activity.getSharedPreferences("pref",MODE_PRIVATE);
        EditText LatView= activity.findViewById(R.id.AddressLat);
        String latitude= preferences.getString("ALatitude","");
        LatView.setText(latitude);
        EditText LongView= activity.findViewById(R.id.AddressLong);
        String longitude= preferences.getString("ALongitude","");
        LongView.setText(longitude);

    }

    public void saveProfile(Activity activity){
        SharedPreferences preferences = activity.getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        EditText LatView = activity.findViewById(R.id.AddressLat);
        EditText LongView = activity.findViewById(R.id.AddressLong);
        boolean latV=ValidLocations.isValidLat(LatView.getText().toString());
        boolean longV=ValidLocations.isValidLong(LongView.getText().toString());
        if(latV&&longV) {
            editor.putString("ALatitude", LatView.getText().toString());
            editor.putString("ALongitude", LongView.getText().toString());
            editor.apply();
        }
        else{
            Alert.showAlert("Please enter a valid Coordinate",activity);
        }
    }
}
