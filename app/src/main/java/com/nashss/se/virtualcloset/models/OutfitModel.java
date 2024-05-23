package com.nashss.se.virtualcloset.models;

import java.util.List;
import java.util.Objects;

import static com.nashss.se.virtualcloset.utils.CollectionUtils.copyToList;

public class OutfitModel {
    private final String id;
    private final String name;
    private final String customerId;
    private final List<String> tags;
    private final int wornCount;

    private OutfitModel(String id, String name, String customerId, List<String> tags, int wornCount) {
        this.id = id;
        this.name = name;
        this.customerId = customerId;
        this.tags = tags;
        this.wornCount = wornCount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getTags() {
        return copyToList(tags);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OutfitModel that = (OutfitModel) o;
        return wornCount == that.wornCount &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, customerId, tags, wornCount);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String name;
        private String customerId;
        private List<String> tags;
        private int wornCount;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder withTags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder withWornCount(int wornCount) {
            this.wornCount = wornCount;
            return this;
        }

        public OutfitModel build() {
            return new OutfitModel(id, name, customerId, tags, wornCount);
        }
    }
}
