package in.sadrudd.filesyncingutility.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;
import android.widget.Toast;

import java.util.Set;

import in.sadrudd.filesyncingutility.sharedpreferences.SharedPreferencesManager;
import in.sadrudd.filesyncingutility.utils.Constants;

public class BluetoothService extends JobService {

    //TODO do something about the ID length!
    private static final String ID = "in.sadrudd.filesync";

    private static final int REQUEST_ENABLE_BT = 42;

    private int jobID = 0;
    private BluetoothAdapter bluetoothAdapter;
    public BluetoothService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        jobID = params.getJobId();
        Log.i(params.getJobId() + "", "Job started");
        attemptBluetoothConnection();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(ID, "Job stopped");
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
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0){
                performSpecificJob();


            } else {
                // Start discovery

            }
        }
        return true;
    }


    private void performSpecificJob(){
        // Two different possible jobs at the moment:
        // (1) Select Bluetooth device to pair with
        // (2) Do the file sync with the selected Bluetooth Device

        // Find one that matches the MAC address specified in
        // menu screen
        switch (jobID){
            case Constants.BLUETOOTH_SELECT_DEVICE_JOB_ID:

                break;
            case Constants.BLUETOOTH_SERVICE_JOB_ID:
                break;
        }
    }


    private String findSelectedBluetoothDevice(Set<BluetoothDevice> pairedBluetoothDevices){
        for (BluetoothDevice device: pairedBluetoothDevices){
            if (device.getAddress().equals(SharedPreferencesManager.getSelectedDeviceMacAddress(this))){
                return device.getAddress();
            }
        }
        return Constants.ERROR_SELECTED_DEVICE_NOT_FOUND;
    }

    private boolean bluetoothEnabledOnDevice(){
        if (bluetoothAdapter.isEnabled())
            return true;

        // TODO Strings.XML!
        Toast.makeText(this,"Bluetooth not enabled! Please check your device", Toast.LENGTH_LONG)
                .show();
        this.stopSelf();
        return false;
    }

}
