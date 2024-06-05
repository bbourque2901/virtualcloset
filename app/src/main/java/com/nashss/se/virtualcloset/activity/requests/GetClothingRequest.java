package com.nashss.se.virtualcloset.activity.requests;

public class GetClothingRequest {
    private final String clothingId;

    private GetClothingRequest(String clothingId) {
        this.clothingId = clothingId;
    }

    public String getClothingId() {
        return clothingId;
    }

    @Override
    public String toString() {
        return "GetClothingRequest{" +
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

        public GetClothingRequest build() {
            return new GetClothingRequest(clothingId);
        }
    }
}
