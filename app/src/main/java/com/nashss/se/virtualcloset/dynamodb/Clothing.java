package com.nashss.se.virtualcloset.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "clothing")
public class Clothing {
    private String clothingId;
    private String customerId;
    private Integer wornCount;
    private String category;
    private String color;
    private String fit;
    private String length;
    private String weather;
    private String occasion;

    @DynamoDBHashKey(attributeName = "clothingId")
    public String getClothingId() {
        return clothingId;
    }

    public void setClothingId(String clothingId) {
        this.clothingId = clothingId;
    }

    @DynamoDBAttribute(attributeName = "customerId")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "CategoryWornCountIndex", attributeName = "wornCount")
    public Integer getWornCount() {
        return wornCount;
    }

    public void setWornCount(Integer wornCount) {
        this.wornCount = wornCount;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "CategoryWornCountIndex", attributeName = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @DynamoDBAttribute(attributeName = "color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @DynamoDBAttribute(attributeName = "fit")
    public String getFit() {
        return fit;
    }

    public void setFit(String fit) {
        this.fit = fit;
    }

    @DynamoDBAttribute(attributeName = "length")
    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    @DynamoDBAttribute(attributeName = "weather")
    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @DynamoDBAttribute(attributeName = "occasion")
    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Clothing clothing = (Clothing) o;
        return Objects.equals(clothingId, clothing.clothingId) &&
                Objects.equals(customerId, clothing.customerId) &&
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
        return Objects.hash(clothingId, customerId, wornCount, category, color, fit, length, weather, occasion);
    }
}
