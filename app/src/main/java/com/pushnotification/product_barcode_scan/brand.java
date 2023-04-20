package com.pushnotification.product_barcode_scan;

public class brand {
    String id;
    String text;
    String subcategory;

    public brand(String id, String text, String subcategory) {
        this.id = id;
        this.text = text;
        this.subcategory = subcategory;
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

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
}
