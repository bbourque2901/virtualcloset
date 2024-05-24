package com.nashss.se.virtualcloset.activity.requests;

public class GetOutfitRequest {
    private final String id;

    private GetOutfitRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GetOutfitRequest{" +
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

        public GetOutfitRequest build() {
            return new GetOutfitRequest(id);
        }
    }
}
