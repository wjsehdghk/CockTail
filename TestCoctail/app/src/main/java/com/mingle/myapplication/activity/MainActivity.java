package com.mingle.myapplication.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
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
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.ListView;
import android.widget.RelativeLayout;

import android.widget.Toast;
import android.widget.ToggleButton;


import com.mingle.myapplication.DialogCall;
import com.mingle.myapplication.R;

import com.mingle.myapplication.adapter.MainListAdapter;
import com.mingle.myapplication.model.Resion;
import com.mingle.myapplication.model.SharedPreferenceUtil;
import com.mingle.myapplication.service.RECOBackgroundRangingService;

import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.SweetSheet;

import java.util.ArrayList;
import java.util.List;

import com.mingle.myapplication.severcall.Servercall;
public class MainActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback{
    public static final String RECO_UUID = "24DDF411-8CF1-440C-87CD-E368DAF9C93E";
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int ACCESS_FINE_LOCATION_REQUEST_CODE = 1;
    private static final int PERMISSIONS_REQ_NUM = 100;
    // 필요한 권한들
    private static  String[] PERMISSIONS_CONTACT = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_PHONE_STATE};

    private boolean isExitResion = false; // 장소에서 나왔다면 메인 실행

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;

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

    List<Resion> list;
    MainListAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        showDialog(); //닉네임 입력 팝업창 불러오기. UserNickname 입력후 SharedPreference에 저장.

        servercall=new Servercall();
        servercall.customizeset(getApplicationContext()); //서버에서 디폴트값 얻어오기 . SharedPreference에 값 저장

        audioManager = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);


        m_checkPermission();

        /*

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

        */
        list = new ArrayList<Resion>();
        adapter = new MainListAdapter(this, R.layout.layout_main_list, list);
        listView = (ListView) findViewById(R.id.mainListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent cinema = new Intent(getApplicationContext(), ResionCinemaActivity.class);
                        cinema.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(cinema);
                        break;
                    case 1:
                        Intent library = new Intent(getApplicationContext(), RegionLibraryActivity.class);
                        library.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(library);
                        break;
                    case 2:
                        Intent exhibition = new Intent(getApplicationContext(), ResionExhibitionActivity.class);
                        exhibition.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(exhibition);
                        break;
                    default:break;
                }
            }
        });
        addData();


        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "ISRESIONSET", 0);
        Intent monitorService = new Intent(this, RECOBackgroundRangingService.class);
        startService(monitorService);
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

        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CallServiceFrag", 0); // 다른 지역에서 callservice 사용 안함
    }
    public void addData() {
        Bitmap bitmapCin = BitmapFactory.decodeResource(getResources(), R.drawable.cinema);
        Bitmap bitmapLib = BitmapFactory.decodeResource(getResources(), R.drawable.library);
        Bitmap bitmapExh = BitmapFactory.decodeResource(getResources(), R.drawable.exhibition);
        Bitmap blurCin=blur(bitmapCin);
        Bitmap blurLib=blur(bitmapLib);
        Bitmap blurExh=blur(bitmapExh);
        BitmapDrawable blurDrawCin=new BitmapDrawable(getResources(), blurCin);
        BitmapDrawable blurDrawLib=new BitmapDrawable(getResources(), blurLib);
        BitmapDrawable blurDrawExh=new BitmapDrawable(getResources(), blurExh);
        adapter.add(new Resion(R.mipmap.ic_cinema, blurDrawCin, "영화관"));
        adapter.add(new Resion(R.mipmap.ic_library, blurDrawLib, "도서관"));
        adapter.add(new Resion(R.mipmap.ic_exhibition, blurDrawExh, "전시장"));
    }

    public Bitmap blur(Bitmap image) {
        if(null == image) return null;
        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(this);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(8f);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }

    public void showDialog(){
        dialogCall=new DialogCall();
        dialogCall.show(getFragmentManager(), "NickName");
        dialogCall.setCancelable(true);
    }

    @TargetApi(23)
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
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            //권한이 없을 경우

            //최초 인지, 재요청인지 확인
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                // 임의로 취소 시킨 경우 권한 재요청
                ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT,  ACCESS_FINE_LOCATION_REQUEST_CODE);
            } else {
                //최초로 권한을 요청하는 경우
                ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT, ACCESS_FINE_LOCATION_REQUEST_CODE);
            }
        } else {
            //사용 권한이 있음 확인
        }

        try {
            //Settings.System.canWrite(this);
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, PERMISSIONS_REQ_NUM);

        } catch (Exception e) {
            e.printStackTrace();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PERMISSIONS_REQ_NUM:
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(this)) {
                        //권한 승인이 이루어 지지 않음
                    }
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    private void updateThread() {
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
                    isExitResion=true;
                }
                else if(SharedPreferenceUtil.getSharedPreference(this, "ResionMajor") == 18249) { // 노란색
                    audioManager.setRingerMode(
                            SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ExhibitionRingerMode"));

                    Intent intent = new Intent(getApplicationContext(), ResionExhibitionActivity.class);
                    startActivity(intent);
                    //moveTaskToBack(SharedPreferenceUtil.isResionSet);

                    SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "ISRESIONSET", 0);
                    //Intent intent3 = new Intent();
                    //intent3.setAction(Intent.ACTION_MAIN);
                    //intent3.addCategory(Intent.CATEGORY_HOME);
                    //startActivity(intent3);
                    isExitResion=true;
                }
                else if(SharedPreferenceUtil.getSharedPreference(this, "ResionMajor") == 0) {
                    if(isExitResion) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        isExitResion=false;
                    }
                }

                else {
                    Log.d("RESION: ", "알수없는 비콘");
                }
            }
        }
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
        Toast.makeText(this, "MainResume", Toast.LENGTH_SHORT);
        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CallServiceFrag", 0);
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
