package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.GetSortedOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.GetSortedOutfitResult;

import com.nashss.se.virtualcloset.converters.ModelConverter;

import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;

import com.nashss.se.virtualcloset.models.OutfitModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;

public class GetSortedOutfitActivity {
    private final Logger log = LogManager.getLogger();
    private final OutfitDao outfitDao;

    /**
     * Instantiates new GetSortedOutfitActivity object.
     *
     * @param outfitDao to access outfit table.
     */
    @Inject
    public GetSortedOutfitActivity(OutfitDao outfitDao) {
        this.outfitDao = outfitDao;
    }

    /**
     * This method sorts the customer's outfits by their worn count in ascending order.
     *
     * @param request Request containing specified customer and their sort order
     * @return getSortedOutfitResult with the updated outfit list.
     */
    public GetSortedOutfitResult handleRequest(final GetSortedOutfitRequest request) {
        log.info("Received GetSortedOutfitRequest {}", request);

        String customerId = request.getCustomerId();
        boolean ascending = request.isAscending();

        List<Outfit> outfits = outfitDao.getOutfitsSortedByWornCount(customerId, ascending);
        List<OutfitModel> outfitModels = outfits.stream()
                .map(outfit -> new ModelConverter().toOutfitModel(outfit))
                .collect(Collectors.toList());

        return GetSortedOutfitResult.builder()
                .withOutfits(outfitModels)
                .build();
    }
}
