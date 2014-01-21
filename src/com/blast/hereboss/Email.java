package com.blast.hereboss;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hereboss.R;


public class Email extends Activity implements OnClickListener {
	
	TextView email;
	EditText eAddress;
	Button send;
	String emailAddress, name, lat, lng, EA, tick, tock, hTime;
	int hour, minutes, seconds ;
	
	
	protected void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email_layout);
		
		email = (TextView)findViewById(R.id.textView1);
		eAddress = (EditText)findViewById(R.id.editText1);
		send = (Button)findViewById(R.id.button1);
		send.setOnClickListener(this);
		
		Intent intent = getIntent();
		
		name = intent.getStringExtra("name"); 
		lat = intent.getStringExtra("lat"); 
		lng = intent.getStringExtra("long"); 
		
		
		
		
		Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR);
		minutes = c.get(Calendar.MINUTE);
		seconds = c.get(Calendar.SECOND);
		tick = String.valueOf(seconds);
		tock = String.valueOf(minutes);
		hTime = String.valueOf(hour);
		
		email.setText(hTime + ":" + tock + ":" +tick + "\n" 
				+ name + "\n"
				+ "latitude: " + lat + "\n"
				+ "longitude" + lng + "\n");
		
	}
	
	public void onClick(View v){
		EA = eAddress.getText().toString();
		String emailArray[] = { EA };
		String emailMessage = hTime + ":" + tock + ":" +tick + "\n" 
				+ name + "\n"
				+ "latitude: " + lat + "\n"
				+ "longitude" + lng + "\n";
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailArray);
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Clock In/out for " + name);
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailMessage);
		startActivity(emailIntent);
	}
	

}
