package com.applicationdevelopers.lordoffood.Model.Common;
/**
 * Created by Syed Ans ALi on 23-june-21.
 */

public class CityModel {
    private String name,id;

    public CityModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CityModel(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
