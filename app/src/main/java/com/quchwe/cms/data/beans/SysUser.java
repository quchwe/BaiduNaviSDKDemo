package com.quchwe.cms.data.beans;

import java.util.Date;

/**
 * Created by quchwe on 2017/2/22 0022.
 */

public class SysUser {

    private Long uid;
    private String userName;
    private String userPassword;
    private String sex;
    private String carId;
    private String carVintage;
    private String address;
    private String phoneNumber;
    private Date purchaseDate;
    private String drivingLicenseId;
    private Date createTime;
    private String carType;
    private Date dischargeDate;
    private String email;
    private int age;
    private Date updateTime;
    private String headImage;
    private String signature;
    private String userToken;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarVintage() {
        return carVintage;
    }

    public void setCarVintage(String carVintage) {
        this.carVintage = carVintage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getDrivingLicenseId() {
        return drivingLicenseId;
    }

    public void setDrivingLicenseId(String drivingLicenseId) {
        this.drivingLicenseId = drivingLicenseId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Date getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(Date dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    //    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Arrays.asList(new SimpleGrantedAuthority("SYSUSER"));
//    }
//
    public String getPassword() {
        return userPassword;
    }



    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "uid=" + uid +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", sex='" + sex + '\'' +
                ", carId='" + carId + '\'' +
                ", carVintage='" + carVintage + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", drivingLicenseId='" + drivingLicenseId + '\'' +
                ", createTime=" + createTime +
                ", carType='" + carType + '\'' +
                ", dischargeDate=" + dischargeDate +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", updateTime=" + updateTime +
                ", headImage='" + headImage + '\'' +
                ", signature='" + signature + '\'' +
                ", userToken='" + userToken + '\'' +
                '}';
    }
}
