package com.nashss.se.virtualcloset.lambda;

import com.nashss.se.virtualcloset.activity.requests.GetUserOutfitsRequest;
import com.nashss.se.virtualcloset.activity.results.GetUserOutfitsResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetUserOutfitsLambda
        extends LambdaActivityRunner<GetUserOutfitsRequest, GetUserOutfitsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetUserOutfitsRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetUserOutfitsRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromUserClaims(claims ->
                    GetUserOutfitsRequest.builder()
                            .withCustomerId(claims.get("email"))
                            .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideGetUserOutfitsActivity().handleRequest(request)
        );
    }
}

