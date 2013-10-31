package com.atech.bluetoothlab;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class PrinterActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_printer_list);
	}

	public void deviceSelected(BluetoothDevice item) {
		if (isTablet()) {
			PrinterActionListFragment actionFragment = (PrinterActionListFragment)getSupportFragmentManager().findFragmentByTag("detail");
			actionFragment.displayActions();
			
		} else {
			PrinterActionListFragment actionFragment = new PrinterActionListFragment();
			Bundle args = new Bundle();
			args.putBoolean("selected", true);
			actionFragment.setArguments(args);
			getSupportFragmentManager().beginTransaction().add(android.R.id.content, actionFragment, "main").addToBackStack(null).commit();
		}
	}

	public boolean isTablet() {
		return getResources().getBoolean(R.bool.is_tablet);
	}

}
