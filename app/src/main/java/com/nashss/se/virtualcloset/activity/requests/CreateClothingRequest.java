package com.nashss.se.virtualcloset.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateClothingRequest.Builder.class)
public class CreateClothingRequest {
    private final String customerId;
    private final String category;
    private final String color;
    private final String fit;
    private final String length;
    private final String weather;
    private final String occasion;

    private CreateClothingRequest(String customerId, String category, String color,
                                  String fit, String length, String weather, String occasion) {
        this.customerId = customerId;
        this.category = category;
        this.color = color;
        this.fit = fit;
        this.length = length;
        this.weather = weather;
        this.occasion = occasion;
    }

    public String getCustomerId() {
        return customerId;
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
    public String toString() {
        return "CreateClothingRequest{" +
                "customerId='" + customerId + '\'' +
                ", category='" + category + '\'' +
                ", color='" + color + '\'' +
                ", fit='" + fit + '\'' +
                ", length='" + length + '\'' +
                ", weather='" + weather + '\'' +
                ", occasion='" + occasion + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String customerId;
        private String category;
        private String color;
        private String fit;
        private String length;
        private String weather;
        private String occasion;

        public Builder withCustomerId(String customerId) {
            this.customerId = customerId;
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

        public CreateClothingRequest build() {
            return new CreateClothingRequest(customerId, category, color, fit, length, weather, occasion);
        }
    }
}
