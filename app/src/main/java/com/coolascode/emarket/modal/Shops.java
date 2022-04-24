package com.coolascode.emarket.modal;

public class Shops {

    private String shopName;
    private String shopType;
    private String shopLocation;
    private  String userId;
    private String emailId;

    public Shops() {
    }

    public Shops(String shopName, String shopType, String shopLocation, String userId, String emailId) {
        this.shopName = shopName;
        this.shopType = shopType;
        this.shopLocation = shopLocation;
        this.userId = userId;
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getShopLocation() {
        return shopLocation;
    }

    public void setShopLocation(String shopLocation) {
        this.shopLocation = shopLocation;
    }

}
