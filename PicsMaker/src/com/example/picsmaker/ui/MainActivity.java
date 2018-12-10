package com.example.picsmaker.ui;

import com.example.picsmaker.R;
import com.example.picsmaker.R.layout;
import com.example.picsmaker.ui.EditActivity;
import com.example.picsmaker.ui.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}
	
	public void init() {
		Button mButton = (Button) findViewById(R.id.add);

		mButton.setOnClickListener(new View.OnClickListener() {
			
			Intent intent = new Intent(MainActivity.this, EditActivity.class);
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(intent);
			
			}
		});
	}
}
