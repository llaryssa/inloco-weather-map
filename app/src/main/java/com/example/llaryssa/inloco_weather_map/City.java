package com.example.llaryssa.inloco_weather_map;

/**
 * Created by llaryssa on 1/14/17.
 */

public class City {
    private String name;
    private float maxT;
    private float minT;
    private String description;

    public City(String name, float maxT, float minT, String description) {
        this.name = name;
        this.maxT = maxT;
        this.minT = minT;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMaxT() {
        return maxT;
    }

    public void setMaxT(float maxT) {
        this.maxT = maxT;
    }

    public float getMinT() {
        return minT;
    }

    public void setMinT(float minT) {
        this.minT = minT;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
