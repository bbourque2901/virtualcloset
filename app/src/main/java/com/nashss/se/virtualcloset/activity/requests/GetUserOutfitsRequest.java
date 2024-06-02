package com.nashss.se.virtualcloset.activity.requests;

public class GetUserOutfitsRequest {
    private final String customerId;

    private GetUserOutfitsRequest(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "GetUserOutfitsRequest{" +
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

        public GetUserOutfitsRequest build() {
            return new GetUserOutfitsRequest(customerId);
        }
    }
}
