package in.sadrudd.filesyncingutility;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import in.sadrudd.filesyncingutility.services.BluetoothService;
import in.sadrudd.filesyncingutility.services.CommService;
import in.sadrudd.filesyncingutility.sharedpreferences.SharedPreferencesManager;
import in.sadrudd.filesyncingutility.utils.Constants;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private CommService commService;


    private boolean isBound = false;

    private TextView tvSelectedDevice;


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

    }

    private void initialiseTextViewSelectedDevice(){
        tvSelectedDevice = (TextView) findViewById(R.id.tvSelectedDevice);
        tvSelectedDevice.setText(SharedPreferencesManager.getSelectedDeviceName(this));
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

    private void startBluetoothSelectDeviceJob(){
        JobInfo selectBluetoothDeviceJob = new JobInfo.Builder(
                Constants.BLUETOOTH_SELECT_DEVICE_JOB_ID,
                new ComponentName(this, BluetoothService.class)).build();
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(selectBluetoothDeviceJob);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSelectDevice:

                break;
        }
    }

}
