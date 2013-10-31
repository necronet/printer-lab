package com.atech.bluetoothlab;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.atech.bluetoothlab.BluetoothHelper.BluetoothHelperEventListener;

public class PrinterListFragment extends Fragment implements OnItemClickListener, BluetoothHelperEventListener{

	private static final String TAG = "DEBUG ";  
	private static final int REQUEST_ENABLE_BT = 200;
	private BluetoothPrinterAdapter adapter;
	private ListView list; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.printer_list, container, false);
		list = (ListView) view.findViewById(R.id.list);
		
		BluetoothHelper helper = BluetoothHelper.instance().setOnBuetoothHelperEventListener(this);;
		
		adapter = new BluetoothPrinterAdapter(getActivity(), helper.getPairedDevices());
		list.setAdapter(adapter);
		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		list.setOnItemClickListener(this);
		
		
		return view;
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

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		list.setItemChecked(position, true);
		((PrinterActivity)getActivity()).deviceSelected(adapter.getItem(position));
	}

	
	//in case that bluetooth is not enabled
	@Override
	public void bluetoothNotEnable(BluetoothHelper helper) {
		Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
	}
	
	
	
}
