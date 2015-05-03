package in.sadrudd.filesyncingutility.ui;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import in.sadrudd.filesyncingutility.R;

/**
 * Created by sjunjo on 02/05/2015.
 */
public class DeviceViewAdapter extends BaseAdapter {

    private List<BluetoothDevice> devices;

    public DeviceViewAdapter(){
        devices = new ArrayList<BluetoothDevice>();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return devices.get(position);
    }

    @Override
    public boolean isEmpty() {
        return devices.size() == 0;
    }

    @Override
    public int getCount() {
        return devices.size();
    }

    public void setDevices(Set<BluetoothDevice> deviceSet){
        devices.clear();
        devices.addAll(deviceSet);
    }

    public void addDevice(BluetoothDevice device){
        // Avoid adding duplicates.
        if (!devices.contains(device))
            devices.add(device);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.device_list_view, parent, false);
        TextView tvDeviceName = (TextView) rowView.findViewById(R.id.tvDeviceName);
        tvDeviceName.setText(devices.get(position).getName());
        TextView tvDeviceMACAddress = (TextView) rowView.findViewById(R.id.tvMACAddress);
        tvDeviceMACAddress.setText(devices.get(position).getAddress());
        return rowView;
    }

}
