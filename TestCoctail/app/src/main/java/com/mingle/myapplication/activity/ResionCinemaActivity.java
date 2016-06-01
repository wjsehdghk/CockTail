package com.mingle.myapplication.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.mingle.myapplication.R;
import com.mingle.myapplication.TriToggleButton;
import com.mingle.myapplication.model.SharedPreferenceUtil;
import com.mingle.myapplication.service.CallService;
import com.mingle.myapplication.severcall.Servercall;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.SweetSheet;

public class ResionCinemaActivity extends AppCompatActivity {

    private SweetSheet mSweetSheet3;
    private RelativeLayout rl;

    Toolbar toolbar;
    Toolbar bottombar;
    Button homeButton;
    Button exhibitButton;
    Button libraryButton;
    ToggleButton bottomToggleButton;
    ImageView cinema_back;
    ImageView cinema_icon;
    ImageView cinema_edge;
    Bitmap bitmap;
    Bitmap bitmap2;
    Bitmap bitmap3;
    SeekBar seekBar;
    Switch callServiceSwitchBtn;
    Switch messgeSwitchBtn;
    Switch wifiSwitchBtn;
    AudioManager audioManager;
    ComponentName mCallService;
    WifiManager mWifiManager;

    String cinema;
    Servercall servercall;

    SeekBar ringSeekBar;            //벨 소리 조절
    SeekBar mediaSeekBar;           //미디어 소리 조절
    SeekBar alertSeekBar;           //알람 소리 조절
    SeekBar sysSeekBar;             //시스템 소리 조절

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resion_cinema);

        servercall = new Servercall();
        cinema = "cinema";
        servercall.postResioninfo(getApplicationContext(), cinema);


        final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        audioManager = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cinema);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.cinema_edge);
        bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.cinema_icon);

        cinema_back = (ImageView) findViewById(R.id.cinema_back);
        cinema_edge = (ImageView) findViewById(R.id.cinema_edge);
        cinema_icon = (ImageView) findViewById(R.id.cinema_icon);

        cinema_back.setImageBitmap(bitmap);
        cinema_edge.setImageBitmap(bitmap2);

        if (SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ResionMajor") == 18243) {
            cinema_edge.setAnimation(animRotate);
        }
        cinema_icon.setImageBitmap(bitmap3);

        homeButton = (Button) findViewById(R.id.home_btn);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
                finish();
            }
        });

        libraryButton = (Button) findViewById(R.id.library_btn);
        libraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent library = new Intent(getApplicationContext(), RegionLibraryActivity.class);
                library.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(library);
                finish();
            }
        });
        exhibitButton = (Button) findViewById(R.id.exhibition_btn);
        exhibitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exhibition = new Intent(getApplicationContext(), ResionExhibitionActivity.class);
                exhibition.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(exhibition);
                finish();
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

        Settings.System.putInt(getContentResolver(), "screen_brightness",
                SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaBrightness"));
        audioManager.setRingerMode(
                SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaRingerMode"));

        Log.d("SharedPreferenceUtil 1", "Resion Cinema: " + SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaBrightness"));
        Log.d("SharedPreferenceUtil 1", "Resion Cinema: " + SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaRingerMode"));

        servercall.usersettinginfo(getApplicationContext(),
                SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "UserNickname", ""),
                cinema,
                SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaBrightness"),
                SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaRingerMode"),
                SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaChecked")
        );

        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CallServiceFrag", 1);
        mCallService = startService(new Intent(this, CallService.class));
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bitmap.recycle();
        bitmap2.recycle();
        bitmap3.recycle();
        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CallServiceFrag", 0); // 다른 지역에서 callservice 사용 안함
    }

    private void setupCustomView() {
        mSweetSheet3 = new SweetSheet(rl);
        CustomDelegate customDelegate = new CustomDelegate(true,
                CustomDelegate.AnimationType.AlphaAnimation);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_custom_view, null, false);
        customDelegate.setCustomView(view);
        customDelegate.setSweetSheetColor(getResources().getColor(R.color.colorBottomtab));
        mSweetSheet3.setDelegate(customDelegate);
        mSweetSheet3.setBackgroundEffect(new BlurEffect(8));
        mSweetSheet3.setBackgroundClickEnable(false);

        ringSeekBar = (SeekBar) view.findViewById(R.id.ringSeekBar);
        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiSwitchBtn = (Switch) view.findViewById(R.id.switch3);

        wifiSwitchBtn.setChecked(mWifiManager.isWifiEnabled());

        view.findViewById(R.id.triToggleButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (TriToggleButton.getState()) {
                    case 0:
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CinemaRingerMode", AudioManager.RINGER_MODE_SILENT);
                        break;
                    case 1:
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CinemaRingerMode", AudioManager.RINGER_MODE_VIBRATE);
                        break;
                    case 2:
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CinemaRingerMode", AudioManager.RINGER_MODE_NORMAL);
                        break;
                    default:
                        break;
                }
            }
        });

        seekBar = (SeekBar) view.findViewById(R.id.custom_seek);
        seekBar.setMax(255);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < 10) {
                    progress = 10;
                    seekBar.setProgress(progress);
                }
                Settings.System.putInt(getContentResolver(), "screen_brightness", progress);
                SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CinemaBrightness", progress);
                Log.d("SharedPreferenceUtil 3", "Resion Cinema: " + SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaBrightness"));
                Log.d("SharedPreferenceUtil 3", "Resion Cinema: " + SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaRingerMode"));

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        seekBar.setProgress(
                SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaBrightness") * 100 / 255);
        Log.d("SharedPreferenceUtil 2", "Resion Cinema: " + SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaBrightness"));
        Log.d("SharedPreferenceUtil 2", "Resion Cinema: " + SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaRingerMode"));
        callServiceSwitchBtn = (Switch) view.findViewById(R.id.switch1);
        messgeSwitchBtn = (Switch) view.findViewById(R.id.switch2);
        if (SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaChecked") == 1)
            callServiceSwitchBtn.setChecked(true);
        else callServiceSwitchBtn.setChecked(false);

        callServiceSwitchBtn.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    initiateService();
                    SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CinemaChecked", 1);
                    messgeSwitchBtn.setEnabled(true);

                } else {
                    terminateService();
                    SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CinemaChecked", 0);
                    messgeSwitchBtn.setEnabled(false);
                }

            }
        });
        messgeSwitchBtn.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "MessageChecked", 1);
                } else {
                    SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "MessageChecked", 0);
                }
            }
        });

        wifiSwitchBtn.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mWifiManager.setWifiEnabled(true);
                } else {
                    mWifiManager.setWifiEnabled(false);
                }
            }
        });


        mediaSeekBar = (SeekBar) view.findViewById(R.id.mediaSeekBar);
        alertSeekBar = (SeekBar) view.findViewById(R.id.alertSeekBar);
        sysSeekBar = (SeekBar) view.findViewById(R.id.sysSeekBar);

        final AudioManager audioManager3 = (AudioManager) getSystemService(AUDIO_SERVICE);
        int mMax = audioManager3.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
        int mCurrentRing = audioManager3.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        alertSeekBar.setMax(mMax);
        alertSeekBar.setProgress(mCurrentRing);
        alertSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager3.setStreamVolume(AudioManager.STREAM_NOTIFICATION, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

        });

        final AudioManager audioManager4 = (AudioManager) getSystemService(AUDIO_SERVICE);
        mMax = audioManager4.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        mCurrentRing = audioManager4.getStreamVolume(AudioManager.STREAM_SYSTEM);
        sysSeekBar.setMax(mMax);
        sysSeekBar.setProgress(mCurrentRing);
        sysSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager4.setStreamVolume(AudioManager.STREAM_SYSTEM, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

        });


        final AudioManager audioManager1 = (AudioManager) getSystemService(AUDIO_SERVICE);
        mMax = audioManager1.getStreamMaxVolume(AudioManager.STREAM_RING);
        mCurrentRing = audioManager1.getStreamVolume(AudioManager.STREAM_RING);

        ringSeekBar.setMax(mMax);
        ringSeekBar.setProgress(mCurrentRing);
        ringSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager1.setStreamVolume(AudioManager.STREAM_RING, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        final AudioManager audioManager2 = (AudioManager) getSystemService(AUDIO_SERVICE);
        mMax = audioManager2.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mCurrentRing = audioManager2.getStreamVolume(AudioManager.STREAM_MUSIC);
        mediaSeekBar.setMax(mMax);
        mediaSeekBar.setProgress(mCurrentRing);
        mediaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager2.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void initiateService() {
        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CallServiceFrag", 1);
        mCallService = startService(new Intent(this, CallService.class));
    }

    public void terminateService() {
        if (mCallService == null) {
            return;
        }
        Intent i = new Intent();
        i.setComponent(mCallService);
        stopService(i);
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
        } else {
            super.onBackPressed();
        }
        bottomToggleButton.setChecked(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}