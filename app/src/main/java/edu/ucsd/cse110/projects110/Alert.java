package edu.ucsd.cse110.projects110;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class Alert {
    public static void showAlert(String message, Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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
