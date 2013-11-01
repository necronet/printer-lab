package com.atech.bluetoothlab;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class ConnectThread extends Thread {

	private BluetoothSocket socket;
	private BluetoothDevice device;
	private BluetoothAdapter adapter;
	
	public ConnectThread(BluetoothDevice device, UUID deviceUUID) {
		BluetoothSocket tmp = null;
		this.device = device;
		// Get a BluetoothSocket to connect with the given BluetoothDevice
		try {
			tmp = device.createInsecureRfcommSocketToServiceRecord(deviceUUID);
		} catch (IOException e) {
			e.printStackTrace();
		}
		socket = tmp;
	}
	
	public ConnectThread withBluetoothAdapter(BluetoothAdapter adapter) {
		this.adapter = adapter;
		return this;
	}
	
	@Override
	public void run() {
		if(adapter != null) {
			//before doing a connect if an adapter was set to us we cancel the discovery of any new devices
			adapter.cancelDiscovery();
		}
	}
	
}
