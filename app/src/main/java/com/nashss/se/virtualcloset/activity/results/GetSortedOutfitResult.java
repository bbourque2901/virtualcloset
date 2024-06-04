package com.nashss.se.virtualcloset.activity.results;

import com.nashss.se.virtualcloset.models.OutfitModel;

import java.util.List;

public class GetSortedOutfitResult {
    private final List<OutfitModel> outfits;

    private GetSortedOutfitResult(List<OutfitModel> outfits) {
        this.outfits = outfits;
    }

    public List<OutfitModel> getOutfits() {
        return outfits;
    }

    @Override
    public String toString() {
        return "GetSortedOutfitResult{" +
                "outfits=" + outfits +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<OutfitModel> outfits;

        public Builder withOutfits(List<OutfitModel> outfits) {
            this.outfits = outfits;
            return this;
        }

        public GetSortedOutfitResult build() {
            return new GetSortedOutfitResult(outfits);
        }
    }
}
