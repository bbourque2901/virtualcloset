package com.nashss.se.virtualcloset.activity.results;

import com.nashss.se.virtualcloset.models.ClothingModel;

public class GetClothingResult {
    private final ClothingModel clothing;

    private GetClothingResult(ClothingModel clothing) {
        this.clothing = clothing;
    }

    public ClothingModel getClothing() {
        return clothing;
    }

    @Override
    public String toString() {
        return "GetClothingResult{" +
                "clothing=" + clothing +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ClothingModel clothing;
        public Builder withClothing(ClothingModel clothing) {
            this.clothing = clothing;
            return this;
        }

        public GetClothingResult build() {
            return new GetClothingResult(clothing);
        }
    }
}
