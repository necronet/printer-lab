package com.atech.bluetoothlab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PrinterActionListFragment extends Fragment implements
		OnItemClickListener {

	private ArrayAdapter<String> adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.action_list, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, getResources()
						.getStringArray(R.array.actions));

		ListView list = (ListView) getView().findViewById(R.id.list);
		list.setAdapter(adapter);

		list.setOnItemClickListener(this);
		
		Bundle args = getArguments();
		if (args != null && args.containsKey("selected")) 
		{
			displayActions();
		}
	}

	public void displayActions() {
		getView().findViewById(R.id.empty).setVisibility(View.GONE);
		getView().findViewById(R.id.list).setVisibility(View.VISIBLE);
	}

	@Override
	public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
		switch (pos) {
		case 0: // hello world example
			break;
		case 1: // printing invoice
			break;
		case 2:// printing invoice style
			break;
		case 3:// printing invoice images
			break;
		}
	}

}
