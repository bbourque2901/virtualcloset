package com.nashss.se.virtualcloset.lambda;

import com.nashss.se.virtualcloset.activity.requests.IncrementOutfitWornCountRequest;
import com.nashss.se.virtualcloset.activity.results.IncrementOutfitWornCountResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class IncrementOutfitWornCountLambda
        extends LambdaActivityRunner<IncrementOutfitWornCountRequest, IncrementOutfitWornCountResult>
        implements RequestHandler<AuthenticatedLambdaRequest<IncrementOutfitWornCountRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<IncrementOutfitWornCountRequest> ipt, Context ctxt) {
        return super.runActivity(
            () -> {
                IncrementOutfitWornCountRequest unAuthRequest = ipt.fromUserClaims(claims ->
                        IncrementOutfitWornCountRequest.builder()
                                .withCustomerId(claims.get("email"))
                                .build());
                return ipt.fromPath(path ->
                        IncrementOutfitWornCountRequest.builder()
                                .withId(path.get("id"))
                                .withCustomerId(unAuthRequest.getCustomerId())
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideIncrementOutfitWornCountActivity().handleRequest(request)
        );
    }
}
