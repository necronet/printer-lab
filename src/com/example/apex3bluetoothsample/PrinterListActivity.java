package com.example.apex3bluetoothsample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PrinterListActivity extends Activity {

	private ArrayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_printer_list);
		
		ListView list = (ListView) findViewById(R.id.list);
		adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1);
		list.setAdapter(adapter);
		
		
	}


}
