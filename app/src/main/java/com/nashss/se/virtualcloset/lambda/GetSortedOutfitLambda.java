package com.nashss.se.virtualcloset.lambda;

import com.nashss.se.virtualcloset.activity.requests.GetSortedOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.GetSortedOutfitResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetSortedOutfitLambda
        extends LambdaActivityRunner<GetSortedOutfitRequest, GetSortedOutfitResult>
        implements RequestHandler<LambdaRequest<GetSortedOutfitRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetSortedOutfitRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromQuery(query ->
                    GetSortedOutfitRequest.builder()
                            .withCustomerId(query.get("customerId"))
                            .withAscending(Boolean.parseBoolean(query.get("ascending")))
                            .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideGetSortedOutfitActivity().handleRequest(request)
        );
    }
}
