package com.applicationdevelopers.lordoffood.Model.ProductModel;

public class ProductModel {
    private String product_name;
    private String product_price;
    private String product_image;
    private String location;
    private String open_status;
    private String views;
    private String state_name;
    private String description_post;
    private int sectionId;
    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public ProductModel(String product_name, String product_price, String product_image, String location, String open_status, String views, String state_name, String description_post, String product_cat) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_image = product_image;
        this.location = location;
        this.open_status = open_status;
        this.views = views;
        this.state_name = state_name;
        this.description_post = description_post;
        this.product_cat = product_cat;
    }
    public ProductModel(String product_name, String product_price, String product_image, String location, String open_status, String views, String state_name, String description_post, String product_cat,int sectionId) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_image = product_image;
        this.location = location;
        this.open_status = open_status;
        this.views = views;
        this.state_name = state_name;
        this.description_post = description_post;
        this.product_cat = product_cat;
        this.sectionId = sectionId;
    }
    public ProductModel() {
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOpen_status() {
        return open_status;
    }

    public void setOpen_status(String open_status) {
        this.open_status = open_status;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getDescription_post() {
        return description_post;
    }

    public void setDescription_post(String description_post) {
        this.description_post = description_post;
    }

    public String getProduct_cat() {
        return product_cat;
    }

    public void setProduct_cat(String product_cat) {
        this.product_cat = product_cat;
    }

    private String product_cat;
    public ProductModel(String product_name, String product_price) {
        this.product_name = product_name;
        this.product_price = product_price;
    }
    public ProductModel(String product_name, String product_price, String product_image) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_image=product_image;
    }
    public ProductModel(String product_name, String product_price, String product_image,String category) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_image=product_image;
        this.product_cat=category;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }
}

