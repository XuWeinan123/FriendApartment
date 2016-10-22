package com.aaronxu.friendapartment.bean;

import android.text.Editable;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by AaronXu on 2016-10-18.
 */

public class MyUser extends BmobUser {
    //用户名、邮箱、密码、手机号父类已经自带
    private Boolean sex;
    private Integer age;
    private List<String> personLabel;
    private List<String> houseLabel;
    private List<String> roommateLabel;
    private Boolean isGroup;
    private String rentLocation;
    private String company;
    private Integer statusCode;

    public MyUser() {
        this.sex = true;
        this.age = 100;
        this.isGroup = true;
        this.rentLocation = "默认地点";
        this.company = "默认公司";
        this.statusCode = 0;

    }
    public MyUser(String username,String password,String mail){
        this();
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(mail);
    }

    public List<String> getPersonLabel() {
        return personLabel;
    }

    public void setPersonLabel(List<String> personLabel) {
        this.personLabel = personLabel;
    }

    public List<String> getHouseLabel() {
        return houseLabel;
    }

    public void setHouseLabel(List<String> houseLabel) {
        this.houseLabel = houseLabel;
    }

    public List<String> getRoommateLabel() {
        return roommateLabel;
    }

    public void setRoommateLabel(List<String> roommateLabel) {
        this.roommateLabel = roommateLabel;
    }

    public Boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public String getRentLocation() {
        return rentLocation;
    }

    public void setRentLocation(String rentLocation) {
        this.rentLocation = rentLocation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
