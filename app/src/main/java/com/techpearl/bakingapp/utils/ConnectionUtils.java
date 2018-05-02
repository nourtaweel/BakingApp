package com.techpearl.bakingapp.utils;

/**
 * Created by Nour on 0002, 2/5/18.
 * methods for network
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ConnectionUtils {

    public static boolean isConnected(Context context){
        /* Based on code snippet in
         * https://developer.android.com/training/basics/network-ops/managing.html */
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
