package com.atech.bluetoothlab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;



public class PrinterListFragment extends Fragment implements OnItemClickListener{

	private BluetoothPrinterAdapter adapter;
	private ListView list; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.printer_list, container, false);
		list = (ListView) view.findViewById(R.id.list);
		adapter = new BluetoothPrinterAdapter(getActivity(), BluetoothHelper.instance().getPairedDevices());
		list.setAdapter(adapter);
		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		list.setOnItemClickListener(this);
		
		
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		list.setItemChecked(position, true);
	}
	
	
	
}
