package com.example.ratelimiter;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        MutableClock clock = new MutableClock(0L);

        List<RateLimitRule> rules = Arrays.asList(
                new RateLimitRule(5, Duration.ofMinutes(1)),
                new RateLimitRule(1000, Duration.ofHours(1)));

        RateLimiterEngine rateLimiter = new RateLimiterEngine(
                clock,
                new FixedWindowRateLimitingAlgorithm());

        InternalService internalService = new InternalService(
                rateLimiter,
                new ExternalProviderClient(),
                rules);

        System.out.println("Algorithm: " + rateLimiter.currentAlgorithmName());
        runFixedWindowDemo(internalService, clock);

        rateLimiter.switchAlgorithm(new SlidingWindowCounterRateLimitingAlgorithm());
        System.out.println("\nSwitched algorithm to: " + rateLimiter.currentAlgorithmName());
        runSlidingWindowDemo(internalService, clock);
    }

    private static void runFixedWindowDemo(InternalService internalService, MutableClock clock) {
        System.out.println("=== Fixed Window Demo (T1 max 5/min) ===");

        for (int i = 1; i <= 6; i++) {
            ClientRequest request = new ClientRequest("T1", "job-fixed-" + i, true);
            String result = internalService.handleRequest(request);
            System.out.println("Request " + i + " -> " + result);
        }

        ClientRequest internalOnlyRequest = new ClientRequest("T1", "internal-only", false);
        String result = internalService.handleRequest(internalOnlyRequest);
        System.out.println("No external call path -> " + result);

        clock.advanceMillis(Duration.ofMinutes(1).toMillis());
        ClientRequest afterWindowReset = new ClientRequest("T1", "after-reset", true);
        System.out.println("After 1 minute -> " + internalService.handleRequest(afterWindowReset));
    }

    private static void runSlidingWindowDemo(InternalService internalService, MutableClock clock) {
        System.out.println("=== Sliding Window Demo ===");

        clock.advanceMillis(Duration.ofSeconds(10).toMillis());
        for (int i = 1; i <= 5; i++) {
            ClientRequest request = new ClientRequest("T1", "job-sliding-" + i, true);
            System.out.println("Sliding request " + i + " -> " + internalService.handleRequest(request));
        }

        clock.advanceMillis(Duration.ofSeconds(30).toMillis());
        ClientRequest burst = new ClientRequest("T1", "burst-after-30s", true);
        System.out.println("Burst check -> " + internalService.handleRequest(burst));
    }
}
