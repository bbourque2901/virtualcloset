package com.nashss.se.virtualcloset.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = DeleteOutfitRequest.Builder.class)
public class DeleteOutfitRequest {
    private final String id;
    private final String customerId;

    private DeleteOutfitRequest(String id, String customerId) {
        this.id = id;
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }
    public String getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "DeleteOutfitRequest{" +
                "id='" + id + '\'' +
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
        private String customerId;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public DeleteOutfitRequest build() {
            return new DeleteOutfitRequest(id, customerId);
        }
    }
}
