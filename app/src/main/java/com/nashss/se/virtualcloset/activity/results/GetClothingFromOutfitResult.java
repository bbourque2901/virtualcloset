package com.nashss.se.virtualcloset.activity.results;

import com.nashss.se.virtualcloset.models.ClothingModel;

import java.util.ArrayList;
import java.util.List;

public class GetClothingFromOutfitResult {
    private final List<ClothingModel> clothingList;

    private GetClothingFromOutfitResult(List<ClothingModel> clothingList) {
        this.clothingList = clothingList;
    }

    public List<ClothingModel> getClothingList() {
        return new ArrayList<>(clothingList);
    }

    @Override
    public String toString() {
        return "GetClothingFromOutfitResult{" +
                "clothingList=" + clothingList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<ClothingModel> clothingList;

        public Builder withClothingList(List<ClothingModel> clothingList) {
            this.clothingList = new ArrayList<>(clothingList);
            return this;
        }

        public GetClothingFromOutfitResult build() {
            return new GetClothingFromOutfitResult(clothingList);
        }
    }
}
