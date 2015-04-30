package in.sadrudd.filesyncingutility.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sjunjo on 22/02/2015.
 */
public class SharedPreferencesManager {

    private static final String SHARED_PREFERENCES = "FILESYNC_SHARED_PREFERENCES";
    private static final String SHARED_PREFERENCES_AUTOSTART = "SHARED_PREFERENCES_AUTOSTART";
    private static final String SHARED_PREFERENCES_FOLDER_SYNC_INTERVAL
            = "SHARED_PREFERENCES_FOLDER_SYNC_INTERVAL";


    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(SHARED_PREFERENCES, 0);
    }

    public static boolean autostartEnabled(Context context){
        return getSharedPreferences(context).getBoolean(SHARED_PREFERENCES_AUTOSTART, false);
    }



}
