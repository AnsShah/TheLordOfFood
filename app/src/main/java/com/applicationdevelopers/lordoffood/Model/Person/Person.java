package com.applicationdevelopers.lordoffood.Model.Person;

public class Person {


    private int id;
    private String pname;
    private String price;
    private String qty;
    private String category;
    private String imageUrl;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;
    public Person(){

    }

   /* public String getQuatity() {
        return quatity;
    }

    public void setQuatity(String quatity) {
        this.quatity = quatity;
    }*/

    // private String quatity;


    /*  public Person(int id, String pname, double price,Long qty) {
          this.id = id;
          this.pname = pname;
         // this.price = price;
         // this.qty = qty;
      }*/
   /* public Person(int id, String pname, String price, String qty1, String date1) {
        this.id = id;
        this.pname = pname;
        this.price = price;
        this.qty = qty1;
        this.date=date1;
    }*/
    public Person(int id, String pname, String price, String qty1, String category) {
        this.id = id;
        this.pname = pname;
        this.price = price;
        this.qty = qty1;
        this.category=category;
    }
    public Person(int id, String pname, String price, String qty1, String category,String imgUrl) {
        this.id = id;
        this.pname = pname;
        this.price = price;
        this.qty = qty1;
        this.category=category;
        this.imageUrl=imgUrl;
    }
    public Person(String pname,int id,String price, String qty1,String imgUrl) {
        this.id = id;
        this.pname = pname;
        this.price = price;
        this.qty = qty1;
        this.category=category;
        this.imageUrl=imgUrl;
    }
    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty()
    {
        return qty;
    }
    public void setQty(String qty)
    {
        this.qty=qty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
