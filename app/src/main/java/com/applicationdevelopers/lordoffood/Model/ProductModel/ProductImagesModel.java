package com.applicationdevelopers.lordoffood.Model.ProductModel;

public class ProductImagesModel {
    private String imagesUrl;

    public ProductImagesModel() {
    }
    public ProductImagesModel(String imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public String getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(String imagesUrl) {
        this.imagesUrl = imagesUrl;
    }
}
