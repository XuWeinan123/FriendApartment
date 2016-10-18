package com.aaronxu.friendapartment;

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
    private BmobUser myBmobUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this,"a0386af55dcdc5367aa94407ebd3d9f7");
        isLogined();
        myBmobUser = new BmobUser();
        mName = (EditText) findViewById(R.id.editText_name);
        mPassword = (EditText) findViewById(R.id.editText_password);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.loginByAccount(String.valueOf(mName.getText()), String.valueOf(mPassword.getText()), new LogInListener<MyUser>() {
                    @Override
                    public void done(MyUser user, BmobException e) {
                        if(user!=null){
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this,"登录失败"+e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public boolean isLogined() {
        BmobUser bmobUser = BmobUser.getCurrentUser();
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
