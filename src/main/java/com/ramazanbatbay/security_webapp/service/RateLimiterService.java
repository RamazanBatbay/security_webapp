package com.ramazanbatbay.security_webapp.service;

import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class RateLimiterService {

    // Max requests allowed within the time window
    private static final int MAX_REQUESTS = 100;
    // Time window in seconds (e.g., 60 seconds)
    private static final int TIME_WINDOW_SECONDS = 60;

    // Key: IP Address, Value: Queue of timestamps of requests
    private final Map<String, ConcurrentLinkedDeque<Instant>> requestHistory = new ConcurrentHashMap<>();

    public boolean allowRequest(String ipAddress) {
        ConcurrentLinkedDeque<Instant> timestamps = requestHistory.computeIfAbsent(ipAddress,
                k -> new ConcurrentLinkedDeque<>());

        Instant now = Instant.now();
        Instant windowStart = now.minusSeconds(TIME_WINDOW_SECONDS);

        // Remove old requests that are outside the time window
        synchronized (timestamps) {
            while (!timestamps.isEmpty() && timestamps.peekFirst().isBefore(windowStart)) {
                timestamps.pollFirst();
            }

            if (timestamps.size() < MAX_REQUESTS) {
                timestamps.addLast(now);
                return true;
            } else {
                return false;
            }
        }
    }

    public void cleanup() {
        requestHistory.clear();
    }
}
