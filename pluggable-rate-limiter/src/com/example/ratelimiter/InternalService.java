package com.example.ratelimiter;

import java.util.List;

public class InternalService {
    private final ExternalCallRateLimiter rateLimiter;
    private final ExternalProviderClient externalProviderClient;
    private final List<RateLimitRule> rules;

    public InternalService(
            ExternalCallRateLimiter rateLimiter,
            ExternalProviderClient externalProviderClient,
            List<RateLimitRule> rules) {
        this.rateLimiter = rateLimiter;
        this.externalProviderClient = externalProviderClient;
        this.rules = rules;
    }

    public String handleRequest(ClientRequest request) {
        String internalComputation = "processed-" + request.getPayload();

        if (!request.isExternalCallNeeded()) {
            return internalComputation + "|no-external-call";
        }

        String rateKey = "tenant:" + request.getTenantId() + "|provider:payments";
        RateLimitDecision decision = rateLimiter.allowExternalCall(rateKey, rules);
        if (!decision.isAllowed()) {
            return internalComputation + "|external-call-denied:" + decision.getReason();
        }

        String externalResult = externalProviderClient.callPaidResource(request);
        return internalComputation + "|" + externalResult;
    }
}
