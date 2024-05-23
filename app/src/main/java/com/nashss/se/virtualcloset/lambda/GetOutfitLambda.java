package com.nashss.se.virtualcloset.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.virtualcloset.activity.requests.GetOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.GetOutfitResult;

public class GetOutfitLambda
        extends LambdaActivityRunner<GetOutfitRequest, GetOutfitResult>
        implements RequestHandler<LambdaRequest<GetOutfitRequest>, LambdaResponse> {
    /**
     * Handles a Lambda Function request.
     *
     * @param input   The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return The Lambda Function output
     */
    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetOutfitRequest> input, Context context) {
        return LambdaResponse.success();
    }
}
