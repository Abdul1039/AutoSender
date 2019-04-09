package com.blazeminds.autosender;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.google.android.gms.maps.GoogleMap;

/*
 * Created by Abdul on 4/9/2019.
 */

public class FragmentOne extends Fragment {
	
	
	private Handler handler = new Handler();
	private SeekBar radius;
	private EditText radiusValues;
	private Button setCoordinates;
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_one, container, false);
		
		initView(view);
		
		return view;
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
	
	private void initView(View view) {
		
		radius = view.findViewById(R.id.radius);
		radiusValues = view.findViewById(R.id.radiusValue);
		setCoordinates = view.findViewById(R.id.setCoordinate);
		
		radius.setProgress(2);
		radiusValues.setText(String.valueOf(radius.getProgress()));
		radiusValues.setSelection(radiusValues.getText().length());
		
		radiusValues.setCursorVisible(false);
		
		radiusValues.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				radiusValues.setCursorVisible(true);
				return false;
			}
		});
		radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, final int i,final  boolean b) {
				
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						
						radiusValues.setText(String.valueOf(i * 2));
						
						radiusValues.setSelection(radiusValues.getText().length());
					}
				}, 1);
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				radiusValues.setCursorVisible(false);
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			
			}
		});
		
		
		setCoordinates.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				displayMap(getActivity());
			}
		});
	}
	
	private void displayMap(Activity activity){
		
		Dialog dialog = new Dialog(activity,R.style.AppTheme);
		
		dialog.setContentView(R.layout.map);
		
		dialog.show();
		
	}
	
}
