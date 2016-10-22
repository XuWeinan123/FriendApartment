package com.aaronxu.friendapartment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationActivity extends AppCompatActivity {
    private static final String TAG = "LocationActivity";
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption =null;

    private RelativeLayout locationGpsButton;
    private TextView locationGpsText;
    private ImageView locationIcon;
    private EditText locationSearcher;
    private ListView locationListView;
    private String[] locationBestCitys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        getSupportActionBar().setElevation(0);

        //初始化定位所需要的参数
        initLocationClient();
        //初始化findId
        initFindId();
        //初始化CityList
        initCityList();

        locationGpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation rotateAnimation = AnimationUtils.loadAnimation(LocationActivity.this,R.anim.rotate_anim);
                locationIcon.setImageResource(R.mipmap.location_activity_refresh_icon);
                locationIcon.startAnimation(rotateAnimation);
                mLocationClient.startLocation();
            }
        });
        //第一次使用SimpleAdapter,谨慎一点
        List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
        for (int i = 0;i<locationBestCitys.length;i++){
            Map<String,Object> listItem = new HashMap<String,Object>();
            listItem.put("cityName",locationBestCitys[i]);
            listItems.add(listItem);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,listItems,R.layout.item_best_city,new String[]{"cityName"},new int[]{R.id.item_best_city_textView});
        locationListView.setAdapter(simpleAdapter);
        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "parent:"+parent+"\nview:"+view+"\nposition:"+position+"\nid"+id);
                locationGpsText.setText(locationBestCitys[position]);
            }
        });

        locationGpsButton.callOnClick();
    }

    private void initCityList() {
        locationBestCitys = new String[]{
                "北京","上海","深圳","武汉","广州",
                "珠海","佛山","南京","苏州","杭州",
                "济南","青岛","郑州","石家庄","福州",
                "厦门","长沙","成都","重庆","太原",
        };
    }
    private void initFindId() {
        locationGpsText = (TextView) findViewById(R.id.location_activity_gps_text);
        locationIcon = (ImageView) findViewById(R.id.location_activity_icon);
        locationGpsButton = (RelativeLayout) findViewById(R.id.location_activity_button);
        locationSearcher = (EditText) findViewById(R.id.location_activity_searcher);
        locationListView = (ListView) findViewById(R.id.location_activity_listView);
    }
    private void initLocationClient() {
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                locationIcon.clearAnimation();
                locationIcon.setImageResource(R.mipmap.location_activity_gps_icon);
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。
                        locationGpsText.setText(aMapLocation.getCountry()+aMapLocation.getProvince()+aMapLocation.getCity()+aMapLocation.getDistrict()+aMapLocation.getStreet());
                    }else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.d(TAG,"location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        });

        //初始化定位属性
        initLocationOption();
        mLocationClient.setLocationOption(mLocationOption);
    }

    private void initLocationOption() {
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
    }
}
