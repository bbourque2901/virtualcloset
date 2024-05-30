package com.nashss.se.virtualcloset.lambda;

import com.nashss.se.virtualcloset.activity.requests.UpdateOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.UpdateOutfitResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


public class UpdateOutfitLambda
        extends LambdaActivityRunner<UpdateOutfitRequest, UpdateOutfitResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateOutfitRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateOutfitRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdateOutfitRequest unauthenticatedRequest = input.fromBody(UpdateOutfitRequest.class);
                    return input.fromUserClaims(claims ->
                            UpdateOutfitRequest.builder()
                                    .withId(unauthenticatedRequest.getId())
                                    .withName(unauthenticatedRequest.getName())
                                    .withCustomerId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateOutfitActivity().handleRequest(request)
        );
    }
}
