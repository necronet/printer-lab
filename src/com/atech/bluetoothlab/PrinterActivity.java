package com.atech.bluetoothlab;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class PrinterActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		// try to connect to this device
		BluetoothHelper.instance().connectTo(item);

		if (isTablet()) {
			PrinterActionListFragment actionFragment = (PrinterActionListFragment) getSupportFragmentManager()
					.findFragmentByTag("detail");
			actionFragment.displayActions();

		} else {
			PrinterActionListFragment actionFragment = new PrinterActionListFragment();
			Bundle args = new Bundle();
			args.putBoolean("selected", true);
			actionFragment.setArguments(args);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.main, actionFragment, "main")
					.addToBackStack(null).commit();
		}
	}

	public boolean isTablet() {
		return getResources().getBoolean(R.bool.is_tablet);
	}

}
