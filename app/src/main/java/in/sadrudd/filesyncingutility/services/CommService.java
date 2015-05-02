package in.sadrudd.filesyncingutility.services;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import in.sadrudd.filesyncingutility.sharedpreferences.SharedPreferencesManager;
import in.sadrudd.filesyncingutility.utils.Constants;

/**
 * - Keeps files synced
 * Created by sjunjo on 22/02/2015.
 */
public class CommService extends Service {


    // Debugging
    private static final String DEBUG_TAG = "in.sadrudd.filesyncingutility.services.CommService";

    private final IBinder commServiceBinder = new CommServiceBinder();

    private JobScheduler jobScheduler;

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

    private void scheduleBluetoothJob(){
        JobInfo bluetoothJob = new JobInfo.Builder(Constants.BLUETOOTH_SERVICE_JOB_ID, new ComponentName(this, BluetoothService.class))
                .setPeriodic(SharedPreferencesManager.
                        getFolderSyncInterval(this))
                        .build();

        jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(bluetoothJob);



    }

    private void cancelBluetoothJob(){
        jobScheduler.cancel(Constants.BLUETOOTH_SERVICE_JOB_ID);
    }

    public class CommServiceBinder extends Binder {

        public CommService getService(){
           return CommService.this;
        }

    }
}
