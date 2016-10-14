package com.aaronxu.friendapartment;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPager mViewPager;
    private List<Fragment> fragmentList;
    private BottomBar mBottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        
        initFragment();
        initMainViewPager();
        initBottomBarListener();

    }

    private void initBottomBarListener() {
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if(tabId == R.id.tab_main){
                    Toast.makeText(getApplicationContext(),"跳转到了主页",Toast.LENGTH_SHORT).show();
                }
                if(tabId == R.id.tab_find){
                    Toast.makeText(getApplicationContext(),"跳转到了发现",Toast.LENGTH_SHORT).show();
                }
                if(tabId == R.id.tab_my){
                    Toast.makeText(getApplicationContext(),"跳转到了个人设置",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        Fragment fragment1 = MainFragment.newInstance("首页");
        fragmentList.add(fragment1);
        Log.d(TAG, "initFragment: ");
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
        return true;
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
