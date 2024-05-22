package com.nashss.se.virtualcloset.dynamodb;

import java.util.Objects;

public class Clothing {
    private String clothingId;
    private Integer wornCount;
    private String category;
    private String color;
    private String fit;
    private String length;
    private String weather;
    private String occasion;

    public String getClothingId() {
        return clothingId;
    }

    public void setClothingId(String clothingId) {
        this.clothingId = clothingId;
    }

    public Integer getWornCount() {
        return wornCount;
    }

    public void setWornCount(Integer wornCount) {
        this.wornCount = wornCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFit() {
        return fit;
    }

    public void setFit(String fit) {
        this.fit = fit;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothing clothing = (Clothing) o;
        return Objects.equals(clothingId, clothing.clothingId) &&
                Objects.equals(wornCount, clothing.wornCount) &&
                Objects.equals(category, clothing.category) &&
                Objects.equals(color, clothing.color) &&
                Objects.equals(fit, clothing.fit) &&
                Objects.equals(length, clothing.length) &&
                Objects.equals(weather, clothing.weather) &&
                Objects.equals(occasion, clothing.occasion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clothingId, wornCount, category, color, fit, length, weather, occasion);
    }
}
