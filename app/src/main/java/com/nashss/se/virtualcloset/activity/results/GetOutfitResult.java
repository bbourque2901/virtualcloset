package com.nashss.se.virtualcloset.activity.results;


public class GetOutfitResult {
    private final OutfitModel outfit;

    private GetOutfitResult(OutfitModel outfit) {
        this.outfit = outfit;
    }

    public OutfitModel getOutfit() {
        return outfit;
    }

    @Override
    public String toString() {
        return "GetOutfitResult{" +
                "outfit=" + outfit +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private OutfitModel outfit;

        public Builder withOutfit(OutfitModel outfit) {
            this.outfit = outfit;
            return this;
        }

        public GetOutfitResult build() {
            return new GetOutfitResult(outfit);
        }
    }
}
