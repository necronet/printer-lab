package com.atech.bluetoothlab;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.ParcelUuid;

public class BluetoothHelper {

	private static final String[] SUPPORTED_DEVICES = {"APEX3"};
	
	
	private static BluetoothHelper instance;
	private BluetoothAdapter mBluetoothAdapter;
	private BluetoothHelperEventListener eventListener;
	private ConnectThread connection;

	private BluetoothHelper() {}

	public static BluetoothHelper instance() {
		if (null == instance) {
			instance = new BluetoothHelper();
		}
		return instance;
	}
	
	public BluetoothAdapter getBluetoothAdapter()
	{
		//lazy instatiation for bluetooth adapter object
		if (mBluetoothAdapter == null) {
			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		}
		return 	mBluetoothAdapter;
	}
	
	public List<BluetoothDevice> getPairedDevices() {
		List<BluetoothDevice> pairedDevices = null;
		if (getBluetoothAdapter() != null) {
			
			// Device does not support Bluetooth
			if (!mBluetoothAdapter.isEnabled() && eventListener != null) {
				eventListener.bluetoothNotEnable(this);//notify in case bluetooth is not enable
			}
	
			//return the list of paired devices anyway
			pairedDevices = new ArrayList(getBluetoothAdapter().getBondedDevices());
			
		}
		
		return pairedDevices;
	}
	
	
	
	public BluetoothHelper setOnBuetoothHelperEventListener (BluetoothHelperEventListener eventListener) {
		this.eventListener = eventListener;
		return this;
	}
	
	public interface BluetoothHelperEventListener {
		public void bluetoothNotEnable(BluetoothHelper helper);
		public void bluetoothConnectionStart(ConnectThread connection);
	}

	public void connectTo(BluetoothDevice device) {
		ParcelUuid uuids[] = device.getUuids();
		if (uuids != null && uuids.length > 0) {
			UUID uuid = uuids[0].getUuid();//uuid to connect
			
			
			if (connection != null) //if there is another connection open cancel that one
				connection.cancel();
			
			connection = new ConnectThread(device, uuid).withBluetoothAdapter(getBluetoothAdapter());
			connection.start();
			
			if (eventListener != null) {
				//notify that a connection started
				eventListener.bluetoothConnectionStart(connection);
			}
		} else {
			//we start fetching the uuids, remember to implement the proper broadcast receiver to get 
			//notify when new uuids were discovered
			device.fetchUuidsWithSdp();
		}
	}

	
}

