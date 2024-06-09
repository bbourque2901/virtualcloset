package com.nashss.se.virtualcloset.activity.requests;

public class IncrementClothingWornCountRequest {
    private final String clothingId;

    private IncrementClothingWornCountRequest(String clothingId) {
        this.clothingId = clothingId;
    }

    public String getClothingId() {
        return clothingId;
    }

    @Override
    public String toString() {
        return "IncrementClothingWornCountRequest{" +
                "clothingId='" + clothingId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String clothingId;

        public Builder withClothingId(String clothingId) {
            this.clothingId = clothingId;
            return this;
        }

        public IncrementClothingWornCountRequest build() {
            return new IncrementClothingWornCountRequest(clothingId);
        }
    }
}
