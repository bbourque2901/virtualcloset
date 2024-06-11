package com.nashss.se.virtualcloset.lambda;

import com.nashss.se.virtualcloset.activity.requests.GetSortedClothingRequest;
import com.nashss.se.virtualcloset.activity.requests.GetSortedOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.GetSortedOutfitResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetSortedOutfitLambda
        extends LambdaActivityRunner<GetSortedOutfitRequest, GetSortedOutfitResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetSortedOutfitRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetSortedOutfitRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    GetSortedOutfitRequest unAuthRequest = input.fromUserClaims(claims ->
                            GetSortedOutfitRequest.builder()
                                    .withCustomerId(claims.get("email"))
                                    .build());

                    return input.fromQuery(query ->
                            GetSortedOutfitRequest.builder()
                                    .withCustomerId(unAuthRequest.getCustomerId())
                                    .withAscending(Boolean.parseBoolean(query.getOrDefault("ascending", "true")))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetSortedOutfitActivity().handleRequest(request)
        );
    }
}
