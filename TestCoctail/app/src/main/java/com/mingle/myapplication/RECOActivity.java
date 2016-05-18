package com.mingle.myapplication;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECORangingListener;
import com.perples.recosdk.RECOServiceConnectListener;

import java.util.ArrayList;


/**
 * RECOActivity class is the base activity for RECOMonitoringActivity and RECORangingActivity.
 * If you want to implement monitoring or ranging in a single class,
 * you can remove this class and include the methods and RECOServiceConnectListener to each class.
 *
 * RECOActivity 클래스는 RECOMonitoringActivity와 RECORangingActivity를 위한 기본 클래스 입니다.
 * Monitoring 이나 ranging을 단일 클래스로 구성하고 싶으시다면, 이 클래스를 삭제하시고 필요한 메소드와 RECOServiceConnectListener를 해당 클래스에서 구현하시기 바랍니다.
 */
public abstract class RECOActivity extends AppCompatActivity implements RECOServiceConnectListener, RECORangingListener {
    protected RECOBeaconManager mRecoManager;
    protected ArrayList<RECOBeaconRegion> mRegions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        mRegions = this.generateBeaconRegion();


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

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private ArrayList<RECOBeaconRegion> generateBeaconRegion() {
        ArrayList<RECOBeaconRegion> regions = new ArrayList<RECOBeaconRegion>();

        RECOBeaconRegion recoRegion;
        recoRegion = new RECOBeaconRegion("24DDF411-8CF1-440C-87CD-E368DAF9C93E", "RECO Sample Region");
        regions.add(recoRegion);

        return regions;
    }

    protected abstract void start(ArrayList<RECOBeaconRegion> regions);
    protected abstract void stop(ArrayList<RECOBeaconRegion> regions);

}
