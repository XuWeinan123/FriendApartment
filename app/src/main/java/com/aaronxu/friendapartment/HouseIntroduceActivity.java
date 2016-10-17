package com.aaronxu.friendapartment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aaronxu.friendapartment.bean.CardBean;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.IconHintView;

public class HouseIntroduceActivity extends AppCompatActivity {

    private static final String TAG = "HouseIntroduceActivity";
    private Intent intentFromFragment;
    private CardBean cardBeanFromFindFragment;
    private RollPagerView mRollViewPager;
    private TextView mMasterName;
    private TextView mMapLocation;
    private LinearLayout mMapLocationButton;
    private CardView mCardView01;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_introduce);

        intentFromFragment = getIntent();
        Bundle bundle = intentFromFragment.getExtras();
        cardBeanFromFindFragment = bundle.getParcelable("item");
        //设置标题
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setTitle(cardBeanFromFindFragment.getmName());
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mRollViewPager = (RollPagerView) findViewById(R.id.roll_view_pager_introduce);
        mMasterName = (TextView) findViewById(R.id.item_master_name);
        mMapLocation = (TextView) findViewById(R.id.item_map_location_text);
        mMapLocationButton = (LinearLayout) findViewById(R.id.item_map_location_button);
        mCardView01 = (CardView) findViewById(R.id.item_card_view_01);

        preInit();
        Log.d(TAG, cardBeanFromFindFragment != null ? cardBeanFromFindFragment.toString() : "对象为空");
        init();
    }

    private void init() {
        mMasterName.setText("雇主："+cardBeanFromFindFragment.getmMasterName());
        String tempForLocation = cardBeanFromFindFragment.getmMapLocation();
        mMapLocation.setText(tempForLocation.substring(tempForLocation.indexOf("?q=")+3));
        mMapLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HouseIntroduceActivity.this,"跳转至自带地图应用",Toast.LENGTH_SHORT).show();
                Uri mUri = Uri.parse(cardBeanFromFindFragment.getmMapLocation());
                Intent mIntent = new Intent(Intent.ACTION_VIEW,mUri);
                startActivity(mIntent);
            }
        });
        View popupView = getLayoutInflater().inflate(R.layout.card_introduce_roommate, null);
        mPopupWindow = new PopupWindow(popupView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mCardView01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HouseIntroduceActivity.this,"点击了cardView",Toast.LENGTH_SHORT).show();
                mPopupWindow.showAsDropDown(v);
            }
        });
    }

    private void preInit() {
        //设置播放时间间隔
        mRollViewPager.setPlayDelay(3000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new TestNormalAdapter());
        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        mRollViewPager.setHintView(new IconHintView(this, R.mipmap.activate, R.mipmap.unactivate));
        //mRollViewPager.setHintView(new ColorPointHintView(this, Color.YELLOW, Color.WHITE));
        //mRollViewPager.setHintView(new TextHintView(this));
        //mRollViewPager.setHintView(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class TestNormalAdapter extends StaticPagerAdapter {
        private int[] imgs = {
                R.drawable.item_default,
                R.drawable.item_default,
                R.drawable.item_default,
        };


        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }


        @Override
        public int getCount() {
            return imgs.length;
        }
    }
}
