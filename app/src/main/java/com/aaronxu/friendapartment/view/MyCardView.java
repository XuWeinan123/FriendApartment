package com.aaronxu.friendapartment.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.aaronxu.friendapartment.R;
import com.aaronxu.friendapartment.util.DensityUtil;

/**
 * Created by AaronXu on 2016-10-15.
 */
public class MyCardView extends View{
    private static final String TAG = "CardView";
    private Context mContext;

    private Bitmap mBitmap;
    private String mName;
    private int mPercent;
    private int widthSize;
    private int widthMode;
    private int heightSize;
    private int heightMode;
    private TextPaint mNamePaint;
    private TextPaint mPercentPaint;
    private Paint mBGPaint;

    public MyCardView(Context context){
        this(context,null);
    }
    public MyCardView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public MyCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        mContext = getContext();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyCardView);
        mBitmap = BitmapFactory.decodeResource(getResources(),a.getResourceId(R.styleable.MyCardView_bitmap,R.mipmap.avater_00));
        mName = a.getString(R.styleable.MyCardView_name);
        if (mName == null) mName = "空空";
        mPercent = a.getInteger(R.styleable.MyCardView_percent,88);
        Log.d(TAG, "CardView: 参数初始化完成");
        a.recycle();
        initPaint();
    }

    private void initPaint() {
        mNamePaint = new TextPaint();
        mNamePaint.setColor(getResources().getColor(R.color.cardViewName));
        mNamePaint.setTextSize(30);
        mNamePaint.setAntiAlias(true);

        mPercentPaint = new TextPaint();
        mPercentPaint.setColor(getResources().getColor(R.color.cardViewPercent));
        mPercentPaint.setTextSize(18);
        mPercentPaint.setAntiAlias(true);

        mBGPaint = new Paint();
        mBGPaint.setColor(getResources().getColor(R.color.totalWhite));
        mBGPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        widthMode = MeasureSpec.getMode(widthMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);
        heightMode = MeasureSpec.getMode(heightMeasureSpec);
        Log.d(TAG, "onMeasure: "+widthSize+"  "+heightSize);
//        Log.d(TAG, "onMeasure: "+ widthSize +"\n"+ widthMode +"\n" + heightSize +"\n"+ heightMode);

        setMeasuredDimension(DensityUtil.dip2px(mContext,68),DensityUtil.dip2px(mContext,96));
//        Log.d(TAG, "onMeasure: "+getMeasuredWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(0,0,getMeasuredWidth(),getMeasuredHeight(),DensityUtil.dip2px(mContext,2),DensityUtil.dip2px(mContext,2),mBGPaint);
        canvas.drawBitmap(mBitmap,0,0,null);
        canvas.drawText(mName,(getMeasuredWidth()-mNamePaint.measureText(mName))/2,mBitmap.getHeight()+DensityUtil.dip2px(mContext,13),mNamePaint);
        canvas.drawText(String.valueOf(mPercent),(getMeasuredWidth()- mPercentPaint.measureText(String.valueOf(mPercent)))/2,getMeasuredHeight()-DensityUtil.dip2px(mContext,5),mPercentPaint);
    }
}
