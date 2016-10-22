package com.aaronxu.friendapartment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.opengl.Visibility;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.aaronxu.friendapartment.bean.MyUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class PersonCenterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PersonCenterActivity";
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

    private boolean isChanged;
    public static final int RESULT_OK = 76;

    private List<String> statusList;
    private ProgressDialog progressDialog;
    private MyUser tempUser;
    private AlertDialog.Builder saveDialog;
    private EditText mNameDialogEditText;
    private AlertDialog mNameAlertDialog;
    private String beforeChange;
    private EditText mMailDialogEditText;
    private AlertDialog mMailAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setTitle("个人中心");
        mActionBar.setDisplayHomeAsUpEnabled(true);


        statusList = new ArrayList<>();
        statusList.add("找房中");
        statusList.add("找室友");
        statusList.add("已找到");

        tempUser = new MyUser();
        myUser = BmobUser.getCurrentUser(MyUser.class);

        findIdAndSetListen();
        initInformation();
        initDialog();
    }

    private void initDialog() {
        //初始化Loading对话框
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在忙碌，请坐和放宽");
        //初始化保存Dialog
        saveDialog = new AlertDialog.Builder(PersonCenterActivity.this);
        saveDialog.setMessage("保存修改?");
        saveDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.show();
                tempUser.update(myUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null){
                            showToast("信息更新成功");
                            setResult(RESULT_OK);
                            finish();
                        }else {
                            showToast("信息更新失败\n"+e);
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });
        saveDialog.setNegativeButton("取消",null);
        //初始化修改的Dialog
        initAlertDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.person_center_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:// 点击返回图标事件
                this.finish();
                break;
            case R.id.save_change:
                if (isChanged){
                    saveDialog.show();
                }else {
                    showToast("未更改");
                }

        }
        return super.onOptionsItemSelected(item);
    }

    private void findIdAndSetListen() {
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

        mIsGroupSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChanged) isChanged = true;
                tempUser.setGroup(isChecked);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.person_center_name:
                beforeChange = String.valueOf(mNameText.getText());
                mNameDialogEditText.setText(beforeChange);
                mNameAlertDialog.show();
                break;
            case R.id.person_center_mail:
                beforeChange = String.valueOf(mMailText.getText());
                mMailDialogEditText.setText(beforeChange);
                mMailAlertDialog.show();
                break;
            case R.id.person_center_status:
                showToast("用户状态为："+mStatusText.getText());
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

    private void initInformation() {
        mNameText.setText(myUser.getUsername());
        mMailText.setText(myUser.getEmail());
        mMapLocationText.setText(myUser.getRentLocation());
        mCompanyText.setText(myUser.getCompany());
        String tempStatus = statusList.get(myUser.getStatusCode());
        mStatusText.setText(tempStatus);
        mIsGroupSwitch.setChecked(myUser.isGroup());
    }

    public void initAlertDialog(){
        //以下是出初始化了修改用户名称的dialog
        View dialog = LayoutInflater.from(this).inflate(R.layout.layout_for_dialog_name,null);
        mNameDialogEditText = (EditText) dialog.findViewById(R.id.editText_in_dialog);
        AlertDialog.Builder mNameAlertDialogBuilder;
        mNameAlertDialogBuilder = new AlertDialog.Builder(this);
        mNameAlertDialogBuilder.setView(dialog);
        mNameAlertDialogBuilder.setTitle("修改用户名");
        mNameAlertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "修改前用户名："+ beforeChange +"\t修改后用户名："+mNameDialogEditText.getText()+"\n他们是否一致？"+ beforeChange.equals(mNameDialogEditText.getText().toString()));
                if (beforeChange.equals(mNameDialogEditText.getText().toString())){
                    Log.d(TAG, "用户名一致，无需修改");
                }else{
                    mNameText.setText(mNameDialogEditText.getText());
                    if (!isChanged) isChanged = true;
                    //把更新信息先放大tempUser中
                    tempUser.setUsername(String.valueOf(mNameDialogEditText.getText()));
                }
            }
        });
        mNameAlertDialog = mNameAlertDialogBuilder.create();

        //以下是初始化了修改用户邮箱的dialog
        View dialogMail = LayoutInflater.from(this).inflate(R.layout.layout_for_dialog_mail,null);
        mMailDialogEditText = (EditText) dialogMail.findViewById(R.id.editText_in_dialog);
        AlertDialog.Builder mMailAlertDialogBuilder;
        mMailAlertDialogBuilder = new AlertDialog.Builder(this);
        mMailAlertDialogBuilder.setView(dialogMail);
        mMailAlertDialogBuilder.setTitle("修改邮箱");
        mMailAlertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (beforeChange.equals(mMailDialogEditText.getText().toString())){
                    Log.d(TAG, "邮箱一致，无需修改");
                }else{
                    mMailText.setText(mMailDialogEditText.getText());
                    if (!isChanged) isChanged = true;
                    //把更新信息先放大tempUser中
                    tempUser.setEmail(String.valueOf(mMailDialogEditText.getText()));
                }
            }
        });
        mMailAlertDialog = mMailAlertDialogBuilder.create();
    }
    public void showToast(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }
}
