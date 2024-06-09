package com.nashss.se.virtualcloset.activity.requests;

public class IncrementOutfitWornCountRequest {
    private final String id;

    private IncrementOutfitWornCountRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "IncrementOutfitWornCountRequest{" +
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

        public IncrementOutfitWornCountRequest build() {
            return new IncrementOutfitWornCountRequest(id);
        }
    }
}
