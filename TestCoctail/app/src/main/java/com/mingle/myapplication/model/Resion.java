package com.mingle.myapplication.model;

import android.graphics.drawable.Drawable;

/**
 * Created by h on 2016-05-30.
 */
public class Resion {
    private int image1;
    private Drawable image2;
    private String resion;

    public Resion (int image1, Drawable image2, String resion) {
        this.image1 = image1;
        this.image2 = image2;
        this.resion = resion;
    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public Drawable getImage2() {
        return image2;
    }

    public void setImage2(Drawable image2) {
        this.image2 = image2;
    }

    public String getResion() {
        return resion;
    }

    public void setResion(String resion) {
        this.resion = resion;
    }
}
