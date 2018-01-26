package com.parent.forecast.models;

/**
 * Created by mohamedradwan on 1/21/18.
 */

public class CityModel {

    int id;
    String name;
    public CityModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CityModel(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}