package com.nashss.se.virtualcloset.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetOutfitLambda implements RequestHandler<LambdaRequest<LambdaRequest>, LambdaResponse> {
    /**
     * Handles a Lambda Function request.
     *
     * @param input   The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return The Lambda Function output
     */
    @Override
    public LambdaResponse handleRequest(LambdaRequest<LambdaRequest> input, Context context) {
        return LambdaResponse.success();
    }
}
