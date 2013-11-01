package com.atech.bluetoothlab;

import com.atech.bluetoothlab.BluetoothHelper.BluetoothHelperEvent;
import com.atech.bluetoothlab.BluetoothHelper.BluetoothHelperEventListener;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class PrinterActivity extends FragmentActivity implements
		BluetoothHelperEventListener {

	private static final int REQUEST_ENABLE_BT = 200;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		BluetoothHelper.instance().setOnBuetoothHelperEventListener(this);
		setContentView(R.layout.activity_printer_list);

		PrinterListFragment listFragment = new PrinterListFragment();

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.main, listFragment, "main");

		if (isTablet()) {
			PrinterActionListFragment actionFragment = new PrinterActionListFragment();
			ft.add(R.id.sub_main, actionFragment, "detail");
		}

		ft.commit();

	}

	public void deviceSelected(BluetoothDevice item) {
		if (isTablet()) {
			PrinterActionListFragment actionFragment = (PrinterActionListFragment) getSupportFragmentManager()
					.findFragmentByTag("detail");
			actionFragment.connectingToDevice();

		} else {
			PrinterActionListFragment actionFragment = new PrinterActionListFragment();
			Bundle args = new Bundle();
			args.putBoolean("selected", true);
			actionFragment.setArguments(args);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.main, actionFragment, "main")
					.addToBackStack(null).commit();
		}

		// try to connect to this device
		BluetoothHelper.instance().connectTo(item);

	}

	public boolean isTablet() {
		return getResources().getBoolean(R.bool.is_tablet);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT) {
			switch(resultCode) {
				case Activity.RESULT_OK:
					//handle okish state
					break;
				case Activity.RESULT_CANCELED:
					//handle cancellation
					break;
					
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	// in case that bluetooth is not enabled
	@Override
	public void bluetoothEventChange(BluetoothHelperEvent event) {

		switch (event) {
		case NOT_ENABLED:
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			break;
		case NOT_SUPPORTED:
			// if there is no bluetooth
			break;
		case CONNECTION_FAILED:
			//when connection failed
			break;
		}
	}

	@Override
	public void bluetoothConnectionStart(ConnectThread connection) {

	}
	
	@Override
	protected void onDestroy() {
		BluetoothHelper.instance().destroy();
		super.onDestroy();
	}

}
