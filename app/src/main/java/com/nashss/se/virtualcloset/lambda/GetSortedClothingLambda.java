package com.nashss.se.virtualcloset.lambda;

import com.nashss.se.virtualcloset.activity.requests.GetSortedClothingRequest;
import com.nashss.se.virtualcloset.activity.results.GetSortedClothingResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetSortedClothingLambda
        extends LambdaActivityRunner<GetSortedClothingRequest, GetSortedClothingResult>
        implements RequestHandler<LambdaRequest<GetSortedClothingRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetSortedClothingRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromQuery(query ->
                    GetSortedClothingRequest.builder()
                            .withCustomerId(query.get("customerId"))
                            .withAscending(Boolean.parseBoolean(query.get("ascending")))
                            .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideGetSortedClothingActivity().handleRequest(request)
        );
    }
}
