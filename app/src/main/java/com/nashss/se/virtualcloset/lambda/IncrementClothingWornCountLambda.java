package com.nashss.se.virtualcloset.lambda;

import com.nashss.se.virtualcloset.activity.requests.IncrementClothingWornCountRequest;
import com.nashss.se.virtualcloset.activity.results.IncrementClothingWornCountResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class IncrementClothingWornCountLambda
        extends LambdaActivityRunner<IncrementClothingWornCountRequest, IncrementClothingWornCountResult>
        implements RequestHandler<AuthenticatedLambdaRequest<IncrementClothingWornCountRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<IncrementClothingWornCountRequest> ipt, Context ct) {
        return super.runActivity(
            () -> {
                IncrementClothingWornCountRequest unAuthRequest = ipt.fromUserClaims(claims ->
                        IncrementClothingWornCountRequest.builder()
                                .withCustomerId(claims.get("email"))
                                .build());

                return ipt.fromPath(path ->
                        IncrementClothingWornCountRequest.builder()
                                .withClothingId(path.get("clothingId"))
                                .withCustomerId(unAuthRequest.getCustomerId())
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideIncrementClothingWornCountActivity().handleRequest(request)
        );
    }
}
