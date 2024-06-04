package com.nashss.se.virtualcloset.activity.requests;

public class GetSortedClothingRequest {
    private final String customerId;
    private final boolean ascending;

    private GetSortedClothingRequest(String customerId, boolean ascending) {
        this.customerId = customerId;
        this.ascending = ascending;
    }

    public String getCustomerId() {
        return customerId;
    }

    public boolean isAscending() {
        return ascending;
    }

    @Override
    public String toString() {
        return "GetSortedClothingRequest{" +
                "customerId='" + customerId + '\'' +
                ", ascending=" + ascending +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String customerId;
        private boolean ascending;

        public Builder withCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder withAscending(boolean ascending) {
            this.ascending = ascending;
            return this;
        }

        public GetSortedClothingRequest build() {
            return new GetSortedClothingRequest(customerId, ascending);
        }
    }
}
