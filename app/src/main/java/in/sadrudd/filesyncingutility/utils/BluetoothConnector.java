package in.sadrudd.filesyncingutility.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

import java.util.Set;

import in.sadrudd.filesyncingutility.sharedpreferences.SharedPreferencesManager;

/**
 * Created by sjunjo on 02/05/2015.
 */
public class BluetoothConnector {


    private static BluetoothConnector bluetoothConnector;

    private BluetoothAdapter bluetoothAdapter;

    private BluetoothConnector(){

    }

    public static synchronized BluetoothConnector getInstance(){
        if (bluetoothConnector == null){
            bluetoothConnector = new BluetoothConnector();
        }
        return bluetoothConnector;
    }

    public synchronized boolean isBluetoothEnabled(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device does not support Bluetooth
            return false;
        }
        return bluetoothAdapter.isEnabled();
    }


    public synchronized Set<BluetoothDevice> getPairedDevices(){
       return bluetoothAdapter.getBondedDevices();
    }

    private synchronized String findSelectedBluetoothDevice(Set<BluetoothDevice> pairedBluetoothDevices, Context context) {
        for (BluetoothDevice device : pairedBluetoothDevices) {
            if (device.getAddress().equals(SharedPreferencesManager.getSelectedDeviceMacAddress(context))) {
                return device.getAddress();
            }
        }
        return Constants.ERROR_SELECTED_DEVICE_NOT_FOUND;
    }

    public void startDiscovery(){
        bluetoothAdapter.startDiscovery();
    }

    public void stopDiscovery(){
        bluetoothAdapter.cancelDiscovery();
    }

}
