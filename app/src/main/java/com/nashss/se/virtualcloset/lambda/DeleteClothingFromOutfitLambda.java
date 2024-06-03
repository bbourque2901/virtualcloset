package com.nashss.se.virtualcloset.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.virtualcloset.activity.requests.DeleteClothingFromOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.DeleteClothingFromOutfitResult;

public class DeleteClothingFromOutfitLambda
        extends LambdaActivityRunner<DeleteClothingFromOutfitRequest, DeleteClothingFromOutfitResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteClothingFromOutfitRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteClothingFromOutfitRequest> inpt, Context cxt) {
        return super.runActivity(
                () -> {
                    DeleteClothingFromOutfitRequest unAuthRequest = inpt.fromUserClaims(claims ->
                            DeleteClothingFromOutfitRequest.builder()
                                    .withCustomerId(claims.get("email"))
                                    .build());

                    return inpt.fromPath(path ->
                            DeleteClothingFromOutfitRequest.builder()
                                    .withId(path.get("id"))
                                    .withClothingId(path.get("clothingId"))
                                    .withCustomerId(unAuthRequest.getCustomerId())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideDeleteClothingFromOutfitActivity().handleRequest(request)
        );
    }
}
