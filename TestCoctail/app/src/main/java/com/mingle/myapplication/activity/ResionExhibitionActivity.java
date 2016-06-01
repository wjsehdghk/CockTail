package com.mingle.myapplication.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mingle.myapplication.R;
import com.mingle.myapplication.TriToggleButton;
import com.mingle.myapplication.model.SharedPreferenceUtil;
import com.mingle.myapplication.severcall.Servercall;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.SweetSheet;

import java.io.InputStream;
import java.net.URL;

public class ResionExhibitionActivity extends AppCompatActivity {
    private SweetSheet mSweetSheet3;
    private RelativeLayout rl;

    Toolbar toolbar;
    Toolbar bottombar;
    Button homeButton;
    Button cinemaButton;
    Button libraryButton;
    ToggleButton bottomToggleButton;
    ImageView exhibition_back;
    ImageView exhibition_icon;
    ImageView exhibition_edge;
    Bitmap bitmap;
    Bitmap bitmap2;
    Bitmap bitmap3;
    AudioManager audioManager;
    public static int state = -1;
    SeekBar seekBar;
    Handler handler;
    private AlertDialog mDialog = null;
    Servercall servercall;
    String exhibition;

    WifiManager wifiManager;
    Switch popupSwitch;
    Switch wifiSwitch;

    SeekBar ringSeekBar;            //벨 소리 조절
    SeekBar mediaSeekBar;           //미디어 소리 조절
    SeekBar alertSeekBar;           //알람 소리 조절
    SeekBar sysSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resion_exhibition);

        if(SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ExhibitionPopup") == 1) {
            initDialog();//전시장에 관한 팝업창이 나오는 함수
        }

        servercall = new Servercall();
        exhibition = "exhibition";
        servercall.postResioninfo(getApplicationContext(), exhibition);//전시장 들어갔을때 서버에 Count증가 시킴.

        handler = new Handler();

        audioManager = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);

        final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.exhibition);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.exhibition_edge);
        bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.exhibition_icon);

        exhibition_back = (ImageView) findViewById(R.id.exhibition_back);
        exhibition_edge = (ImageView) findViewById(R.id.exhibition_edge);
        exhibition_icon = (ImageView) findViewById(R.id.exhibition_icon);
        exhibition_back.setImageBitmap(bitmap);
        exhibition_edge.setImageBitmap(bitmap2);

        if(SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ResionMajor") == 18249) {
            exhibition_edge.setAnimation(animRotate);
        }
        exhibition_icon.setImageBitmap(bitmap3);

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

        cinemaButton = (Button) findViewById(R.id.cinema_btn);
        cinemaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cinema = new Intent(getApplicationContext(), ResionCinemaActivity.class);
                cinema.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(cinema);
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
                SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ExhibitionBrightness"));
        audioManager.setRingerMode(
                SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ExhibitionRingerMode"));

        servercall.usersettinginfo(getApplicationContext(),
                SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "UserNickname", ""),
                exhibition,
                SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ExhibitionBrightness"),
                SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ExhibitionRingerMode"),
                SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ExhibitionPopup")
        );

        Log.d("SharedPreferenceUtil 1", "Resion Exhibition: " + SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ExhibitionBrightness"));
        Log.d("SharedPreferenceUtil 1", "Resion Exhibition: " + SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ExhibitionRingerMode"));

        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CallServiceFrag", 0); // 다른 지역에서 callservice 사용 안함
    }

    public void initDialog() {
        final Handler handler = new Handler();
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_layout, null);

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("전시장");
        ab.setCancelable(false);
        ab.setView(dialogView);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final ImageView iv = (ImageView) dialogView.findViewById(R.id.webImage);
                    URL url = new URL("https://scontent.xx.fbcdn.net/t31.0-8/13248551_1711638399105324_4905597678629514614_o.jpg");
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            iv.setImageBitmap(bm);
                        }
                    }, 30);
                    iv.setImageBitmap(bm);
                } catch (Exception e) {

                }
            }
        });
        t.start();
        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                setDismiss(mDialog);
            }
        });
        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                setDismiss(mDialog);
            }
        });
        ab.show();
    }
    private void setDismiss(Dialog dialog) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
        bottomToggleButton.setChecked(false);
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
    }
    private void setupCustomView() {
        mSweetSheet3 = new SweetSheet(rl);
        CustomDelegate customDelegate = new CustomDelegate(true,
                CustomDelegate.AnimationType.AlphaAnimation);

        View view = LayoutInflater.from(this).inflate(R.layout.layout_customview_exh, null, false);

        customDelegate.setCustomView(view);
        customDelegate.setSweetSheetColor(getResources().getColor(R.color.colorBottomtab));
        mSweetSheet3.setDelegate(customDelegate);
        mSweetSheet3.setBackgroundEffect(new BlurEffect(8));
        mSweetSheet3.setBackgroundClickEnable(false);
        view.findViewById(R.id.triToggleButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (TriToggleButton.getState()) {
                    case 0:
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "ExhibitionRingerMode", AudioManager.RINGER_MODE_SILENT);
                        break;
                    case 1:
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "ExhibitionRingerMode", AudioManager.RINGER_MODE_VIBRATE);
                        break;
                    case 2:
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "ExhibitionRingerMode", AudioManager.RINGER_MODE_NORMAL);
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
                SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "ExhibitionBrightness", progress);
                Log.d("SharedPreferenceUtil 3", "Resion Exhibition: " + SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ExhibitionBrightness"));
                Log.d("SharedPreferenceUtil 3", "Resion Exhibition: " + SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ExhibitionRingerMode"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        seekBar.setProgress(
                SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ExhibitionBrightness"));
        Log.d("SharedPreferenceUtil 2", "Resion Exhibition: " + SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ExhibitionBrightness"));
        Log.d("SharedPreferenceUtil 2", "Resion Exhibition: " + SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ExhibitionRingerMode"));


        popupSwitch = (Switch) view.findViewById(R.id.popupSwitch);
        if(SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ExhibitionPopup")==1) popupSwitch.setChecked(true);
        else popupSwitch.setChecked(false);
        popupSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "ExhibitionPopup", 0);
                }
                else {
                    SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "ExhibitionPopup", 1);
                }
            }
        });

        wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        wifiSwitch = (Switch)view.findViewById(R.id.wifiSwitch);
        wifiSwitch.setChecked(wifiManager.isWifiEnabled());
        wifiSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wifiManager.setWifiEnabled(true);
                    SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "ExhibitionPopup", 1);
                } else {
                    wifiManager.setWifiEnabled(false);
                    SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "ExhibitionPopup", 0);
                }
            }
        });

        ringSeekBar = (SeekBar)view.findViewById(R.id.ringSeekBar);
        mediaSeekBar = (SeekBar)view.findViewById(R.id.mediaSeekBar);
        alertSeekBar = (SeekBar)view.findViewById(R.id.alertSeekBar);
        sysSeekBar = (SeekBar)view.findViewById(R.id.sysSeekBar);

        final AudioManager audioManager3 = (AudioManager)getSystemService(AUDIO_SERVICE);
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

        final AudioManager audioManager4 = (AudioManager)getSystemService(AUDIO_SERVICE);
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

        final AudioManager audioManager1 = (AudioManager)getSystemService(AUDIO_SERVICE);
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

        final AudioManager audioManager2 = (AudioManager)getSystemService(AUDIO_SERVICE);
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
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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