package com.nashss.se.virtualcloset.lambda;

import com.nashss.se.virtualcloset.activity.requests.GetClothingRequest;
import com.nashss.se.virtualcloset.activity.results.GetClothingResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetClothingLambda
        extends LambdaActivityRunner<GetClothingRequest, GetClothingResult>
        implements RequestHandler<LambdaRequest<GetClothingRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetClothingRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromPath(path ->
                    GetClothingRequest.builder()
                            .withClothingId(path.get("clothingId"))
                            .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideGetClothingActivity().handleRequest(request)
        );
    }
}
