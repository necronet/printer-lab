package com.atech.bluetoothlab;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

public class PrinterWriterThread extends Thread {

	private InputStream mInStream;
	private OutputStream mOutStream;
	private BluetoothSocket mSocket;
	private Handler mHandler;

	public PrinterWriterThread(BluetoothSocket socket, Handler handler) {
		mSocket = socket;
		mHandler = handler;

		// Get the BluetoothSocket input and output streams
		try {
			mInStream = socket.getInputStream();
			mOutStream = socket.getOutputStream();
		} catch (IOException e) {
			Log.e("Bluetooth", "temp sockets not created", e);
		}

	}

	public void run() {
		Log.i("Bluetooth", "BEGIN mConnectedThread");
		byte[] buffer = new byte[1024];
		int bytes;

		// Keep listening to the InputStream while connected
		while (true) {
			try {
				// Read from the InputStream
				bytes = mInStream.read(buffer);
				// Send the obtained bytes to the UI Activity
				mHandler.obtainMessage(BluetoothHelper.READ, bytes, -1, buffer).sendToTarget();
			} catch (IOException e) {
				Log.e("Bluetooth", "we lost the connection", e);
				mHandler.sendEmptyMessage(BluetoothHelper.CONNECTION_LOST);
			}
		}
	}
	
	/**
	 * Write to the connected OutStream.
	 * 
	 * @param buffer
	 *            The bytes to write
	 */
	public void write(byte[] buffer) {
		Log.d("Bluetooth", "buffer to be written");
		try {
			mOutStream.write(buffer);
			Log.d("Bluetooth", "buffer wrote");
			// Share the sent message back to the UI Activity
			mHandler.obtainMessage(BluetoothHelper.WRITE, -1, -1,buffer).sendToTarget();
			Log.d("Bluetooth", "Sending message to handler");
		} catch (IOException e) {
			Log.e("Bluetooth", "Exception during write", e);
		}
	}


	public void cancel() {

		try {
			mInStream.close();
			mOutStream.close();
			mSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
