package in.sadrudd.filesyncingutility.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import in.sadrudd.filesyncingutility.sharedpreferences.SharedPreferencesManager;
import in.sadrudd.filesyncingutility.services.CommService;

/**
 * If Auto-Start is turned on, this will start CommService.
 * Created by sjunjo on 22/02/2015.
 */
public class AutoStartReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SharedPreferencesManager.autostartEnabled(context)) {
            context.startService(new Intent(context, CommService.class));
            Log.i("Autostart", "Started");
        }
    }


}
