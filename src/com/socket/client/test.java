package com.socket.client;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class test extends Activity {
	//** Called when the activity is first created. *//*

	private Button button;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		//获取ip地址
		String MyIPAdress=getLocalIpAddress();
		final EditText data1 = (EditText) findViewById(R.id.data1);
		data1.setText(MyIPAdress);
		button = (Button) findViewById(R.id.button1);

		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				Intent intent = new Intent();
				Bundle bundle = new Bundle();

				bundle.putString("value1", data1.getText().toString());
				

				intent.putExtras(bundle);
				intent.setClass(test.this, main.class);
				startActivity(intent);
			}
		});
	}

	//获取本机ip

	public String getLocalIpAddress() {  
          String ipaddress="";
          
      try {  
          for (Enumeration<NetworkInterface> en = NetworkInterface  
                  .getNetworkInterfaces(); en.hasMoreElements();) {  
              NetworkInterface intf = en.nextElement();  
              for (Enumeration<InetAddress> enumIpAddr = intf  
                      .getInetAddresses(); enumIpAddr.hasMoreElements();) {  
                  InetAddress inetAddress = enumIpAddr.nextElement();  
                  if (!inetAddress.isLoopbackAddress()) {  
                          ipaddress= inetAddress.getHostAddress().toString();  
                  }  
              }  
          }  
      } catch (SocketException ex) {  
          Log.e("WifiPreference IpAddress", ex.toString());  
      }  
      return ipaddress; 
      }
}