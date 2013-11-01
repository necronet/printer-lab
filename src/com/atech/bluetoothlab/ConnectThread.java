package com.atech.bluetoothlab;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class ConnectThread extends Thread {

	private static final String TAG = "ConnectThread";
	
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
		if (adapter != null && adapter.isDiscovering()) {
			// before doing a connect if an adapter was set to us we cancel the
			// discovery of any new devices
			adapter.cancelDiscovery();
		}

		try {
			// Connect the device through the socket. This will block
			// until it succeeds or throws an exception
			socket.connect();
		} catch (IOException connectException) {
			Log.e(TAG,"connection could not be stablished due to: " + connectException.getMessage());
			
			cancel();
			return;
		}

	}

	/** Will cancel an in-progress connection, and close the socket */
	public void cancel() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
