package com.nashss.se.virtualcloset.lambda;

import com.nashss.se.virtualcloset.activity.requests.GetUserClothingRequest;
import com.nashss.se.virtualcloset.activity.results.GetUserClothingResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetUserClothingLambda
        extends LambdaActivityRunner<GetUserClothingRequest, GetUserClothingResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetUserClothingRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetUserClothingRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromUserClaims(claims ->
                    GetUserClothingRequest.builder()
                            .withCustomerId(claims.get("email"))
                            .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideGetUserClothingActivity().handleRequest(request)
        );
    }
}
