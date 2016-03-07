package com.perples.recosample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.widget.ListView;

import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOBeaconRegionState;
import com.perples.recosdk.RECOMonitoringListener;

/**
 * RECOMonitoringActivity class is to monitor regions in the foreground.
 * 
 * RECOMonitoringActivity 클래스는 foreground 상태에서 monitoring을 수행합니다. 
 */
public class RECOMonitoringActivity extends RECOActivity implements RECOMonitoringListener {
	
	private RECOMonitoringListAdapter mMonitoringListAdapter;
	private ListView mRegionListView;
	
	private long mScanPeriod = 1000L;
	private long mSleepPeriod = 5000L;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_monitoring);
		
		//mRecoManager will be created here. (Refer to the RECOActivity.onCreate())
		//mRecoManager 인스턴스는 여기서 생성됩니다. RECOActivity.onCreate() 메소들르 참고하세요.
		
		//Set RECOMonitoringListener (Required)
		//RECOMonitoringListener 를 설정합니다. (필수)
		mRecoManager.setMonitoringListener(this);
		
		//Set scan period and sleep period. 
		//The default is 1 second for the scan period and 10 seconds for the sleep period.
		mRecoManager.setScanPeriod(mScanPeriod);
		mRecoManager.setSleepPeriod(mSleepPeriod);
		
		/**
		 * Bind RECOBeaconManager with RECOServiceConnectListener, which is implemented in RECOActivity
		 * You SHOULD call this method to use monitoring/ranging methods successfully.
		 * After binding, onServiceConenct() callback method is called. 
		 * So, please start monitoring/ranging AFTER the CALLBACK is called.
		 * 
		 * RECOServiceConnectListener와 함께 RECOBeaconManager를 bind 합니다. RECOServiceConnectListener는 RECOActivity에 구현되어 있습니다.
		 * monitoring 및 ranging 기능을 사용하기 위해서는, 이 메소드가 "반드시" 호출되어야 합니다.
		 * bind후에, onServiceConnect() 콜백 메소드가 호출됩니다. 콜백 메소드 호출 이후 monitoring / ranging 작업을 수행하시기 바랍니다.
		 */
		mRecoManager.bind(this);
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		
		mMonitoringListAdapter = new RECOMonitoringListAdapter(this);
		mRegionListView = (ListView)findViewById(R.id.list_monitoring);
		mRegionListView.setAdapter(mMonitoringListAdapter);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();	
		this.stop(mRegions);
		this.unbind();
	}
	
	@Override
	public void onServiceConnect() {
		Log.i("RECOMonitoringActivity", "onServiceConnect");
		this.start(mRegions);
		//Write the code when RECOBeaconManager is bound to RECOBeaconService
	}

	@Override
	public void didDetermineStateForRegion(RECOBeaconRegionState recoRegionState, RECOBeaconRegion recoRegion) {
		Log.i("RECOMonitoringActivity", "didDetermineStateForRegion()");
		Log.i("RECOMonitoringActivity", "region: " + recoRegion.getUniqueIdentifier() + ", state: " + recoRegionState.toString());
		
		mMonitoringListAdapter.updateRegion(recoRegion, recoRegionState, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(new Date()));
		mMonitoringListAdapter.notifyDataSetChanged();
		//Write the code when the state of the monitored region is changed
	}

	@Override
	public void didEnterRegion(RECOBeaconRegion recoRegion) {
		Log.i("RECOMonitoringActivity", "didEnterRegion() region:" + recoRegion.getUniqueIdentifier());
		//Write the code when the device is enter the region
	}

	@Override
	public void didExitRegion(RECOBeaconRegion recoRegion) {
		Log.i("RECOMonitoringActivity", "didExitRegion() region:" + recoRegion.getUniqueIdentifier());
		//Write the code when the device is exit the region
	}

	@Override
	public void didStartMonitoringForRegion(RECOBeaconRegion recoRegion) {
		Log.i("RECOMonitoringActivity", "didStartMonitoringForRegion: " + recoRegion.getUniqueIdentifier());
		//Write the code when starting monitoring the region is started successfully
	}

	@Override
	protected void start(ArrayList<RECOBeaconRegion> regions) {
		Log.i("RECOMonitoringActivity", "start");

		for(RECOBeaconRegion region : regions) {
			try {
				region.setRegionExpirationTimeMillis(3*1000L);
				mRecoManager.startMonitoringForRegion(region);
			} catch (RemoteException e) {
				Log.i("RECOMonitoringActivity", "Remote Exception");
				e.printStackTrace();
			} catch (NullPointerException e) {
				Log.i("RECOMonitoringActivity", "Null Pointer Exception");
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void stop(ArrayList<RECOBeaconRegion> regions) {
		for(RECOBeaconRegion region : regions) {
			try {
				mRecoManager.stopMonitoringForRegion(region);
			} catch (RemoteException e) {
				Log.i("RECOMonitoringActivity", "Remote Exception");
				e.printStackTrace();
			} catch (NullPointerException e) {
				Log.i("RECOMonitoringActivity", "Null Pointer Exception");
				e.printStackTrace();
			}
		}
	}
	
	private void unbind() {
		try {
			mRecoManager.unbind();
		} catch (RemoteException e) {
			Log.i("RECOMonitoringActivity", "Remote Exception");
			e.printStackTrace();
		}
	}

}
