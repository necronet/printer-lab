package com.atech.bluetoothlab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PrinterActionListFragment extends Fragment {

	private ArrayAdapter adapter; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.action_list, container, false);
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.actions));
		
		ListView list = (ListView)view.findViewById(R.id.list);
		list.setAdapter(adapter);
		
		return view;
	}

	public void displayActions() {
		getView().findViewById(R.id.empty).setVisibility(View.GONE);
		getView().findViewById(R.id.list).setVisibility(View.VISIBLE);
	}
	
}
