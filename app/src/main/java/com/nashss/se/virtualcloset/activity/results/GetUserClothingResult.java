package com.nashss.se.virtualcloset.activity.results;

import com.nashss.se.virtualcloset.models.ClothingModel;

import java.util.List;

public class GetUserClothingResult {
    private final List<ClothingModel> clothing;

    private GetUserClothingResult(List<ClothingModel> clothing) {
        this.clothing = clothing;
    }

    public List<ClothingModel> getClothing() {
        return clothing;
    }

    @Override
    public String toString() {
        return "GetUserClothingResult{" +
                "clothing=" + clothing +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<ClothingModel> clothing;

        public Builder withClothing(List<ClothingModel> clothing) {
            this.clothing = clothing;
            return this;
        }

        public GetUserClothingResult build() {
            return new GetUserClothingResult(clothing);
        }
    }
}
