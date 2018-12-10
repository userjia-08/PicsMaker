package com.example.picsmaker.ui;

import com.example.picsmaker.R;
import com.example.picsmaker.R.layout;
import com.example.picsmaker.utility.BuildImages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Button btnBuildImage = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initialize();
		
		
	}
	private void initialize() {
		// TODO Auto-generated method stub
		btnBuildImage = (Button) findViewById(R.id.btnBuildImage);
		
		btnBuildImage.setOnClickListener(this);
		
		//创建app同名相册
		BuildImages.CreateGallery(this);
		
	}
	
	public void onClick(View v) {
		//生成图片
		BuildImages.buildImageFromView(this);
		
	}
	
}
