package com.pushnotification.product_barcode_scan;

public class model {
    String id;
    String text;
    String brand;

    public model(String id, String text, String brand) {
        this.id = id;
        this.text = text;
        this.brand = brand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
