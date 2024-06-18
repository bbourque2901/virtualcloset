package com.nashss.se.virtualcloset.activity.results;

import com.nashss.se.virtualcloset.models.ClothingModel;

public class DeleteClothingResult {
    private final ClothingModel clothing;

    private DeleteClothingResult(ClothingModel clothing) {
        this.clothing = clothing;
    }

    public ClothingModel getClothing() {
        return clothing;
    }

    @Override
    public String toString() {
        return "DeleteClothingResult{" +
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

        public DeleteClothingResult build() {
            return new DeleteClothingResult(clothing);
        }
    }
}
