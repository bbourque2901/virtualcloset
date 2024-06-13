package com.nashss.se.virtualcloset.activity.requests;

public class IncrementOutfitWornCountRequest {
    private final String id;
    private final String customerId;

    private IncrementOutfitWornCountRequest(String id, String customerId) {
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
        return "IncrementOutfitWornCountRequest{" +
                "id='" + id + '\'' +
                "customerId='" + customerId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

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

        public IncrementOutfitWornCountRequest build() {
            return new IncrementOutfitWornCountRequest(id, customerId);
        }
    }
}
