package com.pp.api.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class BlockUserRepositoryImpl implements BlockUserRepository {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void block(Long blockerId, Long blockedId) {
        if (isBlocked(blockerId, blockedId)) {
            return;
        }

        stringRedisTemplate.opsForSet()
                .add(getBlockListKey(blockerId), String.valueOf(blockedId));
    }

    @Override
    public void unblock(Long blockerId, Long blockedId) {
        if (!isBlocked(blockerId, blockedId)) {
            return;
        }

        stringRedisTemplate.opsForSet()
                .remove(getBlockListKey(blockerId), String.valueOf(blockedId));
    }

    @Override
    public boolean isBlocked(Long blockerId, Long blockedId) {
        return Boolean.TRUE.equals(
                stringRedisTemplate.opsForSet()
                .isMember(getBlockListKey(blockerId), String.valueOf(blockedId))
        );
    }

    @Override
    public Set<String> findBlockedIds(Long blockerId) {
        return stringRedisTemplate.opsForSet()
                .members(getBlockListKey(blockerId));
    }

    private String getBlockListKey(Long userId) {
        return "user" + userId + ":blocklist";
    }
}
