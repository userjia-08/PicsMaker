package com.example.picsmaker.ui;

//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.example.picsmaker.R;
import com.example.picsmaker.R.layout;
import com.example.picsmaker.ui.MainActivity;
import com.example.picsmaker.domain.CustomEditText;
import com.example.picsmaker.domain.Material;
import com.example.picsmaker.entity.background;
import com.example.picsmaker.adapter.BackgroundAdapter;
import com.example.picsmaker.adapter.MaterialAdapter;
import com.example.picsmaker.adapter.TextAdapter;
import com.example.picsmaker.utility.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class EditActivity extends Activity implements AdapterView.OnItemSelectedListener{
	
	private Spinner spinnerBackground;
	private Spinner spinnerMaterial;
	private Spinner spinnerText;
	
	private ArrayList<background> mData = null;
	private ArrayList<Material> mMaterial = null;
	private ArrayList<CustomEditText> mText = null;
	private BackgroundAdapter myAdapter;
	private MaterialAdapter myMaterialAdapter;
	private TextAdapter myTextAdapter;
	private Context mContext;
	//�ж��Ƿ�Ϊ�ս�ȥʱ����onItemSelected�ı�־
	private boolean one_selected = false;
	private boolean two_selected = false;
	private boolean three_selected = false;
	private FrameLayout frame;
	private ImageView success;
	private LayoutParams lp;
	private EditText editText;
	private ImageView confirm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = EditActivity.this;
		setContentView(R.layout.activity_edit);
		mData = new ArrayList<background>();
		mMaterial = new ArrayList<Material>();
		mText = new ArrayList<CustomEditText>();
		frame = (FrameLayout) findViewById(R.id.mylayout);
		confirm = (ImageView) findViewById(R.id.imageView3);
		editText = (EditText) findViewById(R.id.EditText);
		init();    
	}
	
	public void init() {
		spinnerBackground = (Spinner) findViewById(R.id.spinner1);
		spinnerMaterial = (Spinner) findViewById(R.id.spinner2);
		spinnerText = (Spinner) findViewById(R.id.spinner3);
		success = (ImageView) findViewById(R.id.imageView2);
		//final Material mezi = new Material(EditActivity.this,R.drawable.ic_launcher);
		
		mMaterial.add(new Material(EditActivity.this,R.drawable.m1));
		mMaterial.add(new Material(EditActivity.this,R.drawable.m2));
		mMaterial.add(new Material(EditActivity.this,R.drawable.m3));
		mMaterial.add(new Material(EditActivity.this,R.drawable.m4));
		mMaterial.add(new Material(EditActivity.this,R.drawable.m5));
		mMaterial.add(new Material(EditActivity.this,R.drawable.m6));
		mMaterial.add(new Material(EditActivity.this,R.drawable.m7));
		mMaterial.add(new Material(EditActivity.this,R.drawable.m8));
		mMaterial.add(new Material(EditActivity.this,R.drawable.m9));
		mMaterial.add(new Material(EditActivity.this,R.drawable.m10));
		mMaterial.add(new Material(EditActivity.this,R.drawable.m11));
		mMaterial.add(new Material(EditActivity.this,R.drawable.m12));
		mMaterial.add(new Material(EditActivity.this,R.drawable.m13));
		mMaterial.add(new Material(EditActivity.this,R.drawable.m14));
		mMaterial.add(new Material(EditActivity.this,R.drawable.m15));
		mMaterial.add(new Material(EditActivity.this,R.drawable.m16));
		mMaterial.add(new Material(EditActivity.this,R.drawable.m17));
		mMaterial.add(new Material(EditActivity.this,R.drawable.m18));
		mMaterial.add(new Material(EditActivity.this,R.drawable.m19));
		mMaterial.add(new Material(EditActivity.this,R.drawable.m20));
		
		myMaterialAdapter = new MaterialAdapter(this,R.layout.item_spin_material,mMaterial);
		spinnerMaterial.setAdapter(myMaterialAdapter);
		spinnerMaterial.setOnItemSelectedListener((OnItemSelectedListener) this);
		
		mData.add(new background(getResources().getDrawable(R.drawable.back),R.drawable.back,"����1"));
		//mData.add(new background(getResources().getDrawable(R.drawable.b1),R.drawable.b1,"����2"));
		//mData.add(new background(getResources().getDrawable(R.drawable.b2),R.drawable.b2,"����3"));
//		mData.add(new background(getResources().getDrawable(R.drawable.b3),R.drawable.b3,"����4"));
//		mData.add(new background(getResources().getDrawable(R.drawable.b4),R.drawable.b4,"����5"));
//		mData.add(new background(getResources().getDrawable(R.drawable.b5),R.drawable.b5,"����6"));
//		mData.add(new background(getResources().getDrawable(R.drawable.b6),R.drawable.b6,"����7"));
		
		
		
		myAdapter = new BackgroundAdapter(this,R.layout.item_spin_background,mData);
        spinnerBackground.setAdapter(myAdapter);
        spinnerBackground.setOnItemSelectedListener((OnItemSelectedListener) this);
        
        mText.add(new CustomEditText(EditActivity.this,Typeface.SANS_SERIF));
        mText.add(new CustomEditText(EditActivity.this,Typeface.SERIF));
        mText.add(new CustomEditText(EditActivity.this,Typeface.MONOSPACE));
        myTextAdapter = new TextAdapter(this,R.layout.item_spin_text,mText);
        spinnerText.setAdapter(myTextAdapter);
        spinnerText.setOnItemSelectedListener((OnItemSelectedListener) this);
        
        BuildImages.CreateGallery(this);
        
        success.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BuildImages.buildImageFromView(EditActivity.this);
			}
        	
        });
        
        confirm.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RelativeLayout original = (RelativeLayout) findViewById(R.id.relativeLayout1);
				original.setVisibility(View.VISIBLE);
				KeyboardUtils.hideKeyboard(EditActivity.this);
			}
        });
        
        frame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				KeyboardUtils.hideKeyboard(EditActivity.this);
			}
        	
        });
      
	}
	
	

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		 switch (parent.getId()){
         case R.id.spinner1:
             if(one_selected){
                 Toast.makeText(mContext,"���ı�����~��" + parent.getItemAtPosition(position).toString(),
                         Toast.LENGTH_SHORT).show();
                 frame.setBackgroundDrawable(((background) parent.getItemAtPosition(position)).getIcon());
             } else one_selected = true;
         break;
         
         case R.id.spinner2:
        	 if(two_selected){
                final Material selectedItem = ((Material) parent.getItemAtPosition(position));
         		frame.addView(selectedItem);
             } else two_selected = true;
         break;
         
         case R.id.spinner3:
        	 if(three_selected){
        		final Intent intent = new Intent(EditActivity.this, EditTextActivity.class);
        		final CustomEditText selectedItem = ((CustomEditText) parent.getItemAtPosition(position));
        		selectedItem.setOnClickListener(new OnClickListener() {
        			
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//�л�����
						RelativeLayout original = (RelativeLayout) findViewById(R.id.relativeLayout1);
						original.setVisibility(View.GONE);
						
//						LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//								150,
//								150);
//						lp.setMargins(0, 0, 0, 0);
//						((LinearLayout) findViewById(R.id.linearLayout1))
//								.setLayoutParams(lp);
						
					}
        			
        		});
        		frame.addView(selectedItem);
        		selectedItem.setText(editText.getText().toString());
             } else three_selected = true;
         break;
   
     }
	}
//
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
//
//	@Override
//	public void onTextClick() {
//		// TODO Auto-generated method stub
//		Log.d("HHHH", "���textview");
//	}
//	
//	
	

	
}
