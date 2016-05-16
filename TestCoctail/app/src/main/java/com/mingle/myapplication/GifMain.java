package com.mingle.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import java.io.InputStream;
/**
 * Created by multimedia on 2016-05-11.
 */
public class GifMain extends View {
    private InputStream gifInputStream;
    private Movie gifmovie;
    private int movieWidth,movieHeight;
    private long movieDuration;
    private long movieStart;


    public GifMain(Context context) {
        super(context);
        init(context);
    }

    public GifMain(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GifMain(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        setFocusable(true);
        gifInputStream=context.getResources().openRawResource(R.drawable.cock);
        gifmovie=Movie.decodeStream(gifInputStream);
        movieWidth=gifmovie.width();
        movieHeight=gifmovie.height();
        movieDuration=gifmovie.duration();

    }
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        setMeasuredDimension(movieWidth,movieHeight);
    }
    public int getMovieWidth(){
        return movieWidth;
    }
    public int getMovieHeight(){
        return movieHeight;
    }
    public long getMovieDuration(){
        return movieDuration;
    }

    protected void onDraw(Canvas canvas){
        long now= SystemClock.uptimeMillis();
        if(movieStart == 0){
            movieStart=now;
        }
        if(gifmovie!= null){
            int dur=gifmovie.duration();
            if(dur ==0){
                dur= 8000;
            }
            int realTime=(int)((now-movieStart) %dur);
            gifmovie.setTime(realTime);
            gifmovie.draw(canvas,0,0);
            invalidate();

        }
    }



}

