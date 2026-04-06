package com.example.ratelimiter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RateLimiterEngine implements ExternalCallRateLimiter {
    private final Clock clock;
    private volatile RateLimitingAlgorithm algorithm;

    public RateLimiterEngine(Clock clock, RateLimitingAlgorithm algorithm) {
        if (clock == null || algorithm == null) {
            throw new IllegalArgumentException("clock and algorithm are required");
        }
        this.clock = clock;
        this.algorithm = algorithm;
    }

    public void switchAlgorithm(RateLimitingAlgorithm newAlgorithm) {
        if (newAlgorithm == null) {
            throw new IllegalArgumentException("newAlgorithm is required");
        }
        this.algorithm = newAlgorithm;
    }

    @Override
    public RateLimitDecision allowExternalCall(String rateKey, List<RateLimitRule> rules) {
        if (rateKey == null || rateKey.trim().isEmpty()) {
            throw new IllegalArgumentException("rateKey is required");
        }
        if (rules == null || rules.isEmpty()) {
            throw new IllegalArgumentException("At least one rate limit rule is required");
        }

        List<RateLimitRule> normalizedRules = new ArrayList<>(rules);
        normalizedRules.sort(Comparator.comparingLong(RateLimitRule::getWindowMillis)
                .thenComparingInt(RateLimitRule::getMaxRequests));

        return algorithm.evaluateAndConsume(rateKey, normalizedRules, clock.nowMillis());
    }

    public String currentAlgorithmName() {
        return algorithm.name();
    }
}
