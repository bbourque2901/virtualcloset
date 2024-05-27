package com.nashss.se.virtualcloset.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

@JsonDeserialize(builder = CreateOutfitRequest.Builder.class)
public class CreateOutfitRequest {
    private final String name;
    private final String customerId;
    private final List<String> tags;

    private  CreateOutfitRequest(String name, String customerId, List<String> tags) {
        this.name = name;
        this.customerId = customerId;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<String> getTags() {
        return List.copyOf(tags);
    }

    @Override
    public String toString() {
        return "CreateOutfitRequest{" +
                "name='" + name + '\'' +
                ", customerId='" + customerId + '\'' +
                ", tags=" + tags +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String name;
        private String customerId;
        private List<String> tags;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder withTags(List<String> tags) {
            this.tags = List.copyOf(tags);
            return this;
        }

        public CreateOutfitRequest build() {
            return new CreateOutfitRequest(name, customerId, tags);
        }
    }
}
