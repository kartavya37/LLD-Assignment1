package com.example.ratelimiter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowCounterRateLimitingAlgorithm implements RateLimitingAlgorithm {
    private final LockProvider lockProvider;
    private final Map<String, Map<RateLimitRule, SlidingWindowCounterState>> stateByKey = new ConcurrentHashMap<>();

    public SlidingWindowCounterRateLimitingAlgorithm() {
        this.lockProvider = new LockProvider();
    }

    @Override
    public RateLimitDecision evaluateAndConsume(String rateKey, List<RateLimitRule> rules, long nowMillis) {
        Object lock = lockProvider.lockForKey(rateKey);
        synchronized (lock) {
            Map<RateLimitRule, SlidingWindowCounterState> perRule = stateByKey.computeIfAbsent(rateKey,
                    ignored -> new ConcurrentHashMap<>());

            for (RateLimitRule rule : rules) {
                SlidingWindowCounterState state = perRule.computeIfAbsent(rule,
                        ignored -> new SlidingWindowCounterState());
                if (!state.canConsume(rule, nowMillis)) {
                    return RateLimitDecision.deny("SlidingWindow limit exceeded for rule: " + rule);
                }
            }

            for (RateLimitRule rule : rules) {
                SlidingWindowCounterState state = perRule.get(rule);
                state.consume(rule, nowMillis);
            }
            return RateLimitDecision.allow();
        }
    }

    @Override
    public String name() {
        return "SlidingWindowCounter";
    }

    private static class SlidingWindowCounterState {
        private long currentWindowStart = -1;
        private int currentCount = 0;
        private long previousWindowStart = -1;
        private int previousCount = 0;

        boolean canConsume(RateLimitRule rule, long nowMillis) {
            rollWindowsIfNeeded(rule, nowMillis);
            double estimatedCount = estimateCurrentLoad(rule, nowMillis);
            return estimatedCount < rule.getMaxRequests();
        }

        void consume(RateLimitRule rule, long nowMillis) {
            rollWindowsIfNeeded(rule, nowMillis);
            currentCount++;
        }

        private void rollWindowsIfNeeded(RateLimitRule rule, long nowMillis) {
            long windowSize = rule.getWindowMillis();
            long alignedStart = (nowMillis / windowSize) * windowSize;

            if (currentWindowStart == -1) {
                currentWindowStart = alignedStart;
                currentCount = 0;
                previousWindowStart = alignedStart - windowSize;
                previousCount = 0;
                return;
            }

            if (alignedStart == currentWindowStart) {
                return;
            }

            if (alignedStart == currentWindowStart + windowSize) {
                previousWindowStart = currentWindowStart;
                previousCount = currentCount;
                currentWindowStart = alignedStart;
                currentCount = 0;
                return;
            }

            currentWindowStart = alignedStart;
            currentCount = 0;
            previousWindowStart = alignedStart - windowSize;
            previousCount = 0;
        }

        private double estimateCurrentLoad(RateLimitRule rule, long nowMillis) {
            long windowSize = rule.getWindowMillis();
            long elapsedInCurrent = nowMillis - currentWindowStart;
            double previousWeight = (double) (windowSize - elapsedInCurrent) / windowSize;
            if (previousWindowStart != currentWindowStart - windowSize) {
                previousWeight = 0.0;
            }
            return currentCount + (previousCount * previousWeight);
        }
    }
}
