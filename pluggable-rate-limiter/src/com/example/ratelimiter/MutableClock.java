package com.example.ratelimiter;

public class MutableClock implements Clock {
    private long nowMillis;

    public MutableClock(long initialMillis) {
        this.nowMillis = initialMillis;
    }

    @Override
    public synchronized long nowMillis() {
        return nowMillis;
    }

    public synchronized void advanceMillis(long deltaMillis) {
        if (deltaMillis < 0) {
            throw new IllegalArgumentException("deltaMillis must be non-negative");
        }
        this.nowMillis += deltaMillis;
    }
}
