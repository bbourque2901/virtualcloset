package com.nashss.se.virtualcloset.activity.requests;

public class GetUserClothingRequest {
    private final String customerId;

    private GetUserClothingRequest(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "GetUserClothingRequest{" +
                "customerId='" + customerId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String customerId;

        public Builder withCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public GetUserClothingRequest build() {
            return new GetUserClothingRequest(customerId);
        }
    }
}
