package com.example.myapplication4233424;

public class MemberInfo {
    private String name;
    private String phoneNumber;
    private String birthDay;
    private String adress;
    private String photoUrl;

    public MemberInfo(String name, String phoneNumber, String birthDay, String adress, String photoUrl) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.adress = adress;
        this.photoUrl = photoUrl;
    }

    public MemberInfo(String name, String phoneNumber, String birthDay, String adress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.adress = adress;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getphoneNumber() {
        return this.phoneNumber;
    }
    public void setphoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getbirthDay() {
        return this.birthDay;
    }
    public void setbirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getadress() {
        return this.adress;
    }
    public void setadress(String adress) {
        this.adress = adress;
    }

    public String getphotoUrl() {
        return this.photoUrl;
    }
    public void setphotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

}
