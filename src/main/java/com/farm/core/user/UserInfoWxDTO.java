package com.farm.core.user;

import java.util.List;

public class UserInfoWxDTO {

    private   String requestAuthCode;

    private String iv;

    private String rawData;

    private String signature;

    private String encryptedData;

    private UserInfo userInfo;


    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getRequestAuthCode() {
        return requestAuthCode;
    }

    public void setRequestAuthCode(String requestAuthCode) {
        this.requestAuthCode = requestAuthCode;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
