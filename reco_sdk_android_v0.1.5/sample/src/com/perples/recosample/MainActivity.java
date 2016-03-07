package com.perples.recosample;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	//This is a default proximity uuid of the RECO
	public static final String RECO_UUID = "24DDF411-8CF1-440C-87CD-E368DAF9C93E";
	private static final int REQUEST_ENABLE_BT = 1;
	
	private BluetoothManager mBluetoothManager;
	private BluetoothAdapter mBluetoothAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//If a user device turns off bluetooth, request to turn it on.
		//사용자가 블루투스를 켜도록 요청합니다.
		mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = mBluetoothManager.getAdapter();
		
		if(mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
			Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
			//If the request to turn on bluetooth is denied, the app will be finished.
			//사용자가 블루투스 요청을 허용하지 않았을 경우, 어플리케이션은 종료됩니다.
			finish();
			return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void onResume() {
		Log.i("MainActivity", "onResume()");
		super.onResume();
		
		if(this.isBackgroundMonitoringServiceRunning(this)) {
			ToggleButton toggle = (ToggleButton)findViewById(R.id.backgroundMonitoringToggleButton);
			toggle.setChecked(true);
		}
		
		if(this.isBackgroundRangingServiceRunning(this)) {
			ToggleButton toggle = (ToggleButton)findViewById(R.id.backgroundRangingToggleButton);
			toggle.setChecked(true);
			}
		}
	
		@Override
		protected void onDestroy() {
			Log.i("MainActivity", "onDestroy");
			super.onDestroy();
		}
		
		public void onMonitoringToggleButtonClicked(View v) {
			ToggleButton toggle = (ToggleButton)v;
			if(toggle.isChecked()) {
				Log.i("MainActivity", "onMonitoringToggleButtonClicked off to on");
				Intent intent = new Intent(this, RECOBackgroundMonitoringService.class);
				startService(intent);
			} else {
				Log.i("MainActivity", "onMonitoringToggleButtonClicked on to off");
			stopService(new Intent(this, RECOBackgroundMonitoringService.class));
		}
	}
	
	public void onRangingToggleButtonClicked(View v) {
		ToggleButton toggle = (ToggleButton)v;
		if(toggle.isChecked()) {
			Log.i("MainActivity", "onRangingToggleButtonClicked off to on");
			startService(new Intent(this, RECOBackgroundRangingService.class));
		} else {
			Log.i("MainActivity", "onRangingToggleButtonClicked on to off");
			stopService(new Intent(this, RECOBackgroundRangingService.class));
		}
	}
	
	public void onButtonClicked(View v) {
		Button btn = (Button)v;
		if(btn.getId() == R.id.monitoringButton) {
			final Intent intent = new Intent(this, RECOMonitoringActivity.class);
			startActivity(intent);
		} else {
			final Intent intent = new Intent(this, RECORangingActivity.class);
			startActivity(intent);
		}
	}
	
	private boolean isBackgroundMonitoringServiceRunning(Context context) {
		ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		for(RunningServiceInfo runningService : am.getRunningServices(Integer.MAX_VALUE)) {
			if(RECOBackgroundMonitoringService.class.getName().equals(runningService.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isBackgroundRangingServiceRunning(Context context) {
		ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		for(RunningServiceInfo runningService : am.getRunningServices(Integer.MAX_VALUE)) {
			if(RECOBackgroundRangingService.class.getName().equals(runningService.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

}
