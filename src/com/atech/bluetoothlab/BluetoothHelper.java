package com.atech.bluetoothlab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.util.Log;
import android.widget.Toast;

public class BluetoothHelper {

	private static final String TAG = "BluetoothHelper";
	private static final String[] SUPPORTED_DEVICES = { "APEX3" };

	public enum BluetoothHelperEvent {
		NOT_SUPPORTED, NOT_ENABLED, CONNECTION_FAILED;
	}

	private static BluetoothHelper instance;
	private BluetoothAdapter mBluetoothAdapter;
	private BluetoothHelperEventListener eventListener;
	private ConnectThread connection;

	private BluetoothHelper() {
	}

	public static BluetoothHelper instance() {
		if (null == instance) {
			instance = new BluetoothHelper();
		}
		return instance;
	}

	public BluetoothAdapter getBluetoothAdapter() {
		// lazy instatiation for bluetooth adapter object
		if (mBluetoothAdapter == null) {
			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		}
		return mBluetoothAdapter;
	}

	public List<BluetoothDevice> getPairedDevices() {
		List<BluetoothDevice> pairedDevices = null;
		if (getBluetoothAdapter() != null) {

			// Device does not support Bluetooth
			if (!mBluetoothAdapter.isEnabled() && eventListener != null) {
				eventListener.bluetoothEventChange(BluetoothHelperEvent.NOT_ENABLED);
				// notify in case bluetooth is not enable
			}

			// return the list of paired devices anyway
			pairedDevices = new ArrayList(getBluetoothAdapter().getBondedDevices());
		} else {
			eventListener.bluetoothEventChange(BluetoothHelperEvent.NOT_SUPPORTED);
			// notify there is not support for bluetooth
		}

		return pairedDevices;
	}

	public BluetoothHelper discover() {

		return this;
	}

	public BluetoothHelper setOnBuetoothHelperEventListener(BluetoothHelperEventListener eventListener) {
		this.eventListener = eventListener;
		return this;
	}

	public interface BluetoothHelperEventListener {
		public void bluetoothEventChange(BluetoothHelperEvent event);

		public void bluetoothConnectionStart(ConnectThread connection);
	}

	public void connectTo(BluetoothDevice device) {
		ParcelUuid uuids[] = device.getUuids();
		if (uuids != null && uuids.length > 0) {
			UUID uuid = uuids[0].getUuid();// uuid to connect

			if (connection != null) // if there is another connection open
									// cancel that one
				connection.cancel();

			connection = new ConnectThread(device, uuid, mHandler);
			connection.start();

			mHandler.sendEmptyMessage(CONNECTION_START);

		} else {
			// we start fetching the uuids, remember to implement the proper
			// broadcast receiver to get
			// notify when new uuids were discovered
			device.fetchUuidsWithSdp();
		}
	}

	public void destroy() {
		if (connection != null) // if there is another connection open cancel
								// that one
			connection.cancel();
		instance = null; // we remove this instance
	}

	public static final int CONNECTED = 0, CONNECTION_LOST = -1, CONNECTION_FAILED = 2, CANCEL_DISCOVERY = 3,
			CONNECTION_START = 5;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CONNECTION_START:
				if (eventListener != null) {
					// notify that a connection started
					eventListener.bluetoothConnectionStart(connection);
				}
				break;

			case CONNECTED:
				break;
			case CANCEL_DISCOVERY:
				if (getBluetoothAdapter().isDiscovering())
					// before doing a connect if an adapter was set to us we
					// cancel the discovery of any new devices
					getBluetoothAdapter().cancelDiscovery();
				break;
			case CONNECTION_LOST:
				break;
			case CONNECTION_FAILED:
				if (eventListener != null) {
					// notify that a connection started
					Log.e(TAG,"Notifying connection failed!");
					eventListener.bluetoothEventChange(BluetoothHelperEvent.CONNECTION_FAILED);
				}
				break;

			}
		}
	};

}
