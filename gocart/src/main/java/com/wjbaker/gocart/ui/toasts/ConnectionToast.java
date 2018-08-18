package com.wjbaker.gocart.ui.toasts;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wjbaker.gocart.R;

/**
 * Created by William on 06/05/2018.
 *
 * Displays a toast for when the connection of the phone changes.
 */
public class ConnectionToast
{
    /**
     * Creates a new Toast and displays it.
     *
     * @param context Current context from the broadcast receiver.
     * @param message Message to be displayed in the toast.
     * @param isConnectedIcon Whether the connected icon show be displayed or the disconnected icon.
     */
    public static void show(Context context, String message, boolean isConnectedIcon)
    {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);

        TextView toastMessage = toast.getView().findViewById(android.R.id.message);

        toastMessage.setCompoundDrawablesWithIntrinsicBounds(isConnectedIcon ? R.drawable.icon_network_connected : R.drawable.icon_network_disconnected, 0, 0, 0);
        toastMessage.setCompoundDrawablePadding(32);

        toast.show();
    }
}
