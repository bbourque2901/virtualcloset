package com.nashss.se.virtualcloset.activity.results;

import com.nashss.se.virtualcloset.models.OutfitModel;

public class DeleteOutfitResult {

    private final OutfitModel outfit;
    private final String message;

    private DeleteOutfitResult(OutfitModel outfit, String message) {
        this.outfit = outfit;
        this.message = message;
    }

    public OutfitModel getOutfit() {
        return outfit;
    }
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "DeleteOutfitResult{" +
                "outfit=" + outfit +
                "message=" + message +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private OutfitModel outfit;
        private String message;

        public Builder withOutfit(OutfitModel outfit) {
            this.outfit = outfit;
            return this;
        }
        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public DeleteOutfitResult build() {
            return new DeleteOutfitResult(outfit, message);
        }
    }
}
