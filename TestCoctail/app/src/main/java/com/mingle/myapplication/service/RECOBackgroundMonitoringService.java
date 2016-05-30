package com.mingle.myapplication.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.mingle.myapplication.activity.MainActivity;
import com.mingle.myapplication.R;
import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOBeaconRegionState;
import com.perples.recosdk.RECOMonitoringListener;
import com.perples.recosdk.RECOServiceConnectListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * RECOBackgroundMonitoringService is to monitor regions in the background.
 * 
 * RECOBackgroundMonitoringService는 백그라운드에서 monitoring을 수행합니다.
 */
public class RECOBackgroundMonitoringService extends Service implements RECOMonitoringListener, RECOServiceConnectListener {
	private long mScanDuration = 1*1000L;
	private long mSleepDuration = 10*1000L;
	private long mRegionExpirationTime = 3*1000L;
	private int mNotificationID = 9999;
	
	private RECOBeaconManager mRecoManager;
	private ArrayList<RECOBeaconRegion> mRegions;

	private AudioManager aManager ;

	int test=0;
	private int mNotificationID2=0;
	
	@Override
	public void onCreate() {
		Log.i("RECO_BMS", "onCreate()");
		super.onCreate();
		
		/**
		 * Create an instance of RECOBeaconManager (to set ranging timeout in the background.)
		 * If you do not want to set ranging timeout in the backgournd, create an instance: 
		 * 		mRecoManager = RECOBeaconManager.getInstance(getApplicationContext(), false);
		 * WARNING: It will affect the battery consumption.
		 * 
		 * RECOBeaconManager 인스턴스틀 생성합니다. (백그라운드 ranging timeout 설정)
		 * 백그라운드 ranging timeout을 설정하고 싶지 않으시다면, 다음과 같이 생성하시기 바랍니다.
		 * 		mRecoManager = RECOBeaconManager.getInstance(getApplicationContext(), false); 
		 * 주의: false로 설정 시, 배터리 소모량이 증가합니다.
		 */
		mRecoManager = RECOBeaconManager.getInstance(getApplicationContext(), true);
		aManager = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("RECO_BMS", "onStartCommand()");
		this.bindRECOService();
		
		//this should be set to run in the background.
		//background에서 동작하기 위해서는 반드시 실행되어야 합니다.
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		Log.i("RECO_BMS", "onDestroy()");
		this.tearDown();
		super.onDestroy();
	}

	@Override
	public void onTaskRemoved(Intent rootIntent) {
		Log.i("RECO_BMS", "onTaskRemoved()");
		super.onTaskRemoved(rootIntent);
	}
	
	private void bindRECOService() {
		Log.i("RECO_BMS", "bindRECOService()");
		
		mRegions = new ArrayList<RECOBeaconRegion>();
		this.generateBeaconRegion();
		
		mRecoManager.setMonitoringListener(this);
		mRecoManager.bind(this);
	}
	
	private void generateBeaconRegion() {
		Log.i("RECO_BMS", "generateBeaconRegion()");
		
		RECOBeaconRegion recoRegion;
		
		recoRegion = new RECOBeaconRegion(MainActivity.RECO_UUID, "RECO Sample Region");
		recoRegion.setRegionExpirationTimeMillis(mRegionExpirationTime);
		mRegions.add(recoRegion);
	}
	
	private void startMonitoring() {
		Log.i("RECO_BMS", "startMonitoring()");
		
		mRecoManager.setScanPeriod(mScanDuration);
		mRecoManager.setSleepPeriod(mSleepDuration);
		
		for(RECOBeaconRegion region : mRegions) {
			try {
				mRecoManager.startMonitoringForRegion(region);
			} catch (RemoteException e) {
				Log.e("RECO_BMS", "RemoteException has occured while executing RECOManager.startMonitoringForRegion()");
				e.printStackTrace();
			} catch (NullPointerException e) {
				Log.e("RECO_BMS", "NullPointerException has occured while executing RECOManager.startMonitoringForRegion()");
				e.printStackTrace();
			}
		}
	}
	
	private void stopMonitoring() {
		Log.i("RECO_BMS", "stopMonitoring()");
		
		for(RECOBeaconRegion region : mRegions) {
			try {
				mRecoManager.stopMonitoringForRegion(region);
			} catch (RemoteException e) {
				Log.e("RECO_BMS", "RemoteException has occured while executing RECOManager.stopMonitoringForRegion()");
				e.printStackTrace();
			} catch (NullPointerException e) {
				Log.e("RECO_BMS", "NullPointerException has occured while executing RECOManager.stopMonitoringForRegion()");
				e.printStackTrace();
			}
		}
	}
	
	private void tearDown() {
		Log.i("RECO_BMS", "tearDown()");
		this.stopMonitoring();
		
		try {
			mRecoManager.unbind();
		} catch (RemoteException e) {
			Log.e("RECO_BMS", "RemoteException has occured while executing unbind()");
			e.printStackTrace();
		}
	}
	
	@Override
	public void onServiceConnect() {
		Log.i("RECO_BMS", "onServiceConnect()");
		this.startMonitoring();
		//Write the code when RECOBeaconManager is bound to RECOBeaconService
	}

	@Override
	public void didDetermineStateForRegion(RECOBeaconRegionState state, RECOBeaconRegion region) {
		Log.i("RECO_BMS", "didDetermineStateForRegion()");
		//Write the code when the state of the monitored region is changed
	}

	@Override
	public void didEnterRegion(RECOBeaconRegion region) {
		Log.i("BMS", "didEnterRegion() - " + region.getUniqueIdentifier());
		this.popupNotification("Inside of " + region.getUniqueIdentifier());
		//Write the code when the device is enter the region
		if(!mRegions.isEmpty()) {
		} else {
			Toast.makeText(RECOBackgroundMonitoringService.this, "공백", Toast.LENGTH_SHORT).show();
		}

		//Intent intent = new Intent(getApplicationContext(), ResionCinemaActivity.class);
		//PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
		/*
		try {
			pendingIntent.send();
		}
		catch (PendingIntent.CanceledException e) {
			e.printStackTrace();
		}
		*/

		//startService(new Intent(getApplicationContext(), RECOBackgroundRangingService.class));

	}
	
	@Override
	public void didExitRegion(RECOBeaconRegion region) {
		Log.i("RECO_BMS", "didExitRegion() - " + region.getUniqueIdentifier());
		this.popupNotification("Outside of " + region.getUniqueIdentifier());
		//Write the code when the device is exit the region
	}

	@Override
	public void didStartMonitoringForRegion(RECOBeaconRegion region) {
		Log.i("RECO_BMS", "didStartMonitoringForRegion() - " + region.getUniqueIdentifier());
		//Write the code when starting monitoring the region is started successfully
	}

	private void popupNotification(String msg) {
		Log.i("RECO_BMS", "popupNotification()");
		String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.KOREA).format(new Date());
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification.Builder builder = new Notification.Builder(this).setSmallIcon(R.mipmap.ic_cocktail)
																				.setContentTitle(msg + " " + currentTime)
																				.setContentText(msg);

		Notification.InboxStyle inboxStyle = new Notification.InboxStyle();
		builder.setStyle(inboxStyle);
		nm.cancel(mNotificationID2);
		nm.notify(mNotificationID, builder.build());
		mNotificationID2 = mNotificationID;
		mNotificationID = (mNotificationID - 1) % 1000 + 9000;
		if(mNotificationID < 1) mNotificationID = 9999;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// This method is not used
		return null;
	}
}
