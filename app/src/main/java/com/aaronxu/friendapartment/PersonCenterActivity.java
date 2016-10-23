package com.aaronxu.friendapartment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    public static final int REQUEST_LOCATION = 88;
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

    private boolean isChanged=false;
    public static final int RESULT_OK = 76;

    private List<String> statusList;
    private ProgressDialog progressDialog;
    private MyUser tempUser;
    private String beforeChange;
    private int radioBeforeChange;

    private EditText mNameDialogEditText;
    private EditText mMailDialogEditText;
    private RadioGroup mStatusCodeRadio;
    private AlertDialog.Builder saveDialog;
    private AlertDialog mNameAlertDialog;
    private AlertDialog mMailAlertDialog;
    private Intent intentForLocation;
    private AlertDialog mStatusCodeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setTitle("个人中心");
        mActionBar.setDisplayHomeAsUpEnabled(true);
        //初始化跳转的intent
        intentForLocation = new Intent(this,LocationActivity.class);

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
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d(TAG, "onPrepareOptionsMenu: ");
        if (!isChanged){
            menu.findItem(R.id.save_change).setVisible(false);
        }else{
            menu.findItem(R.id.save_change).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
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
    }
    private void initDialog() {
        //初始化Loading对话框
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在保存中，请坐和放宽");
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
                            invalidateOptionsMenu();
                            isChanged = false;
                            //finish();
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
    private void initInformation() {
        mNameText.setText(myUser.getUsername());
        mMailText.setText(myUser.getEmail());
        mMapLocationText.setText(myUser.getRentLocation());
        mCompanyText.setText(myUser.getCompany());
        mStatusText.setText(statusList.get(myUser.getStatusCode()));
        mIsGroupSwitch.setChecked(myUser.isGroup());
        mIsGroupSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "change isGroup "+isChanged);
                if (!isChanged){
                    isChanged = true;
                    invalidateOptionsMenu();
                }
                tempUser.setGroup(isChecked);
            }
        });
        radioBeforeChange = myUser.getStatusCode();
    }
    public void initAlertDialog(){
        //以下是出初始化了修改用户名称的dialog
        View dialog = LayoutInflater.from(this).inflate(R.layout.dialog_name,null);
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
                    Log.d(TAG, "change name"+isChanged);
                    if (!isChanged){
                        isChanged = true;
                        invalidateOptionsMenu();
                    }
                    //把更新信息先放大tempUser中
                    tempUser.setUsername(String.valueOf(mNameDialogEditText.getText()));
                }
            }
        });
        mNameAlertDialog = mNameAlertDialogBuilder.create();

        //以下是初始化了修改用户邮箱的dialog
        View dialogMail = LayoutInflater.from(this).inflate(R.layout.dialog_mail,null);
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
                    Log.d(TAG, "change mail"+isChanged);
                    if (!isChanged) isChanged = true;
                    //把更新信息先放大tempUser中
                    tempUser.setEmail(String.valueOf(mMailDialogEditText.getText()));
                }
            }
        });
        mMailAlertDialog = mMailAlertDialogBuilder.create();

        //以下是初始化了修改用户状态的dialog
        View dialogStatus = LayoutInflater.from(this).inflate(R.layout.dialog_status,null);
        mStatusCodeRadio = (RadioGroup) dialogStatus.findViewById(R.id.radioGroup_in_dialog);
        ((RadioButton)mStatusCodeRadio.getChildAt(myUser.getStatusCode())).setChecked(true);
        AlertDialog.Builder mStatusCodeDialogBuilder;
        mStatusCodeDialogBuilder = new AlertDialog.Builder(this);
        mStatusCodeDialogBuilder.setView(dialogStatus);
        mStatusCodeDialogBuilder.setTitle("修改状态");
        mStatusCodeDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, radioBeforeChange+"\n"+radioChooseNumber(mStatusCodeRadio));
                if (radioBeforeChange == radioChooseNumber(mStatusCodeRadio)){
                    Log.d(TAG, "状态一致，无需修改");
                }else {
                    radioBeforeChange = radioChooseNumber(mStatusCodeRadio);
                    mStatusText.setText(statusList.get(radioBeforeChange));
                    Log.d(TAG, "change status "+isChanged);
                    if (!isChanged){//用户状态
                        isChanged = true;
                        invalidateOptionsMenu();
                    }
                    tempUser.setStatusCode(radioBeforeChange);
                }
            }
        });
        mStatusCodeDialog = mStatusCodeDialogBuilder.create();
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
                ((RadioButton)mStatusCodeRadio.getChildAt(radioBeforeChange)).setChecked(true);
                mStatusCodeDialog.show();
                break;
            case R.id.person_center_isGroup:
                mIsGroupSwitch.setChecked(!mIsGroupSwitch.isChecked());
                break;
            case R.id.person_center_map_location:
                intentForLocation.putExtra("location",myUser.getRentLocation());
                startActivityForResult(intentForLocation,REQUEST_LOCATION);
                break;
            case R.id.person_center_company:
                showToast("选择公司的功能还没做好");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.d("LocationActivity", "onActivityResult: "+data.getStringExtra("locationReturn")+"\n"+resultCode);
        if (resultCode==RESULT_OK){
            mMapLocationText.setText(data.getStringExtra("locationReturn"));
            Log.d(TAG, "change location "+isChanged);
            if(!isChanged){
                isChanged=true;
                invalidateOptionsMenu();//地点
            }
            tempUser.setRentLocation(String.valueOf(mMapLocationText.getText()));
        }
    }
    private int radioChooseNumber(RadioGroup radioGroup){
        int number=-1;
        int radioGroupCount = radioGroup.getChildCount();
        for (int i=0;i<=radioGroupCount;i++){
            if(((RadioButton)radioGroup.getChildAt(i)).isChecked()){
                number = i;
                break;
            }
        }
        return number;
    }
    public void showToast(String str){
        Toast.makeText(this,str,Toast.LENGTH_LONG).show();
    }
}
