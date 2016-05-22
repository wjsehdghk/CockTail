package com.mingle.myapplication;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.mingle.myapplication.service.CallService;
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
        AudioManager audioManager;
        Intent callService;
        int callFrag=0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_resion_cinema);

                final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
                audioManager = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);

                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cinema);
                bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.cinema_edge);
                bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.cinema_icon);

                cinema_back = (ImageView) findViewById(R.id.cinema_back);
                cinema_edge = (ImageView) findViewById(R.id.cinema_edge);
                cinema_icon = (ImageView)findViewById(R.id.cinema_icon);

                cinema_back.setImageBitmap(bitmap);
                cinema_edge.setImageBitmap(bitmap2);
                cinema_edge.setAnimation(animRotate);
                cinema_icon.setImageBitmap(bitmap3);

                homeButton=(Button)findViewById(R.id.home_btn);
                homeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                                home.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(home);
                                finish();
                        }
                });


                libraryButton=(Button)findViewById(R.id.library_btn);
                libraryButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent library=new Intent(getApplicationContext(),RegionLibraryActivity.class);
                                library.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(library);
                                finish();
                        }
                });
                exhibitButton=(Button)findViewById(R.id.exhibition_btn);
                exhibitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent exhibition = new Intent(getApplicationContext(), ResionExhibitionActivity.class);
                                exhibition.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
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

                startService(new Intent("com.mingle.myapplication.service"));

                callFrag=1;

        }
        protected void onNewIntent(Intent intent){
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
                stopService(new Intent("com.mingle.myapplication.service"));
                callFrag=0;
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
                view.findViewById(R.id.triToggleButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                switch (TriToggleButton.getState()) {
                                        case 0:
                                                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                                break;
                                        case 1:
                                                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                                                break;
                                        case 2:
                                                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                                break;
                                        default:
                                                break;
                                }
                        }
                });

                seekBar =(SeekBar)view.findViewById(R.id.custom_seek);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                if (progress < 10) {
                                        progress = 10;
                                        seekBar.setProgress(progress);
                                }

                                WindowManager.LayoutParams params = getWindow().getAttributes();
                                params.screenBrightness = (float) progress / 100;
                                getWindow().setAttributes(params);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                });

                callServiceSwitchBtn = (Switch)view.findViewById(R.id.switch1);
                callServiceSwitchBtn.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if(isChecked) {
                                        if(callFrag==0) startService(new Intent("com.mingle.myapplication.service"));
                                        Toast.makeText(getApplicationContext(),
                                                "전화 차단 후 메시지 전송기능 사용", Toast.LENGTH_SHORT).show();
                                        callFrag=1;

                                }
                                else {
                                        stopService(new Intent("com.mingle.myapplication.service"));
                                        Toast.makeText(getApplicationContext(),
                                                "전화 차단 후 메시지 전송기능 사용 안함", Toast.LENGTH_SHORT).show();
                                        callFrag=0;
                                }

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



