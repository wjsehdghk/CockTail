package com.mingle.myapplication.model;

/**
 * Created by multimedia on 2016-05-22.
 */
public class ResionValue {
    int major;
    float accuracy;
    String resionName;

    public String getResionName() {
        return resionName;
    }

    public void setResionName(String resionName) {
        this.resionName = resionName;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }
}
