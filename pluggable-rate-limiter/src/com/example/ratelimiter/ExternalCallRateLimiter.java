package com.example.ratelimiter;

import java.util.List;

public interface ExternalCallRateLimiter {
    RateLimitDecision allowExternalCall(String rateKey, List<RateLimitRule> rules);
}
