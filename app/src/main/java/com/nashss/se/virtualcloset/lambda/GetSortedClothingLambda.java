package com.nashss.se.virtualcloset.lambda;

import com.nashss.se.virtualcloset.activity.requests.GetSortedClothingRequest;
import com.nashss.se.virtualcloset.activity.results.GetSortedClothingResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetSortedClothingLambda
        extends LambdaActivityRunner<GetSortedClothingRequest, GetSortedClothingResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetSortedClothingRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetSortedClothingRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    GetSortedClothingRequest unAuthRequest = input.fromUserClaims(claims ->
                            GetSortedClothingRequest.builder()
                                    .withCustomerId(claims.get("email"))
                                    .build());

                    return input.fromQuery(query ->
                            GetSortedClothingRequest.builder()
                                    .withCustomerId(unAuthRequest.getCustomerId())
                                    .withAscending(Boolean.parseBoolean(query.getOrDefault("ascending", "true")))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetSortedClothingActivity().handleRequest(request)
        );
    }
}
