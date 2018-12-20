package com.example.picsmaker.ui;

import java.util.ArrayList;

import com.example.picsmaker.R;
import com.example.picsmaker.domain.CustomEditText;
import com.example.picsmaker.domain.Material;
import com.example.picsmaker.entity.background;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class EditTextActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_text);
		ImageView check = (ImageView)findViewById(R.id.imageView1);
		EditText editText = (EditText)findViewById(R.id.EditText);
		final String text = editText.getText().toString();
		Intent i = getIntent();
		final CustomEditText item = (CustomEditText)i.getSerializableExtra("edittext");
		item.setText(editText.getText().toString());
		check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(EditTextActivity.this, EditActivity.class);
				//intent.putExtra("editText",item);
				startActivity(intent);
			}
			
		});
	}
	
}
