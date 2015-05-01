package in.sadrudd.filesyncingutility.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.bluetooth.BluetoothAdapter;
import android.util.Log;

public class BluetoothService extends JobService {

    //TODO do something about the ID length!
    private static final String ID = "in.sadrudd.filesync";

    private static final int REQUEST_ENABLE_BT = 42;

    private BluetoothAdapter bluetoothAdapter;
    public BluetoothService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(ID, "Job started");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private boolean attemptBluetoothConnection(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device does not support Bluetooth
            return false;
        }
        if (bluetoothEnabledOnDevice()){
            // TODO Check for paired connection and so on.
        }
        return true;
    }

    private boolean bluetoothEnabledOnDevice(){
        if (bluetoothAdapter.isEnabled())
            return true;

        //Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        //this.startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT);

        return false;
    }
}
