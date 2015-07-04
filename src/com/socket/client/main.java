package com.socket.client;

//Download by http://www.codefans.net

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

public class main extends Activity {
	/** Called when the activity is first created. */
	// 定义声明需要用到的UI元素
    
	private EditText edtmsgcontent;
	
	private String ip;
	private int port = 1818;
	TelephonyManager mTm;
	String imei;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private OnSensorEventListener mOnSensorEventListener = new OnSensorEventListener();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		InitView();
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(mOnSensorEventListener, mAccelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);

	}

	private class OnSensorEventListener implements SensorEventListener {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			float[] values = event.values;

			java.util.Locale locale = java.util.Locale.CHINA;
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyyMMddhhmmssSSS", locale);
			Date currentTime = new Date();
			String dateString = formatter.format(currentTime);

			if ((Math.abs(values[0]) > 14 || Math.abs(values[1]) > 14 || Math
					.abs(values[2]) > 14)) {
				try {
					edtmsgcontent.setText(values[0] + "x");
					String msg = imei + "," + dateString;
					if (!TextUtils.isEmpty(msg))
						SendMsg(getIp(), port, msg);
					else {
						// Toast.makeText(this,"请先输入要发送的内容", Toast.LENGTH_LONG);
						edtmsgcontent.requestFocus();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				mSensorManager.unregisterListener(mOnSensorEventListener);
			}
		}

	}
	
	private void InitView() {
		// 显示主界面
		setContentView(R.layout.main);
		//接受数据
		Bundle bundle = this.getIntent().getExtras();

		ip = bundle.getString("value1");
		// 通过id获取ui元素对象
		mTm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		imei = mTm.getDeviceId();
		edtmsgcontent = (EditText) findViewById(R.id.msgcontent);
		/*TextView tv=(TextView)findViewById(R.id.textView1);
		tv.setText("ServerIPAdress:"+ip);*/
		
	}
	public String getIp(){
		Bundle bundle = this.getIntent().getExtras();

		ip = bundle.getString("value1");
		return ip;
	}

	public void SendMsg(String ip, int port, String msg)
			throws UnknownHostException, IOException {
		try {
			Socket socket = null;
			socket = new Socket(ip, port);
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()),true);
			pw.println(msg);
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			String mess  = new String(br.readLine().getBytes("utf-8"),"GBK");
			TextView tv=(TextView)findViewById(R.id.textView1);
			tv.setText("签到成功，欢迎上课！");
			
			pw.close();
			socket.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}