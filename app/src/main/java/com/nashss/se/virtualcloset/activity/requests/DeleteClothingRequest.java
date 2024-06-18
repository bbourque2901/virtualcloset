package com.nashss.se.virtualcloset.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = DeleteClothingRequest.Builder.class)
public class DeleteClothingRequest {
    private final String clothingId;
    private final String customerId;

    private DeleteClothingRequest(String clothingId, String customerId) {
        this.clothingId = clothingId;
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getClothingId() {
        return clothingId;
    }

    @Override
    public String toString() {
        return "DeleteClothingRequest{" +
                "clothingId='" + clothingId + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String customerId;
        private String clothingId;

        public Builder withClothingId(String clothingId) {
            this.clothingId = clothingId;
            return this;
        }

        public Builder withCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public DeleteClothingRequest build() {
            return new DeleteClothingRequest(clothingId, customerId);
        }
    }
}
