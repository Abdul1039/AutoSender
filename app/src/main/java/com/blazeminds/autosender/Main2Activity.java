package com.blazeminds.autosender;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

public class Main2Activity extends AppCompatActivity {
	//private Dialog dialog;
	private GoogleMap googleMap;
	private SeekBar setZoom;
	private Handler handler = new Handler();
	//private SeekBar radius;
	//private EditText radiusValues;
	//private Button setCoordinates;
	private LatLng latLng;
	private static Location getLoc;
	private float getZoom = 15;
	private Switch mView;
	private boolean move = true;
	private SupportMapFragment mapFragment;
	private Button setLocDone;
	private int radius = 50;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		
		Bundle bundle = getIntent().getExtras();
		
		if (bundle != null) {
			radius = bundle.getInt("radius");
		}
		
		//radius = findViewById(R.id.radius);
		//radiusValues = findViewById(R.id.radiusValue);
		//setCoordinates = findViewById(R.id.setCoordinate);
		
	/*	radius.setProgress(2);
		radiusValues.setText(String.valueOf(radius.getProgress()));
		radiusValues.setSelection(radiusValues.getText().length());*/
		
		//	radiusValues.setCursorVisible(false);
		
/*		radiusValues.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				radiusValues.setCursorVisible(true);
				return false;
			}
		});*/
		/*radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, final int i, final boolean b) {
				
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
		
		*/
		/*setCoordinates.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				
	 
				//displayMap(getActivity(), bundle);
			}
		});*/
		
		
		mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
		//mapFragment.onCreate(bundle);
		setLocDone = findViewById(R.id.setLocDone);
		setZoom = findViewById(R.id.setZoom);
		mView = findViewById(R.id.switch1);
		mView.setChecked(false);
		mView.setTextOff("Satellite off");
		final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		final LocationListener locationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				
				getLoc = location;
				
				
			}
			
			@Override
			public void onStatusChanged(String s, int i, Bundle bundle) {
			
			}
			
			@Override
			public void onProviderEnabled(String s) {
			
			}
			
			@Override
			public void onProviderDisabled(String s) {
			
			}
		};
		
		/*  Circle circle = googleMap.addCircle(new CircleOptions()
											  .center(new LatLng(getLoc.getLatitude(),getLoc.getLongitude()))
											  .radius(10000)
											  .strokeColor(Color.RED)
											  .fillColor(Color.BLUE));*/
		
		
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
			} else
				Toast.makeText(this, "Location Permission Required", Toast.LENGTH_SHORT).show();
			return;
		}
		if (manager != null) {
			manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		}
		
		OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
			@Override
			public void onMapReady(GoogleMap googleMap1) {
				Location location = null;
				final LatLng latLng;
				if (ActivityCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
					
					return;
				}
				googleMap = googleMap1;
				if (manager != null) {
					location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				}
				if (location != null) {
					
					latLng = new LatLng(location.getLatitude(), location.getLongitude());
				} else {
					latLng = new LatLng(0, 0);
				}
				googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, getZoom, 10, 0)));
				googleMap.setMyLocationEnabled(true);
				googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
					@Override
					public void onCameraMove() {
						if (move) {
							setZoom.setProgress((int) googleMap.getCameraPosition().zoom);
							
						}
						{
							move = true;
						}
					}
				});
				
				CircleOptions circleOptions = new CircleOptions();
				circleOptions.center(latLng);
				
				circleOptions.strokeColor(Color.RED);
				circleOptions.radius(radius);
				googleMap.addCircle(circleOptions);
				
			}
		};
		
		
		setZoom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				
				getZoom = i;
				
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				move = false;
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			/*	googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(getLoc.getLatitude(), getLoc.getLongitude()), getZoom, 10, 0)));*/
				
				move = false;
				googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(googleMap.getCameraPosition().target.latitude, googleMap.getCameraPosition().target.longitude), getZoom, 10, 0)));
				
			}
		});
		
		mView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				
				if (compoundButton.isChecked()) {
					googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
					mView.setTextOn("Satellite On");
				} else {
					mView.setTextOff("Satellite Off");
					googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				}
			}
		});
		
		setLocDone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				
				
				Intent intent = new Intent();
				intent.putExtra("str", "123");
				setResult(1, intent);
				finish();
				//dialog.dismiss();
			}
		});
		
		
		mapFragment.getMapAsync(onMapReadyCallback);
		
		
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent();
		intent.putExtra("str", "123");
		setResult(1, intent);
		finish();
	}
}
