package com.aaronxu.friendapartment;

import android.graphics.Bitmap;

/**
 * Created by woshi on 2016-10-15.
 */
public class CardBean {
    private Bitmap mBitmap;
    private String mName;
    private int mPercent;

    public CardBean(){
        this.mBitmap = null;
        mName = "空";
        mPercent = 88;
    }
    public CardBean(Bitmap mBitmap, String mName, int mPercent) {
        this.mBitmap = mBitmap;
        this.mName = mName;
        this.mPercent = mPercent;
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmPercent() {
        return mPercent;
    }

    public void setmPercent(int mPercent) {
        this.mPercent = mPercent;
    }
}
