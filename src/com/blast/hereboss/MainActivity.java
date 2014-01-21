package com.blast.hereboss;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hereboss.R;

public class MainActivity extends Activity implements LocationListener {
	TextView longitude, latitude, longHeading, latHeading;
	EditText name;
	DigitalClock clock;
	Button clockIn;
	String provider;
	LocationManager lm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
		//get location manager
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = lm.getBestProvider(criteria, false);
		Location location = lm.getLastKnownLocation(provider);
		boolean enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		
		//init long & lat fields
		if (location != null){
			System.out.println("Provider" + provider + " has been selected.");
			onLocationChanged(location);
		} else {
			latitude.setText("Location Not Available");
			longitude.setText("Location Not Available");
		}
		/*
		 * check if gps is enabled
		 */
		if (!enabled){
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		}
	}
	
	protected void onResume() {
		super.onResume();
		lm.requestLocationUpdates(provider, 400, 1, this);
	}
	
	protected void onPause() {
		super.onPause();
		lm.removeUpdates((LocationListener) this);
	}


	private void init() {
		// TODO Auto-generated method stub
		clock =(DigitalClock)findViewById(R.id.digitalClock1);
		longitude = (TextView)findViewById(R.id.longTV);
		longHeading = (TextView)findViewById(R.id.longHeading);
		latitude = (TextView)findViewById(R.id.latTV);
		latHeading = (TextView)findViewById(R.id.latHeading);
		name = (EditText)findViewById(R.id.nameET);
		clockIn = (Button)findViewById(R.id.clockIn);
		
		
		clockIn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, com.blast.hereboss.Email.class);
				i.putExtra("lat", latitude.getText().toString());
				i.putExtra("long", longitude.getText().toString());
				i.putExtra("name", name.getText().toString());
				startActivity(i);
			}
		});
		
	}
	
	

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		double lat = (double) (location.getLatitude());
		double lng = (double) (location.getLongitude());
		latitude.setText(String.valueOf(lat));
		longitude.setText(String.valueOf(lng));
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Enabled new provider " + provider,
		        Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Disabled provider " + provider,
		        Toast.LENGTH_SHORT).show();
	}

	

}
