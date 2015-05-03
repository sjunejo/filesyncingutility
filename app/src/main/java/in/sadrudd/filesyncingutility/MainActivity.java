package in.sadrudd.filesyncingutility;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

import in.sadrudd.filesyncingutility.services.CommService;
import in.sadrudd.filesyncingutility.sharedpreferences.SharedPreferencesManager;
import in.sadrudd.filesyncingutility.ui.DeviceViewAdapter;
import in.sadrudd.filesyncingutility.utils.BluetoothConnector;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private CommService commService;


    private boolean isBound = false;

    private TextView tvSelectedDevice;
    private ListView lvDevices;
    private DeviceViewAdapter deviceViewAdapter;

    private Button btnSelectDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService(new Intent(this, CommService.class), commServiceConnection,
                Context.BIND_AUTO_CREATE);
        initialiseUI();
    }


    private void initialiseUI(){
        initialiseTextViewSelectedDevice();
        initialiseListViewOfDevices();
        initialiseButtons();

    }

    private void initialiseTextViewSelectedDevice(){
        tvSelectedDevice = (TextView) findViewById(R.id.tvSelectedDevice);
        tvSelectedDevice.setText(SharedPreferencesManager.getSelectedDeviceName(this));
    }

    private void initialiseListViewOfDevices(){
        lvDevices = (ListView) findViewById(R.id.lvDevices);
        lvDevices.setAdapter(deviceViewAdapter = new DeviceViewAdapter());

    }

    private void initialiseButtons(){
        btnSelectDevice = (Button) findViewById(R.id.btnSelectDevice);
        btnSelectDevice.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void selectBluetoothDevice(){
        if (BluetoothConnector.getInstance().isBluetoothEnabled()){
            Set<BluetoothDevice> pairedDevices = BluetoothConnector.getInstance().getPairedDevices();
            // But what if none is paired?
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice pairedDevice : pairedDevices){
                    deviceViewAdapter.addDevice(pairedDevice);

                    Log.i("TAG", pairedDevice.getName());
                }
            }
             Toast.makeText(this, "Going into discovery mode..", Toast.LENGTH_LONG).show();

            startDiscoveryAndRegisterReceiver();

        }
        else {
            Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_LONG).show();
        }

    }

    private ServiceConnection commServiceConnection = new ServiceConnection() {

        /**
         * Note that onServiceConnected is only called after the service is bound
         *
         */

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            CommService.CommServiceBinder commServiceBinder = (CommService.CommServiceBinder) binder;
            commService = commServiceBinder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void startDiscoveryAndRegisterReceiver(){
        // Discovery mode...
        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(bluetoothDiscoveryReceiver, filter);

        BluetoothConnector.getInstance().startDiscovery();
    }

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver bluetoothDiscoveryReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                Log.i("TAG", "Discovery started.");
            }
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){

                Log.i("TAG", "Discovery finished.");
                unregisterReceiver(this);
            }
            else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                Log.i("TAG", "Device found:" + device.getName());
                deviceViewAdapter.addDevice(device);

            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSelectDevice:
                selectBluetoothDevice();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(bluetoothDiscoveryReceiver);
        BluetoothConnector.getInstance().stopDiscovery();
        super.onDestroy();
    }
}
