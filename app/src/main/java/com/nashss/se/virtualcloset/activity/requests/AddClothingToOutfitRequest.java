package com.nashss.se.virtualcloset.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = AddClothingToOutfitRequest.Builder.class)
public class AddClothingToOutfitRequest {
    private final String id;
    private final String clothingId;
    private final String customerId;

    private AddClothingToOutfitRequest(String id, String clothingId, String customerId) {
        this.id = id;
        this.clothingId = clothingId;
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public String getClothingId() {
        return clothingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "AddClothingToOutfitRequest{" +
                "id='" + id + '\'' +
                ", clothingId='" + clothingId + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String id;
        private String clothingId;
        private String customerId;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withClothingId(String clothingId) {
            this.clothingId = clothingId;
            return this;
        }

        public Builder withCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public AddClothingToOutfitRequest build() {
            return new AddClothingToOutfitRequest(id, clothingId, customerId);
        }
    }
}
