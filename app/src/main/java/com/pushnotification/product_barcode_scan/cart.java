package com.pushnotification.product_barcode_scan;

public class cart {
    String id;
    String catrgory;
    String subcategory;
    String brand;
    String model;
    String productname;
    String count;
    String status;

    public cart(String id, String catrgory, String subcategory, String brand, String model, String productname, String count, String status) {
        this.id = id;
        this.catrgory = catrgory;
        this.subcategory = subcategory;
        this.brand = brand;
        this.model = model;
        this.productname = productname;
        this.count = count;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatrgory() {
        return catrgory;
    }

    public void setCatrgory(String catrgory) {
        this.catrgory = catrgory;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
