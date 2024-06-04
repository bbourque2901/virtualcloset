package com.nashss.se.virtualcloset.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.nashss.se.virtualcloset.activity.requests.CreateClothingRequest;
import com.nashss.se.virtualcloset.activity.results.CreateClothingResult;

import com.amazonaws.services.lambda.runtime.RequestHandler;



public class CreateClothingLambda
        extends LambdaActivityRunner<CreateClothingRequest, CreateClothingResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateClothingRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateClothingRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    CreateClothingRequest unauthenticatedRequest = input.fromBody(CreateClothingRequest.class);
                    return input.fromUserClaims(claims ->
                            CreateClothingRequest.builder()
                                    .withCustomerId(claims.get("email"))
                                    .withCategory(unauthenticatedRequest.getCategory())
                                    .withColor(unauthenticatedRequest.getColor())
                                    .withFit(unauthenticatedRequest.getFit())
                                    .withLength(unauthenticatedRequest.getLength())
                                    .withOccasion(unauthenticatedRequest.getOccasion())
                                    .withWeather(unauthenticatedRequest.getWeather())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreateClothingActivity().handleRequest(request)
        );
    }
}
