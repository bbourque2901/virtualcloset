package com.nashss.se.virtualcloset.activity.results;

import com.nashss.se.virtualcloset.models.OutfitModel;

public class CreateOutfitResult {
    private final OutfitModel outfit;

    private CreateOutfitResult(OutfitModel outfit) {
        this.outfit = outfit;
    }

    public OutfitModel getOutfit() {
        return outfit;
    }

    @Override
    public String toString() {
        return "CreateOutfitResult{" +
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

        public CreateOutfitResult build() {
            return new CreateOutfitResult(outfit);
        }
    }
}
