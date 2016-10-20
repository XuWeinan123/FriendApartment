package com.aaronxu.friendapartment.bean;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by AaronXu on 2016-10-18.
 */

public class MyUser extends BmobUser {
    //用户名、邮箱、密码、手机号父类已经自带
    private Boolean sex;
    private int age;
    private List<String> personLabel;
    private List<String> houseLabel;

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
