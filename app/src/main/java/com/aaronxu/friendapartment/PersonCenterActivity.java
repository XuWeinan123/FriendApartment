package com.aaronxu.friendapartment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.aaronxu.friendapartment.bean.MyUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class PersonCenterActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mName;
    private TextView mNameText;
    private RelativeLayout mMail;
    private TextView mMailText;
    private RelativeLayout mStatus;
    private TextView mStatusText;
    private RelativeLayout mIsGroup;
    private Switch mIsGroupSwitch;
    private RelativeLayout mMapLocation;
    private TextView mMapLocationText;
    private RelativeLayout mCompany;
    private TextView mCompanyText;
    private MyUser myUser;

    private List<String> statusList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center);

        statusList = new ArrayList<>();
        statusList.add("找房中");
        statusList.add("找室友");
        statusList.add("已找到");

        myUser = BmobUser.getCurrentUser(MyUser.class);
        findId();
        initInformation();
    }

    private void initInformation() {
        mNameText.setText(myUser.getUsername());
        mMailText.setText(myUser.getEmail());
        mMapLocationText.setText(myUser.getRentLocation());
        mCompanyText.setText(myUser.getCompany());
        String tempStatus = statusList.get(myUser.getStatusCode());
        mStatusText.setText(tempStatus);
    }

    private void findId() {
        mName = (RelativeLayout) findViewById(R.id.person_center_name);
        mNameText = (TextView) findViewById(R.id.person_center_name_text);
        mMail = (RelativeLayout) findViewById(R.id.person_center_mail);
        mMailText = (TextView) findViewById(R.id.person_center_mail_text);
        mStatus = (RelativeLayout) findViewById(R.id.person_center_status);
        mStatusText = (TextView) findViewById(R.id.person_center_status_text);
        mIsGroup = (RelativeLayout) findViewById(R.id.person_center_isGroup);
        mIsGroupSwitch = (Switch) findViewById(R.id.person_center_isGroup_switch);
        mMapLocation = (RelativeLayout) findViewById(R.id.person_center_map_location);
        mMapLocationText = (TextView) findViewById(R.id.person_center_map_location_text);
        mCompany = (RelativeLayout) findViewById(R.id.person_center_company);
        mCompanyText = (TextView) findViewById(R.id.person_center_company_text);

        mName.setOnClickListener(this);
        mMail.setOnClickListener(this);
        mStatus.setOnClickListener(this);
        mIsGroup.setOnClickListener(this);
        mMapLocation.setOnClickListener(this);
        mCompany.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.person_center_name:
                showToast("person_center_name");
                break;
            case R.id.person_center_mail:
                showToast("person_center_mail");
                break;
            case R.id.person_center_status:
                showToast("person_center_status");
                break;
            case R.id.person_center_isGroup:
                mIsGroupSwitch.setChecked(!mIsGroupSwitch.isChecked());
                break;
            case R.id.person_center_map_location:
                showToast("person_center_map_location");
                break;
            case R.id.person_center_company:
                showToast("person_center_company");
                break;

        }
    }
    public void showToast(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }
}
