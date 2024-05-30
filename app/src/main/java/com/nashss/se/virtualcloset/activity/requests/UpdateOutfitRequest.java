package com.nashss.se.virtualcloset.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdateOutfitRequest.Builder.class)
public class UpdateOutfitRequest {
    private final String id;
    private final String name;
    private final String customerId;

    private UpdateOutfitRequest(String id, String name, String customerId) {
        this.id = id;
        this.name = name;
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "UpdateOutfitRequest{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
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
        private String name;
        private String customerId;

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

        public UpdateOutfitRequest build() {
            return new UpdateOutfitRequest(id, name, customerId);
        }
    }
}
