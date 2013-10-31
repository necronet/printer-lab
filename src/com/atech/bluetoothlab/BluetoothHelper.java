package com.atech.bluetoothlab;

import java.util.ArrayList;
import java.util.List;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

public class BluetoothHelper {

	private static final String[] SUPPORTED_DEVICES = {"APEX3"};
	
	private static BluetoothHelper instance;
	private BluetoothAdapter mBluetoothAdapter;
	private BluetoothHelperEventListener eventListener;

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
	}

	
}

