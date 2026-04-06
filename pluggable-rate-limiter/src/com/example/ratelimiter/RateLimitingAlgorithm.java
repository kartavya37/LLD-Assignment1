package com.example.ratelimiter;

import java.util.List;

public interface RateLimitingAlgorithm {
    RateLimitDecision evaluateAndConsume(String rateKey, List<RateLimitRule> rules, long nowMillis);

    String name();
}
