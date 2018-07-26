package com.iwulh.iwulhdemo.bean;

import java.io.Serializable;

/**
 * Auto-generated: 2018-06-07 15:0:16
 */
public class Data implements Serializable{

    private Integer id;
    private String loginName;
    private String userName;
    private String userPassword;
    private String userPortrait;
    private String userBirthday;
    private Integer userAge;
    private String userSex;
    private String userOccupation;
    private String userCompany;
    private String userPosttion;
    private String postalAddress;
    private String userPhone;
    private String userEmail;
    private String creationTime;
    private String updataTime;
    private Integer state;
    private String userAutograph;

    public Integer getId() {
        return id;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserPortrait() {
        return userPortrait;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public String getUserSex() {
        return userSex;
    }

    public String getUserOccupation() {
        return userOccupation;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public String getUserPosttion() {
        return userPosttion;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public String getUpdataTime() {
        return updataTime;
    }

    public Integer getState() {
        return state;
    }

    public String getUserAutograph() {
        return userAutograph;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserPortrait(String userPortrait) {
        this.userPortrait = userPortrait;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public void setUserOccupation(String userOccupation) {
        this.userOccupation = userOccupation;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public void setUserPosttion(String userPosttion) {
        this.userPosttion = userPosttion;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public void setUpdataTime(String updataTime) {
        this.updataTime = updataTime;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setUserAutograph(String userAutograph) {
        this.userAutograph = userAutograph;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userPortrait='" + userPortrait + '\'' +
                ", userBirthday='" + userBirthday + '\'' +
                ", userAge=" + userAge +
                ", userSex='" + userSex + '\'' +
                ", userOccupation='" + userOccupation + '\'' +
                ", userCompany='" + userCompany + '\'' +
                ", userPosttion='" + userPosttion + '\'' +
                ", postalAddress='" + postalAddress + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", updataTime='" + updataTime + '\'' +
                ", state=" + state +
                ", userAutograph='" + userAutograph + '\'' +
                '}';
    }
}