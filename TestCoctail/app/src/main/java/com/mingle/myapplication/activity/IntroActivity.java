package com.mingle.myapplication.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingle.myapplication.R;


public class IntroActivity extends AppCompatActivity {

    ImageView imageView1;
    ImageView imageView2;
    TextView textView1;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_transle);
        final Animation animRotate2 = AnimationUtils.loadAnimation(this, R.anim.anim_transle2);
        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);

        imageView1 = (ImageView)findViewById(R.id.imageView2);
        imageView1.setAnimation(animRotate);
        textView1 = (TextView) findViewById(R.id.textView2);
        textView1.setAnimation(animRotate);

        imageView2 = (ImageView)findViewById(R.id.imageView3);
        imageView2.setAnimation(animRotate2);
        textView2 = (TextView) findViewById(R.id.textView3);
        textView2.setAnimation(animRotate2);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
                //overridePendingTransition(R.anim.anim_alpha, R.anim.anim_alpha2);
                finish();
            }
        }, 2000);


    }
}
