package com.aaronxu.friendapartment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aaronxu.friendapartment.bean.MyUser;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mPassword;
    private Button mLoginButton;

    private EditText mRegisterMail;
    private EditText mRegisterName;
    private EditText mRegisterPassword;
    private Button mRegisterButton;

    private MyUser myBmobUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this,"a0386af55dcdc5367aa94407ebd3d9f7");
        isLogined();
        myBmobUser = new MyUser();

        initRegister();
        initLogin();
    }

    private void initLogin() {
        mName = (EditText) findViewById(R.id.editText_name);
        mPassword = (EditText) findViewById(R.id.editText_password);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBmobUser = new MyUser();
                myBmobUser.setUsername(mName.getText().toString());
                myBmobUser.setPassword(mPassword.getText().toString());
                myBmobUser.login(new SaveListener<MyUser>() {
                    @Override
                    public void done(MyUser myUser, BmobException e) {
                        if (e==null){
                            Toast.makeText(LoginActivity.this, "登陆成功",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            i.putExtra("Username",myBmobUser.getUsername());
                            startActivity(i);
                        }else{
                            Toast.makeText(LoginActivity.this, "登录失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void initRegister() {
        mRegisterMail = (EditText) findViewById(R.id.editText_register_mail);
        mRegisterName = (EditText) findViewById(R.id.editText_register_name);
        mRegisterPassword = (EditText) findViewById(R.id.editText_register_password);
        mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBmobUser = new MyUser();
                myBmobUser.setEmail(mRegisterMail.getText().toString());
                myBmobUser.setUsername(mRegisterName.getText().toString());
                myBmobUser.setPassword(mRegisterPassword.getText().toString());
                myBmobUser.signUp(new SaveListener<MyUser>() {
                    @Override
                    public void done(MyUser myUser, BmobException e) {
                        if (myUser != null) {
                            Toast.makeText(LoginActivity.this, "注册成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "myUser为空，注册失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public boolean isLogined() {
        MyUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
        if(bmobUser != null){
            // 允许用户使用应用
            Toast.makeText(LoginActivity.this,"已经登录过",Toast.LENGTH_SHORT).show();
            return true;
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            Toast.makeText(LoginActivity.this,"没有登录过",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
