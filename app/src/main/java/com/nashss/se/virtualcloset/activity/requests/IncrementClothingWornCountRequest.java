package com.nashss.se.virtualcloset.activity.requests;

public class IncrementClothingWornCountRequest {
    private final String clothingId;
    private final String customerId;

    private IncrementClothingWornCountRequest(String clothingId, String customerId) {
        this.clothingId = clothingId;
        this.customerId = customerId;
    }

    public String getClothingId() {
        return clothingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "IncrementClothingWornCountRequest{" +
                "clothingId='" + clothingId + '\'' +
                "customerId='" + customerId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String clothingId;
        private String customerId;

        public Builder withClothingId(String clothingId) {
            this.clothingId = clothingId;
            return this;
        }

        public Builder withCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public IncrementClothingWornCountRequest build() {
            return new IncrementClothingWornCountRequest(clothingId, customerId);
        }
    }
}
