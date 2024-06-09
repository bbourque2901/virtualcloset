package com.nashss.se.virtualcloset.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.virtualcloset.activity.requests.IncrementClothingWornCountRequest;
import com.nashss.se.virtualcloset.activity.results.IncrementClothingWornCountResult;

public class IncrementClothingWornCountLambda
        extends LambdaActivityRunner<IncrementClothingWornCountRequest, IncrementClothingWornCountResult>
        implements RequestHandler<AuthenticatedLambdaRequest<IncrementClothingWornCountRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<IncrementClothingWornCountRequest> ipt, Context ct) {
        return super.runActivity(
                () -> ipt.fromPath(path ->
                        IncrementClothingWornCountRequest.builder()
                                .withClothingId(path.get("clothingId"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideIncrementClothingWornCountActivity().handleRequest(request)
        );
    }
}
