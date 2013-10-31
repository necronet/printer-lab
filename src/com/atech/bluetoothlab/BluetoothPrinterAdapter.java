package com.atech.bluetoothlab;

import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BluetoothPrinterAdapter extends BaseAdapter {

	private Context mContext;
	private List<BluetoothDevice> devices;
	
	public BluetoothPrinterAdapter(Context context, List<BluetoothDevice> devices) {
		mContext = context;
		if (devices != null) {
			this.devices = devices;
		}
	}
	
	@Override
	public int getCount() {
		return devices.size();
	}

	@Override
	public BluetoothDevice getItem(int position) {
		return devices.get(position);
	}

	@Override
	public long getItemId(int position) {
		//unique identifier
		return getItem(position).getAddress().hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//PS. no we don't use the viewholder pattern since this a small data set < 100
		//and is no ui intensive either
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_activated_1, parent, false);	
		}
		
		TextView textView = (TextView)convertView;
		textView.setText(getItem(position).getName());
		
		return convertView;
	}

}
