package com.aaronxu.friendapartment.bean;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import com.aaronxu.friendapartment.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by woshi on 2016-10-15.
 */
public class CardBean implements Parcelable {
    private Bitmap mBitmap;
    private String mName;
    private int mPrice;
    private List<Bitmap> mPicList;
    private String mMasterName;
    private String mMapLocation;
    private String mNumber;

    public CardBean(){
        this.mBitmap = null;
        mName = "空";
        mPrice = 88;
    }
    public CardBean(Bitmap mBitmap, String mName, int mPrice) {
        this(mBitmap,mName,mPrice,null,"主人名","地点名","联系方式");
        this.mPicList = new ArrayList<>();
        mPicList.add(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.item_default));
    }
    public CardBean(Bitmap mBitmap, String mName, int mPrice,
                    List<Bitmap> mPicList,String mMasterName,String mMapLocation,String mNumber) {
        this.mBitmap = mBitmap;
        this.mName = mName;
        this.mPrice = mPrice;
        this.mPicList = mPicList;
        this.mMasterName = mMasterName;
        this.mMapLocation = mMapLocation;
        this.mNumber = mNumber;
    }

    @Override
    public String toString() {
        return "房屋名字是：" + mBitmap + "\n房屋价格是：" + mPrice + "\n主人名字是：" + mMasterName +"\n地点是：" + mMapLocation + "\n联系方式是：" + mNumber;
    }

    protected CardBean(Parcel in) {
        mBitmap = null;
        mName = in.readString();
        mPrice = in.readInt();
        mMasterName = in.readString();
        mMapLocation = in.readString();
        mNumber = in.readString();
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeInt(mPrice);
        parcel.writeString(mMasterName);
        parcel.writeString(mMapLocation);
        parcel.writeString(mNumber);
    }

    public static final Creator<CardBean> CREATOR = new Creator<CardBean>() {
        @Override
        public CardBean createFromParcel(Parcel in) {
            return new CardBean(in);
        }

        @Override
        public CardBean[] newArray(int size) {
            return new CardBean[size];
        }
    };

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

    public int getmPrice() {
        return mPrice;
    }

    public void setmPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public List<Bitmap> getmPicList() {
        return mPicList;
    }

    public void setmPicList(List<Bitmap> mPicList) {
        this.mPicList = mPicList;
    }

    public String getmMasterName() {
        return mMasterName;
    }

    public void setmMasterName(String mMasterName) {
        this.mMasterName = mMasterName;
    }

    public String getmMapLocation() {
        return mMapLocation;
    }

    public void setmMapLocation(String mMapLocation) {
        this.mMapLocation = mMapLocation;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
