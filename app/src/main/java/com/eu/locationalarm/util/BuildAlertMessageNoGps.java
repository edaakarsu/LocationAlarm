package com.eu.locationalarm.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class BuildAlertMessageNoGps  extends Dialog{

    Dialog dialog;

    public BuildAlertMessageNoGps(Context context) {
        super(context);
    }




    public void buildAlertMessageNoGps(Context context) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                        dialog.dismiss();
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                       // startActivityForResult(111,callGPSSettingIntent);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alert = builder.create();
       // if (!isFinishing()) {
            alert.show();
        }



}

