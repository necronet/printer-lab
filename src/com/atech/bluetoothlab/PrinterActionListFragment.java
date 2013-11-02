package com.atech.bluetoothlab;

import java.nio.ByteBuffer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

	private static final String TAG = "PrinterActionListFragment";
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
			Log.d(TAG, "Listview sending data");
			//this are not magic number 
			//more PCL first try to format letter
			
			byte[] data = new byte[1024];
			ByteBuffer dataBuffer = ByteBuffer.wrap(data);
			for(int i = 0; i < 10 ; i ++) {
				byte[] options = {27, 107, (byte)(48 + i)};
				byte[] text = getCharacters(5);
				dataBuffer.put(options);
				dataBuffer.put(text);
				Log.d(TAG, "data: " +options.length + " text " + text.length );
			}	
			Log.d(TAG, "dataarray: " + dataBuffer.array().length);
			BluetoothHelper.instance().sendData(dataBuffer.array());
			break;
		case 1: // printing invoice
			break;
		case 2:// printing invoice style
			break;
		case 3:// printing invoice images
			break;
		}
	}
	
	private byte[] getCharacters(int limit) {
		StringBuffer dataBuffer = new StringBuffer();
		for (int i = 0 ;i < limit; i ++) {
			dataBuffer.append("Hello world ");
		}
		
		return dataBuffer.toString().getBytes();
	}

}
