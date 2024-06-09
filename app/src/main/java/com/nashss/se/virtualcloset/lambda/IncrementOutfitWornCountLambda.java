package com.nashss.se.virtualcloset.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.virtualcloset.activity.requests.IncrementOutfitWornCountRequest;
import com.nashss.se.virtualcloset.activity.results.IncrementOutfitWornCountResult;

public class IncrementOutfitWornCountLambda
        extends LambdaActivityRunner<IncrementOutfitWornCountRequest, IncrementOutfitWornCountResult>
        implements RequestHandler<AuthenticatedLambdaRequest<IncrementOutfitWornCountRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<IncrementOutfitWornCountRequest> ipt, Context ctxt) {
        return super.runActivity(
                () -> ipt.fromPath(path ->
                        IncrementOutfitWornCountRequest.builder()
                                .withId(path.get("id"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideIncrementOutfitWornCountActivity().handleRequest(request)
        );
    }
}
