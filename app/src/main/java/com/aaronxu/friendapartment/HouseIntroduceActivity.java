package com.aaronxu.friendapartment;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aaronxu.friendapartment.bean.CardBean;
import com.aaronxu.friendapartment.bean.MyUser;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.IconHintView;

import cn.bmob.v3.BmobUser;

public class HouseIntroduceActivity extends AppCompatActivity {

    private static final String TAG = "HouseIntroduceActivity";
    private Intent intentFromFragment;
    private CardBean cardBeanFromFindFragment;
    private RollPagerView mRollViewPager;
    private TextView mMasterName;
    private TextView mMapLocation;
    private LinearLayout mMapLocationButton;
    private ActionBar mActionBar;
    private boolean isCollect = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_introduce);
        mActionBar = getSupportActionBar();

        intentFromFragment = getIntent();
        Bundle bundle = intentFromFragment.getExtras();
        cardBeanFromFindFragment = bundle.getParcelable("item");
        //设置标题
        mActionBar.setTitle(cardBeanFromFindFragment.getmName());
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mRollViewPager = (RollPagerView) findViewById(R.id.roll_view_pager_introduce);
        mMasterName = (TextView) findViewById(R.id.item_master_name);
        mMapLocation = (TextView) findViewById(R.id.item_map_location_text);
        mMapLocationButton = (LinearLayout) findViewById(R.id.item_map_location_button);

        preInit();
        Log.d(TAG, cardBeanFromFindFragment != null ? cardBeanFromFindFragment.toString() : "对象为空");
        init();
    }

    private void init() {
        mActionBar.setTitle(cardBeanFromFindFragment.getmName());
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
        getMenuInflater().inflate(R.menu.menu_houst_introduce, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                this.finish();
                break;
            case R.id.menu_collect:
                if(isCollect) {
                    item.setIcon(R.mipmap.menu_collect);
                    isCollect = false;
                }else {
                    item.setIcon(R.mipmap.menu_collect_2);
                    isCollect = true;
                }
                break;
            case R.id.menu_share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "这是我从我自己写的QQ分享出来的");
                shareIntent.setType("text/plain");

                //设置分享列表的标题，并且每次都显示分享列表
                startActivity(Intent.createChooser(shareIntent, "分享房屋"));
                break;
        }
        return super.onOptionsItemSelected(item);
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
