package com.applicationdevelopers.lordoffood.Model.Menu;

public class MenuModel {

    public String menuName, url;
    public boolean hasChildren, isGroup;
    public int id;

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int sectionId;
    public String product_price;
    public String product_image;
    public String description_post;
    public String openStatus;
    public String cate;

    public MenuModel(String menuName, boolean isGroup, boolean hasChildren, String url) {

        this.menuName = menuName;
        this.url = url;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }
    public MenuModel(String menuName, boolean isGroup, boolean hasChildren, int id) {

        this.menuName = menuName;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
        this.id=id;
    }
    public MenuModel(String menuName, boolean isGroup, boolean hasChildren) {

        this.menuName = menuName;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }
    public MenuModel(String menuName, boolean isGroup, boolean hasChildren, int id, String product_price, String product_image, String description_post, String openStatus, String cate) {
        this.menuName = menuName;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
        this.id=id;
        this.product_price=product_price;
        this.product_image=product_image;
        this.description_post=description_post;
        this.openStatus=openStatus;
        this.cate=cate;
    }
    public MenuModel(String menuName, boolean isGroup, boolean hasChildren, int id, String product_price, String product_image, String description_post, String openStatus, String cate,int sectionId) {
        this.menuName = menuName;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
        this.id=id;
        this.product_price=product_price;
        this.product_image=product_image;
        this.description_post=description_post;
        this.openStatus=openStatus;
        this.cate=cate;
        this.sectionId=sectionId;
    }

    public MenuModel(String menuName, String product_price) {
        this.menuName = menuName;
        this.product_price = product_price;
    }

    @Override
    public String toString() {
        return "MenuModel{" +
                "menuName='" + menuName + '\'' +
                ", url='" + url + '\'' +
                ", hasChildren=" + hasChildren +
                ", isGroup=" + isGroup +
                ", id=" + id +
                ", product_price='" + product_price + '\'' +
                ", product_image='" + product_image + '\'' +
                ", description_post='" + description_post + '\'' +
                ", openStatus='" + openStatus + '\'' +
                ", cate='" + cate + '\'' +
                '}';
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDescription_post() {
        return description_post;
    }

    public void setDescription_post(String description_post) {
        this.description_post = description_post;
    }

    public String getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(String openStatus) {
        this.openStatus = openStatus;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public MenuModel(String menuName, String product_price, String product_image,String openStatus) {
        this.menuName = menuName;
        this.product_price = product_price;
        this.product_image = product_image;
        this.openStatus=openStatus;
    }
    public MenuModel(String menuName, String product_price, String product_image) {
        this.menuName = menuName;
        this.product_price = product_price;
        this.product_image = product_image;
    }
}
