package com.perples.recosample;

import java.util.ArrayList;
import java.util.Collection;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.widget.ListView;

import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECORangingListener;

/**
 * RECORangingActivity class is to range regions in the foreground.
 * 
 * RECORangingActivity 클래스는 foreground 상태에서 ranging을 수행합니다. 
 */
public class RECORangingActivity extends RECOActivity implements RECORangingListener{

	private RECORangingListAdapter mRangingListAdapter;
	private ListView mRegionListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_ranging);
		
		//mRecoManager will be created here. (Refer to the RECOActivity.onCreate())
		//mRecoManager 인스턴스는 여기서 생성됩니다. RECOActivity.onCreate() 메소들르 참고하세요.
				
		//Set RECORangingListener (Required)
		//RECORangingListener 를 설정합니다. (필수)
		mRecoManager.setRangingListener(this);
		
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
		
		mRangingListAdapter = new RECORangingListAdapter(this);
		mRegionListView = (ListView)findViewById(R.id.list_ranging);
		mRegionListView.setAdapter(mRangingListAdapter);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();	
		this.stop(mRegions);
		this.unbind();
	}
	
	private void unbind() {
		try {
			mRecoManager.unbind();
		} catch (RemoteException e) {
			Log.i("RECORangingActivity", "Remote Exception");
			e.printStackTrace();
		}
	}
	
	@Override
	public void onServiceConnect() {
		Log.i("RECORangingActivity", "onServiceConnect()");
		this.start(mRegions);
		//Write the code when RECOBeaconManager is bound to RECOBeaconService
	}

	@Override
	public void didRangeBeaconsInRegion(Collection<RECOBeacon> recoBeacons, RECOBeaconRegion recoRegion) {
		Log.i("RECORangingActivity", "didRangeBeaconsInRegion() region: " + recoRegion.getUniqueIdentifier() + ", number of beacons ranged: " + recoBeacons.size());
		mRangingListAdapter.updateAllBeacons(recoBeacons);
		mRangingListAdapter.notifyDataSetChanged();
		//Write the code when the beacons in the region is received
	}
	
	@Override
	protected void start(ArrayList<RECOBeaconRegion> regions) {
		
		/**
		 * There is a known android bug that some android devices scan BLE devices only once. (link: http://code.google.com/p/android/issues/detail?id=65863)
		 * To resolve the bug in our SDK, you can use setDiscontinuousScan() method of the RECOBeaconManager. 
		 * This method is to set whether the device scans BLE devices continuously or discontinuously. 
		 * The default is set as FALSE. Please set TRUE only for specific devices.
		 * 
		 * mRecoManager.setDiscontinuousScan(true);
		 */
		
		for(RECOBeaconRegion region : regions) {
			try {
				mRecoManager.startRangingBeaconsInRegion(region);
			} catch (RemoteException e) {
				Log.i("RECORangingActivity", "Remote Exception");
				e.printStackTrace();
			} catch (NullPointerException e) {
				Log.i("RECORangingActivity", "Null Pointer Exception");
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void stop(ArrayList<RECOBeaconRegion> regions) {
		for(RECOBeaconRegion region : regions) {
			try {
				mRecoManager.stopRangingBeaconsInRegion(region);
			} catch (RemoteException e) {
				Log.i("RECORangingActivity", "Remote Exception");
				e.printStackTrace();
			} catch (NullPointerException e) {
				Log.i("RECORangingActivity", "Null Pointer Exception");
				e.printStackTrace();
			}
		}
	}

}
