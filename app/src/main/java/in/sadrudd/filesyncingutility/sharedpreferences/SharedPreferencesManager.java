package in.sadrudd.filesyncingutility.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import in.sadrudd.filesyncingutility.R;
import in.sadrudd.filesyncingutility.utils.Constants;

/**
 * Created by sjunjo on 22/02/2015.
 */
public class SharedPreferencesManager {

    private static final String SHARED_PREFERENCES = "FILESYNC_SHARED_PREFERENCES";
    private static final String SHARED_PREFERENCES_AUTOSTART = "SHARED_PREFERENCES_AUTOSTART";
    private static final String SHARED_PREFERENCES_FOLDER_SYNC_INTERVAL
            = "SHARED_PREFERENCES_FOLDER_SYNC_INTERVAL";

    private static final String SHARED_PREFERENCES_SELECTED_DEVICE_NAME =
            "SHARED_PREFERENCES_SELECTED_DEVICE_NAME";
    private static final String SHARED_PREFERENCES_SELECTED_DEVICE_MAC_ADDRESS =
            "SHARED_PREFERENCES_SELECTED_DEVICE_MAC_ADDRESS";


    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(SHARED_PREFERENCES, 0);
    }

    public static boolean autostartEnabled(Context context){
        return getSharedPreferences(context).getBoolean(SHARED_PREFERENCES_AUTOSTART, false);
    }

    public static long getFolderSyncInterval(Context context){
        return getSharedPreferences(context).getLong(SHARED_PREFERENCES_FOLDER_SYNC_INTERVAL,
                Constants.DEFAULT_FOLDER_SYNC_INTERVAL);
    }

    public static String getSelectedDeviceMacAddress(Context context){
        return getSharedPreferences(context)
                .getString(SHARED_PREFERENCES_SELECTED_DEVICE_MAC_ADDRESS, "");
    }

    public static void saveSelectedDeviceMacAddress(String address, Context context){
        getSharedPreferences(context).edit()
                .putString(SHARED_PREFERENCES_SELECTED_DEVICE_MAC_ADDRESS, address).apply();
    }

    public static String getSelectedDeviceName(Context context){
        return getSharedPreferences(context)
                .getString(SHARED_PREFERENCES_SELECTED_DEVICE_NAME,
                        context.getResources().getString(R.string.no_paired_device));
    }

    public static void saveSelectedDeviceName(String address, Context context){
        getSharedPreferences(context).edit()
                .putString(SHARED_PREFERENCES_SELECTED_DEVICE_NAME, address).apply();
    }




}
