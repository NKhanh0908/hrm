package com.project.hrm.common.cofiguration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimitService {
    private final int MAX_REQUESTS_PER_MINUTE = 100;

    private final LoadingCache<String, Bucket> ipBucketCache;

    public RateLimitService() {
        ipBucketCache = CacheBuilder.newBuilder()
                .expireAfterAccess(1, TimeUnit.HOURS)
                .build(new CacheLoader<>() {
                    @Override
                    public Bucket load(String ip) {
                        Refill refill = Refill.greedy(MAX_REQUESTS_PER_MINUTE, Duration.ofMinutes(1));
                        Bandwidth limit = Bandwidth.classic(MAX_REQUESTS_PER_MINUTE, refill);
                        return Bucket4j.builder()
                                .addLimit(limit)
                                .build();
                    }
                });
    }

    public boolean isAllowed(String ip) {
        Bucket bucket = ipBucketCache.getUnchecked(ip);
        return bucket.tryConsume(1);
    }
}
