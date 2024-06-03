package com.nashss.se.virtualcloset.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.virtualcloset.activity.requests.DeleteOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.DeleteOutfitResult;

public class DeleteOutfitLambda
        extends LambdaActivityRunner<DeleteOutfitRequest, DeleteOutfitResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteOutfitRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteOutfitRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    DeleteOutfitRequest unAuthRequest = input.fromUserClaims(claims ->
                            DeleteOutfitRequest.builder()
                                    .withCustomerId(claims.get("email"))
                                    .build());
                    return input.fromPath(path ->
                            DeleteOutfitRequest.builder()
                                    .withId(path.get("id"))
                                    .withCustomerId(unAuthRequest.getCustomerId())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideDeleteOutfitActivity().handleRequest(request)
        );
    }
}
