package com.applicationdevelopers.lordoffood.Model.Common;
/**
 * Created by Syed Ans ALi on 23-june-21.
 */

public class AreaModel {
    private String id,name,city_id;

    @Override
    public String toString() {
        return "AreaModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", city_id='" + city_id + '\'' +
                '}';
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

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public AreaModel() {
    }

    public AreaModel(String id, String name, String city_id) {
        this.id = id;
        this.name = name;
        this.city_id = city_id;
    }
    public AreaModel(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
