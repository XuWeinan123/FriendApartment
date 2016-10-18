package com.aaronxu.friendapartment.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by AaronXu on 2016-10-18.
 */

public class MyUser extends BmobUser {
    private Boolean sex;
    private int age;
    private String name;
    private String phoneNumber;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
