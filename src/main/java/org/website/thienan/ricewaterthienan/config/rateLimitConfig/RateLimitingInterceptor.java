package org.website.thienan.ricewaterthienan.config.rateLimitConfig;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class RateLimitingInterceptor implements HandlerInterceptor {

    private final Map<String, Long> requestCounts = new ConcurrentHashMap<>();
    private static final int MAX_REQUESTS = 100;
    private static final long THRESHOLD_TIME_MILLIS = 24 * 60 * 60 * 1000;

    @Override
    public synchronized boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        log.info("preHandle Rate LimitApi");
        String ipAddress = request.getRemoteAddr(); // get IP access
        long currentTime = System.currentTimeMillis(); // get Current Time
        long count = requestCounts.getOrDefault(ipAddress, 0L); // check quantity request ip
        long lastRequestTime = requestCounts.getOrDefault(ipAddress + "_time", 0L); // get last time

        if (currentTime - lastRequestTime >= THRESHOLD_TIME_MILLIS) {
            requestCounts.put(ipAddress, 1L);
            requestCounts.put(ipAddress + "_time", currentTime);
        } else {
            if (count >= MAX_REQUESTS) {
                response.setStatus(HttpServletResponse.SC_REQUEST_URI_TOO_LONG);
                response.getWriter().write("Too many request in day, please access again!");
                response.getWriter().flush();
                return false;
            } else {
                requestCounts.put(ipAddress, count + 1);
            }
        }

        return true;
    }
}