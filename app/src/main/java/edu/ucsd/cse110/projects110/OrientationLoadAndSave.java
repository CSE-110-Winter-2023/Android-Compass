package edu.ucsd.cse110.projects110;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.TextView;

public class OrientationLoadAndSave implements LoadAndSave {

    public void loadProfile(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("pref",MODE_PRIVATE);

        TextView OrientationText= activity.findViewById(R.id.Orientation);
        String orientation = preferences.getString("Orientation","");
        OrientationText.setText(orientation);

        TextView OrientationButton = activity.findViewById(R.id.setOrientation);
        OrientationButton.setText("Set Orientation");
    }


    public void saveProfile(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        TextView orientationInput = activity.findViewById(R.id.Orientation);
        boolean validOrientation = ValidLocations.isValidFloat(orientationInput.getText().toString());
        if (validOrientation) {
            if(!orientationInput.getText().toString().equals("")) {
                editor.putString("Orientation", orientationInput.getText().toString());
            }
            else{
                editor.putString("Orientation", "Orientation");
            }
            editor.apply();
        }
        else {
            Alert.showAlert("Please enter a Valid Orientation Degree",activity);
        }
    }
}
