package com.mingle.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.mingle.sweetpick.ViewPagerDelegate;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import com.mingle.myapplication.DialogCall;

public class ResionExhibitionActivity extends AppCompatActivity {
    private SweetSheet mSweetSheet;
    private SweetSheet mSweetSheet2;
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

    public static int state=-1;

    SeekBar seekBar;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    Handler handler;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resion_exhibition);

        initdialog();

        handler = new Handler();
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();



        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://img.etoday.co.kr/pto_db/2015/07/20150722114910_680826_600_800.jpg");
                    //InputStream is = url.openStream();
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.connect();
                    int imagesize=conn.getContentLength();
                    BufferedInputStream is=new BufferedInputStream(conn.getInputStream(),imagesize);
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    is.close();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("image", "called");
                            dialog.show();
                        }
                    });
                    //imageView.setImageBitmap(bm);
                    //Log.d("image2", "called2");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //t.start();
        audioManager = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        //final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.exhibition);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.exhibition_edge);
        bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.exhibition_icon);
        exhibition_back = (ImageView) findViewById(R.id.exhibition_back);
        exhibition_edge = (ImageView) findViewById(R.id.exhibition_edge);
        exhibition_icon = (ImageView)findViewById(R.id.exhibition_icon);
        exhibition_back.setImageBitmap(bitmap);
        exhibition_edge.setImageBitmap(bitmap2);
        exhibition_edge.setAnimation(animRotate);
        exhibition_icon.setImageBitmap(bitmap3);
        homeButton = (Button) findViewById(R.id.home_btn);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                home.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(home);
                finish();
            }
        });
        libraryButton = (Button) findViewById(R.id.library_btn);
        libraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent library = new Intent(getApplicationContext(), RegionLibraryActivity.class);
                library.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(library);
                finish();
            }
        });
        cinemaButton = (Button) findViewById(R.id.cinema_btn);
        cinemaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cinema = new Intent(getApplicationContext(), ResionCinemaActivity.class);
                cinema.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(cinema);
                finish();
            }
        });


        rl = (RelativeLayout) findViewById(R.id.rl);

        //setupViewpager();
        //setupRecyclerView();
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

    public void initdialog(){

        LayoutInflater inflater= (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview=inflater.inflate(R.layout.dialog,null);
        imageView=(ImageView)findViewById(R.id.dialogimage);
        builder=new AlertDialog.Builder(this);
        builder.setTitle("메시지");
        builder.setView(dialogview);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("Move", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

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
                switch (state) {
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
                if(progress<10){
                    progress=10;
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

                //   Toast.makeText(MainActivity.this, menuEntity1.title + "  " + position, Toast.LENGTH_SHORT).show();
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
