package com.nashss.se.virtualcloset.lambda;

import com.nashss.se.virtualcloset.activity.requests.GetClothingFromOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.GetClothingFromOutfitResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetClothingFromOutfitLambda
        extends LambdaActivityRunner<GetClothingFromOutfitRequest, GetClothingFromOutfitResult>
        implements RequestHandler<LambdaRequest<GetClothingFromOutfitRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetClothingFromOutfitRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetClothingFromOutfitRequest.builder()
                                .withId(path.get("id"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetClothingFromOutfitActivity().handleRequest(request)
        );
    }
}
