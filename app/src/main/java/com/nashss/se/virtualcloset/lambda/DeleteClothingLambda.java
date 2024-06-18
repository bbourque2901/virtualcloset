package com.nashss.se.virtualcloset.lambda;

import com.nashss.se.virtualcloset.activity.requests.DeleteClothingRequest;
import com.nashss.se.virtualcloset.activity.results.DeleteClothingResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class DeleteClothingLambda
        extends LambdaActivityRunner<DeleteClothingRequest, DeleteClothingResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteClothingRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteClothingRequest> input, Context context) {
        return super.runActivity(
            () -> {
                DeleteClothingRequest unAuthenticatedRequest = input.fromUserClaims(claims ->
                        DeleteClothingRequest.builder()
                                 .withCustomerId(claims.get("email"))
                                 .build());
                return input.fromPath(path ->
                        DeleteClothingRequest.builder()
                                .withClothingId(path.get("clothingId"))
                                .withCustomerId(unAuthenticatedRequest.getCustomerId())
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideDeleteClothingActivity().handleRequest(request)
        );
    }
}
