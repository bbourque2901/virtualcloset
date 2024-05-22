package com.nashss.se.virtualcloset.dynamodb;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Outfit {
    private String id;
    private String name;
    private String customerId;
    private Set<String> tags;
    private List<Clothing> clothingItemList;
    private Integer wornCount;

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

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

    public List<Clothing> getClothingItemList() {
        return clothingItemList;
    }

    public void setClothingItemList(List<Clothing> clothingItemList) {
        this.clothingItemList = clothingItemList;
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
                Objects.equals(clothingItemList, outfit.clothingItemList) &&
                Objects.equals(wornCount, outfit.wornCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, customerId, tags, clothingItemList, wornCount);
    }
}
