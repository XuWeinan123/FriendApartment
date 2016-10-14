package com.aaronxu.friendapartment.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aaronxu.friendapartment.R;

/**
 * Created by AaronXu on 2016-10-14.
 */
public class ViewPagerIndicator extends LinearLayout {

    private static final String TAG = "ViewPagerIndicator";
    ViewPager mViewPager;

    public ViewPagerIndicator(Context context) {
        this(context,null);
    }
    public ViewPagerIndicator(Context context, AttributeSet attrs){
        super(context,attrs);
        //初始化图片资源
        mViewPager = null;
        initChild();
    }

    private void initChild() {

    }

    public void setmViewPager(ViewPager mViewPager){
        this.mViewPager = mViewPager;
    }
    public void setItemClickEvent(){
        int cCount = getChildCount();
        for(int i = 0;i<cCount;i++){
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }
    public ImageView getChildView(int i){
        Log.d(TAG, "getChildView: "+getChildCount());
        return (ImageView) getChildAt(i);
    }
}
