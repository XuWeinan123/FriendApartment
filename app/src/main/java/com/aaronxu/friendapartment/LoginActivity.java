package com.aaronxu.friendapartment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aaronxu.friendapartment.bean.MyUser;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends Activity {


    private EditText mName;
    private EditText mPassword;
    private RelativeLayout mLoginButton;

    private EditText mRegisterMail;
    private EditText mRegisterName;
    private EditText mRegisterPassword;
    private RelativeLayout mRegisterButton;

    private MyUser myBmobUser;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this,"656578d0db5ae6611b73a97705efb329");

        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setTitle("加载中……");
        dialog.setMessage("基于您的网络可能会有些许延迟，请稍安勿躁。");
        dialog.setCancelable(false);

        isLogined();
        myBmobUser = new MyUser();

        initRegister();
        initLogin();
    }

    private void initLogin() {
        mName = (EditText) findViewById(R.id.editText_name);
        mPassword = (EditText) findViewById(R.id.editText_password);
        mLoginButton = (RelativeLayout) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                myBmobUser = new MyUser();
                myBmobUser.setUsername(mName.getText().toString());
                myBmobUser.setPassword(mPassword.getText().toString());
                myBmobUser.login(new SaveListener<MyUser>() {
                    @Override
                    public void done(MyUser myUser, BmobException e) {
                        if (e==null){
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            i.putExtra("Username",myBmobUser.getUsername());
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "登录失败"+(e.getErrorCode()==9018?"\n用户名密码不能为空":e.toString()),Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void initRegister() {
        mRegisterMail = (EditText) findViewById(R.id.editText_register_mail);
        mRegisterName = (EditText) findViewById(R.id.editText_register_name);
        mRegisterPassword = (EditText) findViewById(R.id.editText_register_password);
        mRegisterButton = (RelativeLayout) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                myBmobUser = new MyUser();
                myBmobUser.setEmail(mRegisterMail.getText().toString());
                myBmobUser.setUsername(mRegisterName.getText().toString());
                myBmobUser.setPassword(mRegisterPassword.getText().toString());
                myBmobUser.signUp(new SaveListener<MyUser>() {
                    @Override
                    public void done(MyUser myUser, BmobException e) {
                        if (myUser != null) {
                            Toast.makeText(LoginActivity.this, "注册成功",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            i.putExtra("Username",myBmobUser.getUsername());
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, e.getErrorCode()+"注册失败",Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    public boolean isLogined() {
        MyUser bmobUsered = BmobUser.getCurrentUser(MyUser.class);
        if(bmobUsered != null){
            // 允许用户使用应用
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            i.putExtra("Username",bmobUsered.getUsername());
            startActivity(i);
            finish();
            return true;
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            return false;
        }
    }
}
