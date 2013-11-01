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
import android.widget.TextView;

public class PrinterActionListFragment extends Fragment implements
		OnItemClickListener {

	private ArrayAdapter<String> adapter;
	private TextView textProgress;
	private boolean displayActions = false;
	
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

		textProgress = (TextView)getView().findViewById(R.id.progress_text);
		
		ListView list = (ListView) getView().findViewById(R.id.list);
		list.setAdapter(adapter);

		list.setOnItemClickListener(this);

		
		if (savedInstanceState != null) {
			displayActions = savedInstanceState.getBoolean("displayActions");
			displayActions(!displayActions);
		} else {
		Bundle args = getArguments();
		if (args != null && args.containsKey("selected")) {
			connectingToDevice();
		}
		}
	}
	
@Override
public void onSaveInstanceState(Bundle outState) {
	outState.putBoolean("displayActions", displayActions);
	super.onSaveInstanceState(outState);
}
	
	public void connectingToDevice() {
		getView().findViewById(R.id.empty).setVisibility(View.GONE);
		getView().findViewById(R.id.list).setVisibility(View.GONE);
		getView().findViewById(R.id.progress).setVisibility(View.VISIBLE);
	}

	public void displayActions(boolean error) {
		displayActions = !error;
		getView().findViewById(R.id.empty).setVisibility(View.GONE);

		if (error) {
			getView().findViewById(R.id.progress_bar).setVisibility(View.GONE);
			getView().findViewById(R.id.sad_face).setVisibility(View.VISIBLE);
			textProgress.setText(R.string.connection_failed);
		} else {
			getView().findViewById(R.id.sad_face).setVisibility(View.GONE);
			getView().findViewById(R.id.list).setVisibility(View.VISIBLE);
			getView().findViewById(R.id.progress).setVisibility(View.GONE);
		}
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
