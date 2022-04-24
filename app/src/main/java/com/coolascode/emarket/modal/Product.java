package com.coolascode.emarket.modal;

public class Product {
    private String productName;
    private String productPrice;
    private String shopUid;
    private String shopType;
    private String imageUri;
    private String key;
    private String productDesc;


    public Product() {
    }


    public String getShopUid() {
        return shopUid;
    }


    public Product(String productName, String productPrice, String shopUid, String shopType, String imageUri, String key, String productDesc) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.shopUid = shopUid;
        this.shopType = shopType;
        this.imageUri = imageUri;
        this.key = key;
        this.productDesc = productDesc;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setShopUid(String shopUid) {
        this.shopUid = shopUid;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

}
