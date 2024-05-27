package com.nashss.se.virtualcloset.lambda;

import com.nashss.se.virtualcloset.activity.requests.CreateOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.CreateOutfitResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreateOutfitLambda
        extends LambdaActivityRunner<CreateOutfitRequest, CreateOutfitResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateOutfitRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateOutfitRequest> input, Context context) {
        return super.runActivity(
            () -> {
                CreateOutfitRequest unauthenticatedRequest = input.fromBody(CreateOutfitRequest.class);
                return input.fromUserClaims(claims ->
                        CreateOutfitRequest.builder()
                                .withName(unauthenticatedRequest.getName())
                                .withCustomerId(claims.get("email"))
                                .withTags(unauthenticatedRequest.getTags())
                                .build());
            },
            (request, serviceComponent) ->
                serviceComponent.provideCreateOutfitActivity().handleRequest(request)
        );
    }
}
