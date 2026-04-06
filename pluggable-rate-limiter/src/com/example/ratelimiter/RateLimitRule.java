package com.example.ratelimiter;

import java.time.Duration;
import java.util.Objects;

public final class RateLimitRule {
    private final int maxRequests;
    private final Duration window;

    public RateLimitRule(int maxRequests, Duration window) {
        if (maxRequests <= 0) {
            throw new IllegalArgumentException("maxRequests must be positive");
        }
        if (window == null || window.isZero() || window.isNegative()) {
            throw new IllegalArgumentException("window must be positive");
        }
        this.maxRequests = maxRequests;
        this.window = window;
    }

    public int getMaxRequests() {
        return maxRequests;
    }

    public Duration getWindow() {
        return window;
    }

    public long getWindowMillis() {
        return window.toMillis();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RateLimitRule)) {
            return false;
        }
        RateLimitRule that = (RateLimitRule) other;
        return maxRequests == that.maxRequests && window.equals(that.window);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxRequests, window);
    }

    @Override
    public String toString() {
        return maxRequests + " requests / " + window;
    }
}
