package com.aaronxu.friendapartment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aaronxu.friendapartment.fragment.FindFragment;
import com.aaronxu.friendapartment.fragment.MainFragment;
import com.aaronxu.friendapartment.fragment.MyFragment;
import com.aaronxu.friendapartment.util.NoScrollViewPager;
import com.aaronxu.friendapartment.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    private NoScrollViewPager mViewPager;
    private List<Fragment> fragmentList;
    private ViewPagerIndicator mIndicator;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActionBar = getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setTitle("首页");

        mViewPager = (NoScrollViewPager) findViewById(R.id.view_pager);

        initFragment();
        initMainViewPager();
        initBottomBarListener();
        Log.d(TAG, "onCreate: ");

        mIndicator.getChildView(0).setColorFilter(ContextCompat.getColor(getBaseContext(),R.color.colorPrimary));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<3;i++){
                    mIndicator.getChildView(i).setColorFilter(ContextCompat.getColor(getBaseContext(),R.color.orginGrayColor));
                }
                mIndicator.getChildView(position).setColorFilter(ContextCompat.getColor(getBaseContext(),R.color.colorPrimary));

                mActionBar.setTitle(mViewPager.getAdapter().getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initBottomBarListener() {
        mIndicator = (ViewPagerIndicator) findViewById(R.id.bottom_navigation_bar);
        Log.d(TAG, "initBottomBarListener: ");
        mIndicator.setmViewPager(mViewPager);
        mIndicator.setItemClickEvent();

    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        MainFragment fragment1 =  MainFragment.newInstance("首页");
        FindFragment fragment2 = FindFragment.newInstance("发现");
        MyFragment fragment3 = MyFragment.newInstance("个人");
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
    }

    private void initMainViewPager() {
        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getItem(position).getArguments().getString("title");
            }
        };
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.menu_locate2);
        Log.d(TAG, "onCreateOptionsMenu: ");

        MenuItemCompat.setOnActionExpandListener(search, new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                mViewPager.setCurrentItem(1);
                ((FindFragment)fragmentList.get(1)).changeFilterVisible();
                mIndicator.setVisibility(View.GONE);
                Log.d(TAG, "onMenuItemActionExpand: "+mActionBar.getElevation());
                mActionBar.setElevation(0);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                ((FindFragment)fragmentList.get(1)).changeFilterGone();
                mIndicator.setVisibility(View.VISIBLE);
                mActionBar.setElevation(12.0f);
                return true;
            }
        });
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    public void firstClick(View v){
        Toast.makeText(this,"到南昌",Toast.LENGTH_SHORT).show();
    }
    public void mostDecorate(View v){
        Toast.makeText(this,"最装修",Toast.LENGTH_SHORT).show();
    }
    public void mostHuamnqi(View v){
        Toast.makeText(this,"最人气",Toast.LENGTH_SHORT).show();
    }
    public void mostBest(View v){
        Toast.makeText(this,"最优质",Toast.LENGTH_SHORT).show();
    }
    public void mostNew(View v){
        Toast.makeText(this,"最新品",Toast.LENGTH_SHORT).show();
    }
    public void mostHot(View v){
        Toast.makeText(this,"最火爆",Toast.LENGTH_SHORT).show();
    }
    public void mostArgue(View v){
        Toast.makeText(this,"最争议",Toast.LENGTH_SHORT).show();
    }
}
