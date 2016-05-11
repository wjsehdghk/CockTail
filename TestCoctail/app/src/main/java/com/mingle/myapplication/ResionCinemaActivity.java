package com.mingle.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.mingle.sweetpick.ViewPagerDelegate;

import java.util.ArrayList;

public class ResionCinemaActivity extends AppCompatActivity {
        SeekBar seekBar;
        private SweetSheet mSweetSheet;
        private SweetSheet mSweetSheet2;
        private SweetSheet mSweetSheet3;
        private RelativeLayout rl;
        Toolbar toolbar;
        int now_bright_status;
        AudioManager audioManager;
        Button silent;
        Button virate;
        Button normal;
        Button homeButton;
        Button libraryButton;
        Button exhibitButton;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resion_cinema);
        homeButton=(Button)findViewById(R.id.h_icon);
        homeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent home = new Intent(getApplicationContext(), MainActivity.class);
                        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(home);
                        finish();
                }
        });

        libraryButton=(Button)findViewById(R.id.library_h_icon);
        libraryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent library=new Intent(getApplicationContext(),RegionLibraryActivity.class);
                        library.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(library);
                        finish();
                }
        });
        exhibitButton=(Button)findViewById(R.id.exhibition_h_icon);
        exhibitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent exhibition=new Intent(getApplicationContext(),ResionExhibitionActivity.class);
                        exhibition.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(exhibition);
                        finish();
                }
        });


        /*audioManager=(AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        silent=(Button)findViewById(R.id.silent);
        virate=(Button)findViewById(R.id.veriate);
        normal=(Button)findViewById(R.id.normal);
        silent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
        });
        virate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                }
        });
        normal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
        });
        try{

               now_bright_status = Settings.System.getInt(getContentResolver(),

                   Settings.System.SCREEN_BRIGHTNESS);


        }catch(Exception e){

                Log.e("Exception e " + e.getMessage(), null);

        }

        seekBar=(SeekBar)findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if(progress<10){
                                progress=10;
                                seekBar.setProgress(progress);
                        }
                        WindowManager.LayoutParams params=getWindow().getAttributes();
                        params.screenBrightness=(float)progress/100;
                        params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                        getWindow().setAttributes(params);
                        Settings.System.putInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,progress);

                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
        });}*/
        rl = (RelativeLayout) findViewById(R.id.rl);
        setupViewpager();
        setupRecyclerView();
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

        final String[] colors = {"#5a5b55", "#5a5b55", "#5a5b55"};


        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        final AHBottomNavigationItem item1 = new AHBottomNavigationItem("left", R.drawable.ic_arrow_left, Color.parseColor(colors[0]));
        final AHBottomNavigationItem item2 = new AHBottomNavigationItem("setting", R.drawable.ic_arrow_up, Color.parseColor(colors[1]));
        final AHBottomNavigationItem item3 = new AHBottomNavigationItem("right", R.drawable.ic_arrow_right, Color.parseColor(colors[2]));


        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

        //  Enables Reveal effect
        bottomNavigation.setColored(true);

        bottomNavigation.setCurrentItem(0);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
                @Override
                public void onTabSelected(int position, boolean wasSelected) {
                        // Do something cool here...

                        //fragment.updateColor(Color.parseColor(colors[position]));
                        if (position == 1) {
                                if (mSweetSheet2.isShow()) {
                                        mSweetSheet2.dismiss();
                                }
                                if (mSweetSheet3.isShow()) {
                                        mSweetSheet3.dismiss();
                                }
                                mSweetSheet.toggle();
                        }
                        if (position == 2) {
                                if (mSweetSheet.isShow()) {
                                        mSweetSheet.dismiss();
                                }
                                if (mSweetSheet3.isShow()) {
                                        mSweetSheet3.dismiss();
                                }
                                mSweetSheet2.toggle();
                        }
                        if (position == 0) {
                                if (mSweetSheet.isShow()) {
                                        mSweetSheet.dismiss();
                                }
                                if (mSweetSheet2.isShow()) {
                                        mSweetSheet2.dismiss();
                                }
                                mSweetSheet3.toggle();
                        }
                }
        });


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
                        CustomDelegate.AnimationType.DuangLayoutAnimation);
                View view = LayoutInflater.from(this).inflate(R.layout.layout_custom_view, null, false);
                customDelegate.setCustomView(view);
                mSweetSheet3.setDelegate(customDelegate);
                view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                mSweetSheet3.dismiss();
                        }
                });
        }

        private void setupRecyclerView() {

                final ArrayList<MenuEntity> list = new ArrayList<>();
                //添加假数据
                MenuEntity menuEntity1 = new MenuEntity();
                menuEntity1.iconId = R.drawable.ic_account_child;
                menuEntity1.titleColor = 0xff96CC7A; //textcolor
                menuEntity1.title = "code";



                MenuEntity menuEntity = new MenuEntity();
                menuEntity.iconId = R.drawable.ic_account_child;
                menuEntity.titleColor = 0xffb3b3b3;
                menuEntity.title = "QQ";
                list.add(menuEntity1);
                list.add(menuEntity);
                list.add(menuEntity);
                list.add(menuEntity);
                list.add(menuEntity);
                list.add(menuEntity);
                list.add(menuEntity);
                list.add(menuEntity);
                list.add(menuEntity);
                list.add(menuEntity);
                list.add(menuEntity);
                list.add(menuEntity);
                list.add(menuEntity);

                // SweetSheet 控件,根据 rl 确认位置
                mSweetSheet = new SweetSheet(rl);

                //设置数据源 (数据源支持设置 list 数组,也支持从菜单中获取)
                mSweetSheet.setMenuList(list);
                //根据设置不同的 Delegate 来显示不同的风格.
                mSweetSheet.setDelegate(new RecyclerViewDelegate(true));
                //根据设置不同Effect 来显示背景效果BlurEffect:模糊效果.DimEffect 变暗效果
                mSweetSheet.setBackgroundEffect(new BlurEffect(8));
                //设置点击事件
                mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
                        @Override
                        public boolean onItemClick(int position, MenuEntity menuEntity1) {
                                //即时改变当前项的颜色
                                list.get(position).titleColor = 0xff96CC7A;
                                ((RecyclerViewDelegate) mSweetSheet.getDelegate()).notifyDataSetChanged();

                                //根据返回值, true 会关闭 SweetSheet ,false 则不会.
                                //Toast.makeText(MainActivity.this, menuEntity1.title + "  " + position, Toast.LENGTH_SHORT).show();
                                return false;
                        }
                });

        }

        private void setupViewpager() {


                mSweetSheet2 = new SweetSheet(rl);

                //从menu 中设置数据源
                mSweetSheet2.setMenuList(R.menu.menu_sweet);
                mSweetSheet2.setDelegate(new ViewPagerDelegate());
                mSweetSheet2.setBackgroundEffect(new DimEffect(0.5f));
                mSweetSheet2.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
                        @Override
                        public boolean onItemClick(int position, MenuEntity menuEntity1) {

                              //  Toast.makeText(MainActivity.this, menuEntity1.title + "  " + position, Toast.LENGTH_SHORT).show();
                                return true;
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

                if (mSweetSheet.isShow() || mSweetSheet2.isShow()) {
                        if (mSweetSheet.isShow()) {
                                mSweetSheet.dismiss();
                        }
                        if (mSweetSheet2.isShow()) {
                                mSweetSheet2.dismiss();
                        }
                } else {
                        super.onBackPressed();
                }


        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

        /*
        int id = item.getItemId();
        if (id == R.id.action_recyclerView) {
            if (mSweetSheet2.isShow()) {
                mSweetSheet2.dismiss();
            }
            if (mSweetSheet3.isShow()) {
                mSweetSheet3.dismiss();
            }
            mSweetSheet.toggle();

            return true;
        }
        if (id == R.id.action_viewpager) {
            if (mSweetSheet.isShow()) {
                mSweetSheet.dismiss();
            }
            if (mSweetSheet3.isShow()) {
                mSweetSheet3.dismiss();
            }
            mSweetSheet2.toggle();
            return true;
        }
        if (id == R.id.action_custom) {
            if (mSweetSheet.isShow()) {
                mSweetSheet.dismiss();
            }
            if (mSweetSheet2.isShow()) {
                mSweetSheet2.dismiss();
            }
            mSweetSheet3.toggle();
            return true;
        }
        */
                return super.onOptionsItemSelected(item);
        }
}



