package com.pushnotification.product_barcode_scan;

public class Product {
    String id;
    String name;
    String category;
    String subcategory;
    String brand;
    String model;
    String count;

    public Product(String id, String name, String category, String subcategory, String brand, String model, String count) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.subcategory = subcategory;
        this.brand = brand;
        this.model = model;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
