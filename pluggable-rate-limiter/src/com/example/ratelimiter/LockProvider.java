package com.example.ratelimiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LockProvider {
    private final ConcurrentMap<String, Object> locks = new ConcurrentHashMap<>();

    public Object lockForKey(String key) {
        return locks.computeIfAbsent(key, ignored -> new Object());
    }
}
