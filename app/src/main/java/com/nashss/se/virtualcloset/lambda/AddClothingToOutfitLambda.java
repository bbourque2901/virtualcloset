package com.nashss.se.virtualcloset.lambda;

import com.nashss.se.virtualcloset.activity.requests.AddClothingToOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.AddClothingToOutfitResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


public class AddClothingToOutfitLambda
        extends LambdaActivityRunner<AddClothingToOutfitRequest, AddClothingToOutfitResult>
        implements RequestHandler<AuthenticatedLambdaRequest<AddClothingToOutfitRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddClothingToOutfitRequest> input, Context context) {
        return super.runActivity(
            () -> {
                AddClothingToOutfitRequest unauthenticatedRequest = input.fromBody(AddClothingToOutfitRequest.class);
                return input.fromUserClaims(claims ->
                        AddClothingToOutfitRequest.builder()
                            .withClothingId(unauthenticatedRequest.getClothingId())
                            .withCustomerId(claims.get("email"))
                            .withId(unauthenticatedRequest.getId())
                            .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideAddClothingToOutfitActivity().handleRequest(request)
        );

    }

}
