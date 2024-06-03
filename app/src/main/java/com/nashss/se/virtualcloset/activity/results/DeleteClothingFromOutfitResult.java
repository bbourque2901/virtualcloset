package com.nashss.se.virtualcloset.activity.results;

import com.nashss.se.virtualcloset.models.ClothingModel;

import java.util.ArrayList;
import java.util.List;

public class DeleteClothingFromOutfitResult {
    private final List<ClothingModel> clothingModels;

    private DeleteClothingFromOutfitResult(List<ClothingModel> clothingModels) {
        this.clothingModels = clothingModels;
    }

    public List<ClothingModel> getClothingModels() {
        return new ArrayList<>(clothingModels);
    }

    @Override
    public String toString() {
        return "DeleteClothingFromOutfitResult{" +
                "clothingModels=" + clothingModels +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private List<ClothingModel> clothingModels;

        public Builder withClothingModels(List<ClothingModel> clothingModels) {
            this.clothingModels = new ArrayList<>(clothingModels);
            return this;
        }

        public DeleteClothingFromOutfitResult build() {
            return new DeleteClothingFromOutfitResult(clothingModels);
        }
    }
}
