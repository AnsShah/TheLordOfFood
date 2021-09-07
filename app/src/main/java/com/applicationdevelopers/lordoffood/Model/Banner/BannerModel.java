package com.applicationdevelopers.lordoffood.Model.Banner;

public class BannerModel {
    public BannerModel(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    @Override
    public String toString() {
        return "BannerModel{" +
                "bannerImage='" + bannerImage + '\'' +
                '}';
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public BannerModel() {
    }

    private String bannerImage;
}
