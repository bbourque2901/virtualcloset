package com.nashss.se.virtualcloset.activity.requests;

public class GetClothingFromOutfitRequest {
    private final String id;

    private GetClothingFromOutfitRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GetClothingFromOutfitRequest{" +
                "id='" + id + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public GetClothingFromOutfitRequest build() {
            return new GetClothingFromOutfitRequest(id);
        }
    }
}
