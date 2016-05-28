package com.mingle.myapplication.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.RelativeLayout;

import android.widget.ToggleButton;


import com.mingle.myapplication.DialogCall;
import com.mingle.myapplication.Parameter;
import com.mingle.myapplication.R;

import com.mingle.myapplication.model.SharedPreferenceUtil;
import com.mingle.myapplication.service.RECOBackgroundRangingService;

import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.perples.recosdk.RECOBeacon;
import java.util.ArrayList;

import com.mingle.myapplication.severcall.Servercall;
public class MainActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback{
    public static final String RECO_UUID = "24DDF411-8CF1-440C-87CD-E368DAF9C93E";
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int ACCESS_FINE_LOCATION_REQUEST_CODE = 1;
    // 필요한 권한들
    private static  String[] PERMISSIONS_CONTACT = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_PHONE_STATE};

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private ArrayList<RECOBeacon> mRangedBeacons;
    private SweetSheet mSweetSheet3;
    private RelativeLayout rl;
    ToggleButton bottomToggleButton;
    Button cinemaButton;
    Button libraryButton;
    Button exhibitButton;
    Toolbar toolbar;
    Toolbar bottombar;
    Handler handler;
    AudioManager audioManager;
    int selectBeaconMajor=0;
    int difResionNum=0;
    DialogCall dialogCall;
    Servercall servercall;

    Parameter parameter;
    SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "ISRESIONSET", 0);

        showDialog(); //닉네임 입력 팝업창 불러오기. UserNickname 입력후 SharedPreference에 저장.


        servercall=new Servercall();
        servercall.customizeset(getApplicationContext()); //서버에서 디폴트값 얻어오기 . SharedPreference에 값 저장.


        m_checkPermission();
        audioManager = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        Intent monitorService = new Intent(this, RECOBackgroundRangingService.class);
        startService(monitorService);
        cinemaButton=(Button)findViewById(R.id.cinema_h_icon);
        libraryButton=(Button)findViewById(R.id.library_h_icon);
        exhibitButton=(Button)findViewById(R.id.exhibition_h_icon);
        cinemaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cinema = new Intent(getApplicationContext(), ResionCinemaActivity.class);
                cinema.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(cinema);
            }
        });
        libraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent library = new Intent(getApplicationContext(), RegionLibraryActivity.class);
                library.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(library);

            }
        });
        exhibitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exhibition = new Intent(getApplicationContext(), ResionExhibitionActivity.class);
                exhibition.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(exhibition);

            }
        });
        rl = (RelativeLayout) findViewById(R.id.rl);
        setupCustomView();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(getLayoutInflater().inflate(R.layout.actionbar_layout, null),
                new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                )
        );
        bottombar = (Toolbar) findViewById(R.id.bottombar);
        setSupportActionBar(bottombar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(getLayoutInflater().inflate(R.layout.bottombar_layout, null),
                new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                )
        );
        bottomToggleButton = (ToggleButton) findViewById(R.id.bottomToggleButton);
        bottomToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomToggleButton.isChecked()) {
                    mSweetSheet3.show();
                } else {
                    mSweetSheet3.dismiss();
                }
            }
        });
        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "ResionMajor", 0);
    }
    public void showDialog(){
        dialogCall=new DialogCall();
        dialogCall.show(getFragmentManager(),"NickName");
        dialogCall.setCancelable(true);
    }
    private void m_checkPermission() {
        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();

        if(mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ) {
            //권한이 없을 경우

            //최초 인지, 재요청인지 확인
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE) ) {
                // 임의로 취소 시킨 경우 권한 재요청
                ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT,  ACCESS_FINE_LOCATION_REQUEST_CODE);
            } else {
                //최초로 권한을 요청하는 경우
                ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT, ACCESS_FINE_LOCATION_REQUEST_CODE);
            }
        } else {
            //사용 권한이 있음 확인
        }
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                updateThread();
            }
        };
        Thread myThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        handler.sendMessage(handler.obtainMessage());
                        Thread.sleep(1000);
                    } catch (Throwable t) {
                    }
                }
            }
        });

        myThread.start();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    private void updateThread() {
        //if(SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ISRESIONSET")==1) {
        if(selectBeaconMajor !=
                SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ResionMajor")) {
            difResionNum++;
            if(difResionNum == 3) {
                difResionNum = 0;
                selectBeaconMajor = SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ResionMajor");

                if (SharedPreferenceUtil.getSharedPreference(this, "ResionMajor") == 18243) { // 초록색
                    Intent intent = new Intent(getApplicationContext(), ResionCinemaActivity.class);
                    startActivity(intent);
                    //moveTaskToBack(SharedPreferenceUtil.isResionSet);

                    SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "ISRESIONSET", 0);
                    //Intent intent3 = new Intent();
                    //intent3.setAction(Intent.ACTION_MAIN);
                    //intent3.addCategory(Intent.CATEGORY_HOME);
                    //startActivity(intent3);
                }
                else if(SharedPreferenceUtil.getSharedPreference(this, "ResionMajor") == 18249) { // 노란색
                    audioManager.setRingerMode(
                            SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ExhibitionRingerMode")
                    );

                    Intent intent = new Intent(getApplicationContext(), ResionExhibitionActivity.class);
                    startActivity(intent);
                    //moveTaskToBack(SharedPreferenceUtil.isResionSet);

                    SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "ISRESIONSET", 0);
                    //Intent intent3 = new Intent();
                    //intent3.setAction(Intent.ACTION_MAIN);
                    //intent3.addCategory(Intent.CATEGORY_HOME);
                    //startActivity(intent3);
                }

                else {
                    Log.d("RESION: ", "알수없는 비콘");
                }
            }
        }


        //}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ACCESS_FINE_LOCATION_REQUEST_CODE: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                } else {
                   // permissions denied
                }
                return;
            }
        }
    }

    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    private void setupCustomView() {
        mSweetSheet3 = new SweetSheet(rl);
        CustomDelegate customDelegate = new CustomDelegate(true,
                CustomDelegate.AnimationType.AlphaAnimation);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_custom_view, null, false);
        customDelegate.setCustomView(view);
        mSweetSheet3.setDelegate(customDelegate);
        /*view.findViewById(R.id.intro_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();
            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (mSweetSheet3.isShow()) {
            mSweetSheet3.dismiss();
            bottomToggleButton.setChecked(false);
        } else {
            super.onBackPressed();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


}
