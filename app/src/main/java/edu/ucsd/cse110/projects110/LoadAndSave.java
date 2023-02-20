package edu.ucsd.cse110.projects110;
import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.EditText;
public interface LoadAndSave {
    public void loadProfile(Activity activity);
    public void saveProfile(Activity activity);
}
