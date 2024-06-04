package com.nashss.se.virtualcloset.activity.results;

import com.nashss.se.virtualcloset.models.ClothingModel;

import java.util.List;

public class GetSortedClothingResult {
    private final List<ClothingModel> clothes;

    private GetSortedClothingResult(List<ClothingModel> clothes) {
        this.clothes = clothes;
    }

    public List<ClothingModel> getClothes() {
        return clothes;
    }

    @Override
    public String toString() {
        return "GetSortedClothingResult{" +
                "clothes=" + clothes +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<ClothingModel> clothes;

        public Builder withClothes(List<ClothingModel> clothes) {
            this.clothes = clothes;
            return this;
        }

        public GetSortedClothingResult build() {
            return new GetSortedClothingResult(clothes);
        }
    }
}
