package com.nashss.se.virtualcloset.models;

import java.util.Objects;

public class ClothingModel {
    private final String clothingId;
    private final String customerId;
    private final int wornCount;
    private final String category;
    private final String color;
    private final String fit;
    private final String length;
    private final String weather;
    private final String occasion;

    private ClothingModel(String clothingId, String customerId, int wornCount, String category, String color,
                          String fit, String length, String weather, String occasion) {
        this.clothingId = clothingId;
        this.customerId = customerId;
        this.wornCount = wornCount;
        this.category = category;
        this.color = color;
        this.fit = fit;
        this.length = length;
        this.weather = weather;
        this.occasion = occasion;
    }

    public String getClothingId() {
        return clothingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getWornCount() {
        return wornCount;
    }

    public String getCategory() {
        return category;
    }

    public String getColor() {
        return color;
    }

    public String getFit() {
        return fit;
    }

    public String getLength() {
        return length;
    }

    public String getWeather() {
        return weather;
    }

    public String getOccasion() {
        return occasion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClothingModel that = (ClothingModel) o;
        return wornCount == that.wornCount &&
                Objects.equals(clothingId, that.clothingId) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(category, that.category) &&
                Objects.equals(color, that.color) &&
                Objects.equals(fit, that.fit) &&
                Objects.equals(length, that.length) &&
                Objects.equals(weather, that.weather) &&
                Objects.equals(occasion, that.occasion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clothingId, customerId, wornCount, category, color, fit, length, weather, occasion);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String clothingId;
        private String customerId;
        private int wornCount;
        private String category;
        private String color;
        private String fit;
        private String length;
        private String weather;
        private String occasion;

        public Builder withClothingId(String clothingId) {
            this.clothingId = clothingId;
            return this;
        }

        public Builder withCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder withWornCount(int wornCount) {
            this.wornCount = wornCount;
            return this;
        }

        public Builder withCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder withColor(String color) {
            this.color = color;
            return this;
        }

        public Builder withFit(String fit) {
            this.fit = fit;
            return this;
        }

        public Builder withLength(String length) {
            this.length = length;
            return this;
        }

        public Builder withWeather(String weather) {
            this.weather = weather;
            return this;
        }

        public Builder withOccasion(String occasion) {
            this.occasion = occasion;
            return this;
        }

        public ClothingModel build() {
            return new ClothingModel(clothingId, customerId, wornCount, category, color, fit, length, weather, occasion);
        }
    }
}
