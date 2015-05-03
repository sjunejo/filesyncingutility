package in.sadrudd.filesyncingutility.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * - Keeps files synced
 * Created by sjunjo on 22/02/2015.
 */
public class CommService extends Service {


    // Debugging
    private static final String DEBUG_TAG = "in.sadrudd.filesyncingutility.services.CommService";

    private final IBinder commServiceBinder = new CommServiceBinder();


    public CommService(){

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return commServiceBinder;
    }



    public class CommServiceBinder extends Binder {

        public CommService getService(){
           return CommService.this;
        }

    }
}
