package com.wjbaker.gocart.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.wjbaker.gocart.ui.toasts.ConnectionToast;

/**
 * Created by William on 06/05/2018.
 */
public class ConnectionReceiver extends BroadcastReceiver
{
    /**
     * Stores the broadcast action string of when the connection state changes.
     */
    private static final String CONNECTION_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    public ConnectionReceiver() {}

    /**
     * Called when a broadcast events occurs.
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // Checks whether the events is for the connection changing
        if (intent.getAction().equals(CONNECTION_CHANGE_ACTION))
        {
            ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();

            // Gets whether or not the user's phone has an internet connection
            boolean isConnected = networkInfo != null && networkInfo.isConnected();

            if (isConnected)
            {
                ConnectionToast.show(context, "Internet connected, searching enabled.", true);
            }
            else
            {
                ConnectionToast.show(context, "Internet disconnected, searching disabled.", false);
            }
        }
    }
}
