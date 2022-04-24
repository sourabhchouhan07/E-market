package com.coolascode.emarket.modal;

public class Cart {
    String productName;
    String productPrice;
    String productImage;
    String productQuantity;

    public Cart() {
    }

    public Cart(String productName, String productPrice, String productImage, String productQuantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productQuantity = productQuantity;
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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }
}
