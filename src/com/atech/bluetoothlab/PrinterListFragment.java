package com.atech.bluetoothlab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PrinterListFragment extends Fragment implements OnItemClickListener{

	private static final String TAG = "DEBUG ";  
	private BluetoothPrinterAdapter adapter;
	private ListView list; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.printer_list, container, false);
		list = (ListView) view.findViewById(R.id.list);
		
		BluetoothHelper helper = BluetoothHelper.instance();
		
		adapter = new BluetoothPrinterAdapter(getActivity(), helper.getPairedDevices());
		list.setAdapter(adapter);
		list.setChoiceMode(isTablet()? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE);
		list.setOnItemClickListener(this);
		
		
		return view;
	}
	
	private boolean isTablet() {
		return getResources().getBoolean(R.bool.is_tablet);
	}

	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		list.setItemChecked(position, true);
		((PrinterActivity)getActivity()).deviceSelected(adapter.getItem(position));
	}


	
	
	
	
}
