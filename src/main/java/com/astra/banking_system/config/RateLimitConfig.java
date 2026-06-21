package com.astra.banking_system.config;

import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

@Component
public class RateLimitConfig extends OncePerRequestFilter {

    private final  Map<String , Bucket> bucketMap = new ConcurrentReferenceHashMap<>();

    private Bucket createBucket(){
        return Bucket.builder()
                .addLimit(
                        limit->limit
                                .capacity(10)
                                .refillGreedy(10, Duration.ofMinutes(5))
                )
                .build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String ip = request.getRemoteAddr();
        Bucket bucket = bucketMap.computeIfAbsent(
                ip,
                k->createBucket()
        );

        if (bucket.tryConsume(1)){
            filterChain.doFilter(request,response);
        }else{
            response.setStatus(429);
            response.getWriter().write("Too Many Requests ...");
        }


    }
}
