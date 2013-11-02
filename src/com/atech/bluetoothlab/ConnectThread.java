package com.atech.bluetoothlab;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ConnectThread extends Thread {

	private static final String TAG = "ConnectThread";
	
	private BluetoothSocket mSocket;
	private BluetoothDevice mDevice;
	private Handler mHandler;

	public ConnectThread(BluetoothDevice device, UUID deviceUUID, Handler handler) {
		BluetoothSocket tmp = null;
		this.mDevice = device;
		mHandler = handler;
		// Get a BluetoothSocket to connect with the given BluetoothDevice
		try {
			tmp = device.createInsecureRfcommSocketToServiceRecord(deviceUUID);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mSocket = tmp;
	}


	@Override
	public void run() {
		
		if (mHandler != null ) {
			mHandler.sendEmptyMessage(BluetoothHelper.CANCEL_DISCOVERY);
		}

		try {
			// Connect the device through the socket. This will block
			// until it succeeds or throws an exception
			mSocket.connect();
		} catch (IOException connectException) {
			Log.e(TAG,"connection could not be stablished due to: " + connectException.getMessage());
			mHandler.sendEmptyMessage(BluetoothHelper.CONNECTION_FAILED);
			cancel();
			return;
		}
		
		Message msg = mHandler.obtainMessage();
		msg.what = BluetoothHelper.CONNECTED;
		msg.obj = mSocket;
		mHandler.sendMessage(msg);
		
	}

	/** Will cancel an in-progress connection, and close the socket */
	public void cancel() {
		try {
			mSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
