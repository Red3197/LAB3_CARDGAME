package com.example.lab_cardmatchinggame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends Activity {

	Spinner mySpinner;
	Button btn;
	private ArrayAdapter<String> difList;
	final String[] difficults = { "easy" , "normal" , "hard" };
	final Class[] cls={easyGame.class , normalGame.class , hardGame.class};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn=(Button)findViewById(R.id.btn_Ok);
		mySpinner=(Spinner)findViewById(R.id.spinner1);
		
		difList = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, difficults);         
		mySpinner.setAdapter(difList);
		
		btn.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				go();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void go(){
		Intent intent = new Intent();
		intent.setClass(MainActivity.this,
				cls[mySpinner.getLastVisiblePosition()]);
		startActivity(intent);
		MainActivity.this.finish();
	}
	
}
