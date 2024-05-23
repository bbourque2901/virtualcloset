package com.nashss.se.virtualcloset.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.nashss.se.virtualcloset.converters.ClothingLinkedListConverter;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@DynamoDBTable(tableName = "outfits")
public class Outfit {
    private String id;
    private String name;
    private String customerId;
    private Set<String> tags;
    private List<Clothing> clothingItems;
    private Integer wornCount;

    @DynamoDBHashKey(attributeName = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "customerId")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @DynamoDBAttribute(attributeName = "tags")
    public Set<String> getTags() {
        if (null == tags) {
            return null;
        }
        return new HashSet<>(tags);
    }

    public void setTags(Set<String> tags) {
        if (null == tags) {
            this.tags = null;
        } else {
            this.tags = new HashSet<>(tags);
        }

        this.tags = tags;
    }

    @DynamoDBAttribute(attributeName = "clothingItems")
    @DynamoDBTypeConverted(converter = ClothingLinkedListConverter.class)
    public List<Clothing> getClothingItems() {
        return clothingItems;
    }

    public void setClothingItemList(List<Clothing> clothingItems) {
        this.clothingItems = clothingItems;
    }

    @DynamoDBAttribute(attributeName = "wornCount")
    public Integer getWornCount() {
        return wornCount;
    }

    public void setWornCount(Integer wornCount) {
        this.wornCount =  wornCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Outfit outfit = (Outfit) o;
        return Objects.equals(id, outfit.id) &&
                Objects.equals(name, outfit.name) &&
                Objects.equals(customerId, outfit.customerId) &&
                Objects.equals(tags, outfit.tags) &&
                Objects.equals(clothingItems, outfit.clothingItems) &&
                Objects.equals(wornCount, outfit.wornCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, customerId, tags, clothingItems, wornCount);
    }
}