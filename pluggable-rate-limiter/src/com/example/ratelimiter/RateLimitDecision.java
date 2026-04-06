package com.example.ratelimiter;

public final class RateLimitDecision {
    private final boolean allowed;
    private final String reason;

    private RateLimitDecision(boolean allowed, String reason) {
        this.allowed = allowed;
        this.reason = reason;
    }

    public static RateLimitDecision allow() {
        return new RateLimitDecision(true, "allowed");
    }

    public static RateLimitDecision deny(String reason) {
        return new RateLimitDecision(false, reason);
    }

    public boolean isAllowed() {
        return allowed;
    }

    public String getReason() {
        return reason;
    }
}
