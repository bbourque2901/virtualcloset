package com.nashss.se.virtualcloset.lambda;

import com.nashss.se.virtualcloset.activity.requests.GetOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.GetOutfitResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetOutfitLambda
        extends LambdaActivityRunner<GetOutfitRequest, GetOutfitResult>
        implements RequestHandler<LambdaRequest<GetOutfitRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    /**
     * Handles a Lambda Function request.
     *
     * @param input   The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return The Lambda Function output
     */
    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetOutfitRequest> input, Context context) {
        log.info("handlerequest");
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetOutfitRequest.builder()
                                .withId(path.get("id"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetOutfitActivity().handleRequest(request)
        );
    }
}