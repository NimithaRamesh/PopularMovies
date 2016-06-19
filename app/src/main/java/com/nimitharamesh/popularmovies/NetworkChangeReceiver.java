package com.nimitharamesh.popularmovies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by nimitharamesh on 6/1/16.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;


    @Override
    public void onReceive(Context context, Intent intent) {
        int statusCode = NetworkCheck.getConnectivityStatus(context);
        String status = NetworkCheck.getConnectivityStatusString(context);
        if (statusCode == 1 || statusCode == 2) {
            Toast.makeText(context, status, Toast.LENGTH_LONG).show();
            Intent networkIntent = new Intent(context, MainActivity.class);
            networkIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(networkIntent);

        } else {
            Toast.makeText(context, status, Toast.LENGTH_LONG).show();
        }
    }
}