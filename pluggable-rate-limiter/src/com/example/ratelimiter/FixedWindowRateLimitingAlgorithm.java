package com.example.ratelimiter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FixedWindowRateLimitingAlgorithm implements RateLimitingAlgorithm {
    private final LockProvider lockProvider;
    private final Map<String, Map<RateLimitRule, FixedWindowState>> stateByKey = new ConcurrentHashMap<>();

    public FixedWindowRateLimitingAlgorithm() {
        this.lockProvider = new LockProvider();
    }

    @Override
    public RateLimitDecision evaluateAndConsume(String rateKey, List<RateLimitRule> rules, long nowMillis) {
        Object lock = lockProvider.lockForKey(rateKey);
        synchronized (lock) {
            Map<RateLimitRule, FixedWindowState> perRule = stateByKey.computeIfAbsent(rateKey,
                    ignored -> new ConcurrentHashMap<>());

            for (RateLimitRule rule : rules) {
                FixedWindowState state = perRule.computeIfAbsent(rule, ignored -> new FixedWindowState());
                if (!state.canConsume(rule, nowMillis)) {
                    return RateLimitDecision.deny("FixedWindow limit exceeded for rule: " + rule);
                }
            }

            for (RateLimitRule rule : rules) {
                FixedWindowState state = perRule.get(rule);
                state.consume(rule, nowMillis);
            }
            return RateLimitDecision.allow();
        }
    }

    @Override
    public String name() {
        return "FixedWindowCounter";
    }

    private static class FixedWindowState {
        private long activeWindowId = -1;
        private int count = 0;

        boolean canConsume(RateLimitRule rule, long nowMillis) {
            rolloverIfNeeded(rule, nowMillis);
            return count < rule.getMaxRequests();
        }

        void consume(RateLimitRule rule, long nowMillis) {
            rolloverIfNeeded(rule, nowMillis);
            count++;
        }

        private void rolloverIfNeeded(RateLimitRule rule, long nowMillis) {
            long windowId = nowMillis / rule.getWindowMillis();
            if (windowId != activeWindowId) {
                activeWindowId = windowId;
                count = 0;
            }
        }
    }
}
